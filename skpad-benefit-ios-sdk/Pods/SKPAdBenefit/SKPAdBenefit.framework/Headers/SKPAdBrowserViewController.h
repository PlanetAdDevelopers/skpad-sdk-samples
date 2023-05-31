#import <UIKit/UIKit.h>

@class SKPLandingInfo;
@protocol SKPAdBrowserEventDelegate;

@interface SKPAdBrowserViewController : UIViewController

- (instancetype)initWithLandingInfo:(SKPLandingInfo *)landingInfo
                      eventDelegate:(id<SKPAdBrowserEventDelegate>) delegate;

@end
