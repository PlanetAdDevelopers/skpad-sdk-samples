//
//  SKPVideoPlayer.h
//  SKPMediaView
//
//  Created by Jaehee Ko on 15/01/2019.
//  Copyright © 2019 Buzzvil. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <AVKit/AVKit.h>

NS_ASSUME_NONNULL_BEGIN

@protocol SKPPlayerProtocol;
@class SKPVideoPlayer;
@class SKPVideoPlayerOverlay;
@class SKPVideoPlayerConfig;

@protocol SKPAdVideoPlayerDelegate <NSObject>
- (void)SKPAdVideoPlayerDidTappedLearnMore:(SKPVideoPlayer *)videoPlayer;
- (void)SKPAdVideoPlayerDidTappedFullscreen:(SKPVideoPlayer *)videoPlayer;
- (NSTimeInterval)SKPAdVideoPlayerMinimumPlayTime:(SKPVideoPlayer *)videoPlayer;
- (BOOL)SKPAdVideoPlayerShouldShowRemainingTime:(SKPVideoPlayer *)videoPlayer;
- (void)SKPAdVideoPlayerError:(NSError*)error;
@optional
- (BOOL)SKPAdVideoPlayerShouldAutoPlay:(SKPVideoPlayer *)videoPlayer;
- (BOOL)SKPAdVideoPlayerShouldPlay:(SKPVideoPlayer *)videoPlayer;
- (void)SKPAdVideoPlayerDidTappedPlayButton:(SKPVideoPlayer *)videoPlayer wasPlaying:(BOOL)wasPlaying;
- (void)SKPAdVideoPlayerDidTappedMuteButton:(SKPVideoPlayer *)videoPlayer wasMuted:(BOOL)wasMuted;
- (void)SKPAdVideoPlayerWillStartPlayingVideo:(SKPVideoPlayer *)videoPlayer;
- (void)SKPAdVideoPlayerDidResumeVideo:(SKPVideoPlayer *)videoPlayer;
- (void)SKPAdVideoPlayerDidPassMinimumPlayTime:(SKPVideoPlayer *)videoPlayer atSecond:(NSTimeInterval)second;
- (void)SKPAdVideoPlayerDidFinishPlayingVideo:(SKPVideoPlayer *)videoPlayer atSecond:(NSTimeInterval)second;
- (void)SKPAdVideoPlayerDidPause:(SKPVideoPlayer *)videoPlayer atSecond:(NSTimeInterval)second;
- (void)SKPAdVideoPlayerWillReplayVideo:(SKPVideoPlayer *)videoPlayer;
@end

@interface SKPVideoPlayer : UIView

@property (nonatomic, weak) id<SKPAdVideoPlayerDelegate> delegate;
@property (nonatomic, readonly) BOOL isPlaying;
@property (nonatomic, readonly) BOOL isMuted;
@property (nonatomic, readonly) BOOL isFinished;
@property (nonatomic, readonly) id<SKPPlayerProtocol> skpAdPlayer;
@property (nonatomic, assign) AVLayerVideoGravity videoGravity;
@property (nonatomic, strong) SKPVideoPlayerOverlay *overlay;
@property (nonatomic, strong) SKPVideoPlayerConfig *config;

- (void)loadVideoAtUrl:(NSURL *)url autoPlay:(BOOL)autoPlay fromSecond:(NSTimeInterval)fromSecond mute:(BOOL)mute;
- (void)loadVideoWithBuzzPlayer:(id<SKPPlayerProtocol>)player authPlay:(BOOL)autoPlay;
- (void)loadVideoAtVastTag:(NSString *)vastTag autoPlay:(BOOL)autoPlay fromSecond:(NSTimeInterval)fromSecond mute:(BOOL)mute;

- (void)play;
- (void)startAutoPlay;
- (void)pause;
- (void)reset;
- (void)syncUI;
- (void)showControlForFullscreenMode;
- (void)hideControlForFullscreenMode;
- (void)detachView;

@end

NS_ASSUME_NONNULL_END
