NS_ASSUME_NONNULL_BEGIN

@class SABAd;
@class SABLaunchInfo;
@protocol SABLauncher;

typedef enum {
    SABLauncherStatusPageLoadFailed = 0,
    SABLauncherStatusLandingConditionNotSatisfied,
    SABLauncherStatusDeepLinkOpened
} SABLauncherStatus;

@protocol SKPAdLauncherEventDelegate <NSObject>

- (void)launcher:(id<SABLauncher>)launcher didLandingSucceededResponse:(SABLaunchInfo *)launchInfo;

- (void)launcher:(id<SABLauncher>)launcher didLandingFailureResponse:(SABLaunchInfo *)launchInfo status:(SABLauncherStatus)status;

- (void)launcher:(id<SABLauncher>)launcher didOpeningExternalBrowserResponse:(SABLaunchInfo *)launchInfo;

@end

@protocol SABLauncher <NSObject>

- (void)openWithLaunchInfo:(SABLaunchInfo *)launchInfo;

- (void)openWithLaunchInfo:(SABLaunchInfo *)launchInfo delegate:(nullable id<SKPAdLauncherEventDelegate>)delegate;

@end

NS_ASSUME_NONNULL_END
