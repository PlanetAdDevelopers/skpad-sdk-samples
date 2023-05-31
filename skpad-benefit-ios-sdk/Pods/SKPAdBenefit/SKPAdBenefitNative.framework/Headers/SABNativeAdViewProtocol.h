#import <UIKit/UIKit.h>
#import <SKPAdBenefit/SABRewardResult.h>

@class SKPMediaView;

@protocol SABNativeAdViewProtocol <NSObject>

- (void)renderImageMediaAtUrl:(NSURL *)url;
- (void)renderVideoMediaAtUrl:(NSURL *)url autoPlay:(BOOL)autoPlay thumbnailImageUrl:(NSURL *)thumbnailImageUrl fromSecond:(NSTimeInterval)fromSecond mute:(BOOL)mute;
- (void)renderVideoMediaAtVastTag:(NSString *)vastTag autoPlay:(BOOL)autoPlay thumbnailImageUrl:(NSURL *)url fromSecond:(NSTimeInterval)fromSecond mute:(BOOL)mute;

- (void)switchToFullscreen;

- (void)handleImpression;
- (void)handleClick;
- (void)handleVideoPlayFinish;
- (void)handleRewardSuccess;
- (void)handleRewardStart;
- (void)handleRewardFailure:(SABRewardResult)result;
- (void)handleParticipation;
- (void)handleLandingPageOpen;
@end
