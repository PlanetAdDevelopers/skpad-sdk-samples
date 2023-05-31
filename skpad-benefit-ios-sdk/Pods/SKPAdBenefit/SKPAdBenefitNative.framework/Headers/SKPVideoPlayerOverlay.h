//
//  SKPVideoPlayerOverlay.h
//  SKPAdNative
//
//  Created by Jaehee Ko on 16/01/2020.
//  Copyright © 2020 Buzzvil. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@class SKPVideoPlayerOverlay;

@protocol SKPAdVideoPlayerOverlayDelegate <NSObject>
- (void)SKPAdVideoPlayerOverlayDidTappedPlayButton:(SKPVideoPlayerOverlay *)overlay;
- (void)SKPAdVideoPlayerOverlayDidTappedMuteButton:(SKPVideoPlayerOverlay *)overlay;
- (void)SKPAdVideoPlayerOverlayDidTappedFullscreenButton:(SKPVideoPlayerOverlay *)overlay;
- (void)SKPAdVideoPlayerOverlayDidTappedLearnMoreButton:(SKPVideoPlayerOverlay *)overlay;
@end

@interface SKPVideoPlayerOverlay : UIView

@property (nonatomic, strong) IBOutlet UIButton *playButton;
@property (nonatomic, strong) IBOutlet UIButton *muteButton;
@property (nonatomic, strong) IBOutlet UIButton *fullscreenButton;
@property (nonatomic, strong) IBOutlet UILabel *remainingTimeLabel;
@property (nonatomic, strong) IBOutlet UIButton *learnMoreButton;
@property (nonatomic, strong) IBOutlet UIButton *replayButton;
@property (nonatomic, weak) id<SKPAdVideoPlayerOverlayDelegate> delegate;

- (void)videoPlayTimeUpdated:(NSTimeInterval)currentPlayTime minimumTimeForReward:(NSTimeInterval)minimumTimeForReward videoLength:(NSTimeInterval)videoLength;

@end

NS_ASSUME_NONNULL_END
