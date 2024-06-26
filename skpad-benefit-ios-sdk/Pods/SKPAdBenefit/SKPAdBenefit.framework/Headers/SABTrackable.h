//
//  SABTrackable.h
//  SABAd
//
//  Created by Jaehee Ko on 20/03/2019.
//  Copyright © 2019 Buzzvil. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@protocol SABTrackable <NSObject>

- (NSString *)identifier;

- (BOOL)isImpressed;
- (void)setAsImpressed;
- (NSArray<NSString *> *)impressionTrackingUrls;

- (BOOL)isClicked;
- (void)setAsClicked;
- (NSArray<NSString *> *)clickTrackingUrls;

@optional
- (BOOL)isParticipated;
- (void)setAsParticipated;

- (BOOL)isRewarded;
- (void)setAsRewarded;

- (NSString *)playtimeTrackingUrl;
- (NSDictionary *)playtimeTrackingParams;

- (NSTimeInterval)playtime;
- (void)setPlaytime:(NSTimeInterval)playtime;

- (BOOL)isVideoMuted;
- (void)setVideoAsMuted:(BOOL)muted;

- (BOOL)isVideoStoppedByUserAction;
- (void)setVideoAsStoppedByUserAction:(BOOL)byUserAction;

- (NSArray<NSString *> *)vastClickTrackingUrls;

- (BOOL)isBlocked;
- (void)setAsBlocked;

- (BOOL)isVideoPlayed;
- (void)setVideoAsPlayed;

- (BOOL)isVideoFinished;
- (void)setVideoAsFinished;

@end

NS_ASSUME_NONNULL_END
