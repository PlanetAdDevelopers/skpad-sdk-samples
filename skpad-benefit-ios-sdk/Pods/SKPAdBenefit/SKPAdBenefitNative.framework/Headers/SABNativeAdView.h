#import <UIKit/UIKit.h>
#import <SKPAdBenefit/SABRewardResult.h>
#import "SABNativeAdViewProtocol.h"
#import "SKPImpressionTrackableView.h"

NS_ASSUME_NONNULL_BEGIN

@class SABAd;
@class SABNativeAdView;
@class SABMediaView;
@class SABAdInfoView;
@class SABInquiryView;

@protocol SABNativeAdViewDelegate <NSObject>

- (void)SABNativeAdView:(SABNativeAdView *)adView willRequestRewardForAd:(SABAd *)ad;
- (void)SABNativeAdView:(SABNativeAdView *)adView didRewardForAd:(SABAd *)ad withResult:(SABRewardResult)result;
- (void)SABNativeAdView:(SABNativeAdView *)adView didParticipateAd:(SABAd *)ad;

@optional
- (UIViewController *)SABNativeAdViewViewControlleForPresentingFullscreen;
- (void)SABNativeAdView:(SABNativeAdView *)adView didImpressAd:(SABAd *)ad;
- (BOOL)SABNativeAdView:(SABNativeAdView *)adView shouldClickAd:(SABAd *)ad;
- (void)SABNativeAdView:(SABNativeAdView *)adView didClickAd:(SABAd *)ad;
- (void)SABNativeAdView:(SABNativeAdView *)adView didWillOpenLandingPageForAd:(SABAd *)ad;

@end

@protocol SABNativeAdViewVideoDelegate <NSObject>
@optional
- (void)SABNativeAdViewWillStartPlayingVideo:(SABNativeAdView *)adView;
- (void)SABNativeAdViewDidResumeVideo:(SABNativeAdView *)adView;
- (void)SABNativeAdViewWillReplayVideo:(SABNativeAdView *)adView;
- (void)SABNativeAdViewDidPauseVideo:(SABNativeAdView *)adView;
- (void)SABNativeAdView:(SABNativeAdView *)adView didFinishPlayingVideoAd:(SABAd *)ad;
- (void)SABNativeAdView:(SABNativeAdView *)adView didVideoError:(NSError *)error;
@end

@interface SABNativeAdView : UIView <SABNativeAdViewProtocol, SKPImpressionTrackableView>

@property (nonatomic, weak) id<SABNativeAdViewDelegate> delegate;
@property (nonatomic, weak) id<SABNativeAdViewVideoDelegate> videoDelegate;
@property (nonatomic, strong) SABMediaView *mediaView;
@property (nonatomic, strong) NSArray<UIView *> *clickableViews;
@property (nonatomic, strong) SABAd *ad;

@property (nonatomic, strong) SABAdInfoView *adInfoView;
@property (nonatomic, strong) SABInquiryView *inquiryView;

- (BOOL) isReloadBlocked;

@end

NS_ASSUME_NONNULL_END
