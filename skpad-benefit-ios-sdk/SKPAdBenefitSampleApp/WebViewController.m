//
//  WebViewController.m
//  SKPAdBenefitSampleApp
//
//  Created by SK Planet on 2022/08/02.
//

#import <WebKit/WebKit.h>
#import "WebViewController.h"
#import <SKPAdBenefit/SKPAdBenefit.h>
#import <SKPAdBenefitWebInterface/SKPAdBenefitWebInterface.h>

@interface WebViewController () <WKScriptMessageHandler> {
    WKWebView *_webView;
    SABWebInterface *_webInterface;
}

@end

@implementation WebViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    WKWebViewConfiguration *config = [[WKWebViewConfiguration alloc] init];
    WKUserContentController *contentController = [[WKUserContentController alloc] init];
    [contentController addScriptMessageHandler:self name:SKPAdBenefitWebInterfaceName];
    config.userContentController = contentController;
    
    _webView = [[WKWebView alloc] initWithFrame:self.view.bounds configuration:config];
    _webView.allowsBackForwardNavigationGestures = YES;
    [self.view addSubview:_webView];
    
    _webInterface = [[SABWebInterface alloc] initWithWebView:_webView];
    
    if (_url) {
        NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:_url cachePolicy:NSURLRequestReloadIgnoringCacheData timeoutInterval:10.0f];
        [_webView loadRequest:request];
    }
}

- (void)userContentController:(WKUserContentController *)userContentController didReceiveScriptMessage:(WKScriptMessage *)message {
    [_webInterface handleScriptMessage:message];
}

@end
