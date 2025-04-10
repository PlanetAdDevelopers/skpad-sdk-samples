#import <UIKit/UIKit.h>

@class SABInterstitialConfig;
@class SABInterstitialAdHandler;
@class SABError;

NS_ASSUME_NONNULL_BEGIN

typedef enum {
    SABInterstitialDialog,
    SABInterstitialBottomSheet,
    SABInterstitialFullScreen,
} SABInterstitialType;

@protocol SABInterstitialAdHandlerDelegate <NSObject>
- (void)SABInterstitialAdHandler:(SABInterstitialAdHandler *)adLoader didFailToLoadAdWithError:(SABError *)error;
- (void)SABInterstitialAdHandlerDidSucceedLoadingAd:(SABInterstitialAdHandler *)adLoader;
@optional
- (void)SABInterstitialViewControllerDidFinish:(UIViewController *)viewController;
@end

@interface SABInterstitialAdHandler : NSObject

@property (nonatomic, weak) id<SABInterstitialAdHandlerDelegate> delegate;

- (instancetype)initWithUnitId:(NSString *)unitId type:(SABInterstitialType)type;
- (void)show:(UIViewController *)presentingViewController withConfig:(nullable SABInterstitialConfig *)config;

- (void)preloadFullScreenWithConfig:(SABInterstitialConfig *)config onSuccess:(void (^)(NSUInteger adsCount, double totalReward))onSuccess onFailure:(void (^)(SABError *))onFailure;
- (void)resetData;
@end

NS_ASSUME_NONNULL_END
