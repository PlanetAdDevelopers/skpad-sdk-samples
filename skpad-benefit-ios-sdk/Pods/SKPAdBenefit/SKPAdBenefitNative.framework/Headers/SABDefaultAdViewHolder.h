#import <UIKit/UIKit.h>
#import "SABAdViewHolder.h"
#import "SABStateValue.h"

NS_ASSUME_NONNULL_BEGIN

@interface SABDefaultAdViewHolder : SABAdViewHolder

@property (nonatomic, strong) SABStateValue<UIImage *> *ctaViewIcon;
@property (nonatomic, strong) SABStateValue<UIColor *> *ctaViewBackgroundColor;
@property (nonatomic, strong) SABStateValue<UIColor *> *ctaViewTextColor;

@property (nonatomic, assign) CGFloat detailViewPadding;
@property (nonatomic, assign) CGFloat mediaViewCornerRadius;

+ (NSString *)nibName;

@end

NS_ASSUME_NONNULL_END
