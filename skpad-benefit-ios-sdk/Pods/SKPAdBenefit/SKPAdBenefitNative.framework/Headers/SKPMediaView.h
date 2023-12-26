//
//  SKPMediaView.h
//  SKPMediaView
//
//  Created by Jaehee Ko on 26/12/2018.
//  Copyright © 2018 Buzzvil. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SKPVideoPlayer.h"
#import "SKPImpressionTrackableView.h"

typedef enum {
    SKPAdMediaViewAspectFit,
    SKPAdMediaViewAspectFill
} SKPAdMediaViewFillMode;

@protocol SKPPlayerProtocol;

@protocol SKPMediaViewDelegate <NSObject>
- (void)SKPMediaViewDidClickURL:(NSURL*)clickUrl;
@end

@interface SKPMediaView : UIView  <SKPImpressionTrackableView>

@property (nonatomic, weak) id<SKPMediaViewDelegate> clickDelegate;

@property (nonatomic, readonly) SKPVideoPlayer *videoPlayer;
@property (nonatomic, assign) SKPAdMediaViewFillMode fillMode;

- (void)loadImageAtUrl:(NSURL *)url;
- (void)loadVideoAtUrl:(NSURL *)url autoPlay:(BOOL)autoPlay thumbnailImageUrl:(NSURL *)thumbnailImageUrl fromSecond:(NSTimeInterval)fromSecond mute:(BOOL)mute;
- (void)loadVideoWithBuzzPlayer:(id<SKPPlayerProtocol>)player autoPlay:(BOOL)autoPlay thumbnailImageUrl:(NSURL *)thumbnailImageUrl;
- (void)loadVideoAtVastTag:(NSString *)vastTag autoPlay:(BOOL)autoPlay thumbnailImageUrl:(NSURL *)thumbnailImageUrl fromSecond:(NSTimeInterval)fromSecond mute:(BOOL)mute;

//jylee webjs 추가
- (void)loadWebAtHtmlTag:(NSString *)htmltag bgImageUrl:(NSURL*)bgImageUrl bgColor:(UIColor *)bgColor;

- (void)didEnterFullscreen;
- (void)didExitFullscreen;

@end
