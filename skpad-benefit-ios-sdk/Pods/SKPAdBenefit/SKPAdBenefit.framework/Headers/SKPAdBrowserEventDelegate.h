#import <Foundation/Foundation.h>

@protocol SKPAdBrowserEventDelegate <NSObject>

- (void)didBrowserOpened;
- (void)didBrowserClosed;
- (void)didPageLoaded;
- (void)didPageLoadError;
- (void)didUrlLoadingWithUrl:(NSURL *)url;
- (void)didDeepLinkOpened;
- (void)didLanding;

@end
