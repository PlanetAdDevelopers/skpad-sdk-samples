#import <UIKit/UIKit.h>
#import "SABFeedViewController.h"

@class SABFeedConfig;
@class SABError;

@class SABNewFeedViewController;

NS_ASSUME_NONNULL_BEGIN

@interface SABFeedHandler : NSObject

@property (nonatomic, readonly) NSUInteger adsCount;
@property (nonatomic, readonly) double availableReward;

- (instancetype)initWithConfig:(SABFeedConfig *)config;
- (void)preloadWithOnSuccess:(void (^)(void))onSuccess onFailure:(void (^)(SABError *error))onFailure;
- (SABFeedViewController *)populateViewController;

@end

NS_ASSUME_NONNULL_END
