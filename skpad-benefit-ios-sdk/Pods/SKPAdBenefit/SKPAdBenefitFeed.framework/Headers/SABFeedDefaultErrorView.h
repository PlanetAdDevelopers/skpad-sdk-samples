#import "SABFeedErrorView.h"

NS_ASSUME_NONNULL_BEGIN

@interface SABFeedDefaultErrorView : SABFeedErrorView

@property (nonatomic, copy) UIImage *errorImage;
@property (nonatomic, copy) NSString *errorTitleText;
@property (nonatomic, copy) NSString *errorDescriptionText;

@end

NS_ASSUME_NONNULL_END
