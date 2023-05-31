//2023.05.02 09:00

#import <Foundation/Foundation.h>
#import <SKPAdBenefit/SABConfig.h>
#import <SKPAdBenefit/SABUserProfile.h>
#import <SKPAdBenefit/SABUserPreference.h>
#import <SKPAdBenefit/SABAdLoader.h>
#import <SKPAdBenefit/SABAd.h>
#import <SKPAdBenefit/SABAdLoaderParams.h>
#import <SKPAdBenefit/SABCreative.h>
#import <SKPAdBenefit/SABVideoAdMetadata.h>
#import <SKPAdBenefit/SABVideoAdMetadataLoader.h>
#import <SKPAdBenefit/SABTracker.h>
#import <SKPAdBenefit/SABReachability.h>
#import <SKPAdBenefit/SABLauncher.h>
#import <SKPAdBenefit/SABLaunchInfo.h>
#import <SKPAdBenefit/SABArticleLoader.h>
#import <SKPAdBenefit/SABArticle.h>
#import <SKPAdBenefit/SABRewardHandler.h>
#import <SKPAdBenefit/SABLogger.h>
#import <SKPAdBenefit/SABError.h>
#import <SKPAdBenefit/SABDeviceInfo.h>
#import <SKPAdBenefit/SABSession.h>
#import <SKPAdBenefit/SABPointConfig.h>
#import <SKPAdBenefit/SABUnitSetting.h>
#import <SKPAdBenefit/SABUnitManager.h>
#import <SKPAdBenefit/SKPAdBrowser.h>
#import <SKPAdBenefit/SKPAdBrowserViewController.h>
#import <SKPAdBenefit/SKPAdBrowserEventDelegate.h>
#import <SKPAdBenefit/SKPLandingInfo.h>
#import <SKPAdBenefit/SKPEvent.h>
#import <SKPAdBenefit/SKPReward.h>

NS_ASSUME_NONNULL_BEGIN

@class SABConfig;
@class SABUserProfile;
@class SABUserPreference;
@class UIViewController;
@class SABError;

extern NSString *const SABSessionRegisteredNotification;

@interface SKPAdBenefit: NSObject

@property (nonatomic, readonly, nullable) SABConfig *config;
@property (nonatomic, readonly, nullable) SABUserProfile *userProfile;
@property (nonatomic, readonly, nullable) SABUserPreference *userPreference;
@property (nonatomic, readonly) id<SABLauncher> launcher;

+ (SKPAdBenefit *)sharedInstance;
+ (void)initializeWithConfig:(SABConfig *)config;
+ (void)setUserProfile:(nullable SABUserProfile *)userProfile;
+ (void)setUserProfile:(nullable SABUserProfile *)userProfile shouldShowAppTrackingTransparencyDialog:(BOOL)shouldShowAppTrackingTransparencyDialog;
+ (void)setUserPreference:(nullable SABUserPreference *)userPreference;
+ (void)setLauncher:(nullable id<SABLauncher>)launcher;
+ (void)showInquiryPageOnViewController:(UIViewController *)viewController;
- (void)getCurrentPointOnSuccess:(void (^)(int))onSuccess onFailure:(void (^)(NSError *))onFailure;
- (void)getPointConfigOnSuccess:(void (^)(SABPointConfig *))onSuccess onFailure:(void (^)(NSError *))onFailure;

+ (NSData*)getLogAttachementData;
+ (NSString *)getLogBodyData;
+ (void) removeSKPAdsLogFile;
+ (void) setServerType:(NSString *)serverType;
+ (NSString*) getSDKVersion;
@end

NS_ASSUME_NONNULL_END
