#import <UIKit/UIKit.h>

@class SABFeedConfig;
@class SABFeedProvider;
@class SABFeedHeaderView;

NS_ASSUME_NONNULL_BEGIN

@interface SABFeedViewController : UIViewController

@property (nonatomic) SABFeedConfig *config;
@property (nonatomic, strong) SABFeedProvider *provider;

@property (nonatomic, assign) BOOL shouldOverrideTopInset;
@property (nonatomic, assign) CGFloat topInset;

- (void)scrollToTop;

@end

NS_ASSUME_NONNULL_END
