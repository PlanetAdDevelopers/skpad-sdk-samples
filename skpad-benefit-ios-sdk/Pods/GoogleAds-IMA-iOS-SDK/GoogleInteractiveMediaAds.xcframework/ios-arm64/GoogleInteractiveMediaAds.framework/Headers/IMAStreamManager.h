//
//  IMAStreamManager.h
//  GoogleIMA3
//
//  Copyright (c) 2015 Google Inc. All rights reserved.
//
//  Declares IMAStreamManager interface that manages stream playback.
//  The interface represents an abstract API. There can be only one stream managed by a single
//  stream manager.
//  The implementing code should respond to the callbacks as defined in IMAStreamManagerDelegate.
//
//  A typical stream playback session:
//    1. Stream manager is retrieved. Delegate is set.
//    2. Stream playback will meanwhile start because of the adsLoader requestStream call.
//    3. delegate.didReceiveAdEvent: is called with a kIMAAdBreakStarted event.
//    4. Publisher should now hide content controls or disable seeking. Ad is now playing
//    5. delegate.didReceiveAdEvent: is called with a kIMAAdStart event. This event comes with
//       information about the ad.
//    6. delegate.didReceiveAdEvent is called with ad events like quartiles, midpoint and complete.
//    7. Ad break finishes and delegate.didReceiveAdEvent is called with a kIMAAdBreakEnded event.
//    8. Publisher should now show content controls or reenable seeking. Content is now playing.
//       playback should resume now.
//       It is possible to detach the delegate and destroy the ads manager.
//
//  Steps 3-8 may repeat several times for the same stream if there are multiple ads inserted in
//  the stream.

#import <Foundation/Foundation.h>

#import "IMAAdError.h"
#import "IMAAdEvent.h"
#import "IMAAdPlaybackInfo.h"
#import "IMAAdsRenderingSettings.h"
#import "IMAContentPlayhead.h"

NS_ASSUME_NONNULL_BEGIN

@class IMACuepoint;
@class IMAStreamManager;

#pragma mark IMAStreamManagerDelegate

/**
 * A callback protocol for IMAStreamManager.
 */
@protocol IMAStreamManagerDelegate

/**
 * Called when there is an IMAAdEvent.
 *
 * @param streamManager the IMAStreamManager receiving the event
 * @param event         the IMAAdEvent received
 */
- (void)streamManager:(IMAStreamManager *)streamManager didReceiveAdEvent:(IMAAdEvent *)event;

/**
 * Called when there is an IMAAdEvent.
 *
 * @param streamManager the IMAStreamManager receiving the error
 * @param error         the IMAAdError received
 */
- (void)streamManager:(IMAStreamManager *)streamManager didReceiveAdError:(IMAAdError *)error;

@optional

/**
 * Called when the ad is playing to give updates about ad progress.
 *
 * @param streamManager    the IMAStreamManager tracking ad playback
 * @param time             the current ad playback time in seconds
 * @param adDuration       the total duration of the current ad in seconds
 * @param adPosition       the ad position of the current ad in the current ad break
 * @param totalAds         the total number of ads in the current ad break
 * @param adBreakDuration  the total duration of the current ad break in seconds
 * @param adPeriodDuration the total duration of the current ad period in seconds. This includes ads
 * duration plus slate.
 */
- (void)streamManager:(IMAStreamManager *)streamManager
    adDidProgressToTime:(NSTimeInterval)time
             adDuration:(NSTimeInterval)adDuration
             adPosition:(NSInteger)adPosition
               totalAds:(NSInteger)totalAds
        adBreakDuration:(NSTimeInterval)adBreakDuration
       adPeriodDuration:(NSTimeInterval)adPeriodDuration;

@end

#pragma mark - IMAStreamManager

/**
 * The IMAStreamManager class is responsible for playing streams.
 */
@interface IMAStreamManager : NSObject

/**
 * The IMAStreamManagerDelegate to notify with events during stream playback.
 */
@property(nonatomic, weak, nullable) NSObject<IMAStreamManagerDelegate> *delegate;

/**
 * Identifier used during dynamic ad insertion to uniquely identify a stream. This can be used in
 * the Stream Activity Monitor Debug Console to debug the stream session.
 */
@property(nonatomic, copy, readonly, nullable) NSString *streamId;

/**
 * The cuepoints for the current stream, populated after @c kIMAAdEvent_CUEPOINTS_CHANGED event is
 * dispatched. Will be empty for live streams.
 */
@property(nonatomic, readonly) NSArray<IMACuepoint *> *cuepoints;

/**
 * Initializes and loads the stream.
 *
 * @param adsRenderingSettings the IMAAdsRenderingSettings. Pass in to influence ad rendering.
 *                             Use nil to default to standard rendering.
 */
- (void)initializeWithAdsRenderingSettings:(nullable IMAAdsRenderingSettings *)adsRenderingSettings;

/**
 * Returns the stream time with ads for a given content time. Returns the given content time
 * for live streams.
 *
 * @param contentTime   the content time without any ads (in seconds)
 *
 * @return the stream time that corresponds with the given content time once ads are inserted
 */
- (NSTimeInterval)streamTimeForContentTime:(NSTimeInterval)contentTime;

/**
 * Returns the content time without ads for a given stream time. Returns the given stream time
 * for live streams.
 *
 * @param streamTime   the stream time with inserted ads (in seconds)
 *
 * @return the content time that corresponds with the given stream time once ads are removed
 */
- (NSTimeInterval)contentTimeForStreamTime:(NSTimeInterval)streamTime;

/**
 * Returns the previous cuepoint for the given stream time. Returns nil if no such cuepoint exists.
 * This is used to implement features like snap back, and called when the publisher detects that
 * the user seeked in order to force the user to watch an ad break they may have skipped over.
 *
 * @param streamTime   the stream time that the was seeked to.
 *
 * @return the previous IMACuepoint for the given stream time.
 */
- (nullable IMACuepoint *)previousCuepointForStreamTime:(NSTimeInterval)streamTime;

/**
 * Replaces all of the ad tag parameters used for upcoming ad requests for a
 * live stream.
 * Note that this call is a no-op for VOD streams.
 *
 * @param adTagParameters   the new ad tag parameters for the current stream.
 */
- (void)replaceAdTagParameters:(nullable NSDictionary<NSString *, NSString *> *)adTagParameters;

/**
 * Requests SDK to retrieve the ad metadata and then load the provided streamManifestUrl and
 * streamSubtitles into the player. This should be manually triggered once the stream manifest has
 * been retrieved from the third party video stitcher. Note that this is only used for Pod serving
 * VOD with a third party video stitcher, and is a no-op for other stream request types.
 *
 * @param streamURL the manifest url for the stream with dynamic ad insertion.
 * @param streamSubtitles the subtitles array for the stream.
 */
- (void)loadThirdPartyStream:(NSURL *)streamURL
             streamSubtitles:(NSArray<NSDictionary<NSString *, NSString *> *> *)streamSubtitles;

/**
 * :nodoc:
 */
- (instancetype)init NS_UNAVAILABLE;

/**
 * Cleans the stream manager's internal state for proper deallocation.
 */
- (void)destroy;

@end

NS_ASSUME_NONNULL_END
