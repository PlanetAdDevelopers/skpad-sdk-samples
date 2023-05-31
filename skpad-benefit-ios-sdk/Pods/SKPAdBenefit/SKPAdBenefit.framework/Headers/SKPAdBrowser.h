#import <UIKit/UIKit.h>

@class SKPLandingInfo;
@class SKPAdBrowserViewController;
@protocol SKPAdBrowserEventDelegate;

@interface SKPAdBrowser : NSObject

+ (SKPAdBrowser *)sharedInstance;

- (void)setLandingInfo:(SKPLandingInfo *)landingInfo;

- (void)open;
- (UIViewController *)browserViewController;

- (void)addBrowserEventDelegate:(id<SKPAdBrowserEventDelegate>)delegate;
- (void)removeBrowserEventDelegate:(id<SKPAdBrowserEventDelegate>)delegate;

@end
