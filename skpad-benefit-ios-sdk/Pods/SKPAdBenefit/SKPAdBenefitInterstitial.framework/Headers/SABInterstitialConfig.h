#import <UIKit/UIKit.h>
#import <SKPAdBenefitNative/SABStateValue.h>

NS_ASSUME_NONNULL_BEGIN

@interface SABInterstitialConfig : NSObject

@property (nonatomic, strong) UIImage *topIcon;
@property (nonatomic, strong) NSString *titleText;
@property (nonatomic, strong) UIColor *titleTextColor;
@property (nonatomic, strong) UIColor *backgroundColor;
@property (nonatomic, assign) BOOL showInquiryButton;

@property (nonatomic, strong) SABStateValue<UIImage *> *ctaViewIcon;
@property (nonatomic, strong) SABStateValue<UIColor *> *ctaViewBackgroundColor;
@property (nonatomic, strong) SABStateValue<UIColor *> *ctaViewTextColor;

// BottomSheet only
@property (nonatomic, assign) NSUInteger adCount;

//fullscreen only
@property (nonatomic, strong, nullable) Class adViewHolderClass;
@property (nonatomic, strong, nullable) Class errorViewHolderClass;
@property (nonatomic, assign) BOOL useNavigationPush; //default : present
@end

NS_ASSUME_NONNULL_END
