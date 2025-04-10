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

@class SABAd;

typedef enum {
    SKPAdMediaViewAspectFit,
    SKPAdMediaViewAspectFill
} SKPAdMediaViewFillMode;

@protocol SKPPlayerProtocol;

@protocol SKPMediaViewDelegate <NSObject>
- (void)SKPMediaViewDidClickURL:(NSURL*)clickUrl;
- (void)SKPMediaViewDidExtractedBackgroundColor:(UIColor*)bgColor;

@end

@interface SKPMediaView : UIView  <SKPImpressionTrackableView>

@property (nonatomic, weak) id<SKPMediaViewDelegate> delegate;

@property (nonatomic, readonly) SKPVideoPlayer *videoPlayer;
@property (nonatomic, assign) SKPAdMediaViewFillMode fillMode;

- (void)loadImageAtUrl:(NSURL *)url ad:(SABAd*)ad;
- (void)loadVideoAtUrl:(NSURL *)url autoPlay:(BOOL)autoPlay thumbnailImageUrl:(NSURL *)thumbnailImageUrl fromSecond:(NSTimeInterval)fromSecond mute:(BOOL)mute ad:(SABAd*)ad;
- (void)loadVideoWithBuzzPlayer:(id<SKPPlayerProtocol>)player autoPlay:(BOOL)autoPlay thumbnailImageUrl:(NSURL *)thumbnailImageUrl ad:(SABAd*)ad;
- (void)loadVideoAtVastTag:(NSString *)vastTag autoPlay:(BOOL)autoPlay thumbnailImageUrl:(NSURL *)thumbnailImageUrl fromSecond:(NSTimeInterval)fromSecond mute:(BOOL)mute ad:(SABAd*)ad;
- (void)loadWebAtHtmlTag:(NSString *)htmltag bgImageUrl:(NSURL*)bgImageUrl bgColor:(UIColor *)bgColor ad:(SABAd*)ad;

- (void)didEnterFullscreen;
- (void)didExitFullscreen;

@end
