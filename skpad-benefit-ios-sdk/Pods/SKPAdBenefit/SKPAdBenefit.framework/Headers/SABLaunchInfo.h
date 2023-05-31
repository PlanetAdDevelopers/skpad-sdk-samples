#import <Foundation/Foundation.h>

@class SABAd;
@class SABArticle;

NS_ASSUME_NONNULL_BEGIN

@interface SABLaunchInfo : NSObject

@property (nonatomic, copy, readonly) NSURL *url;
@property (nonatomic, copy, nullable, readonly) SABAd *ad;
@property (nonatomic, copy, nullable, readonly) SABArticle *article;
@property (nonatomic, assign, readonly) BOOL shouldLandingExternalBrowser;

- (instancetype)initWithURL:(NSURL *)url ad:(nullable SABAd *)ad article:(nullable SABArticle *)article shouldLandingExternalBrowser:(BOOL)shouldLandingExternalBrowser;

@end

NS_ASSUME_NONNULL_END
