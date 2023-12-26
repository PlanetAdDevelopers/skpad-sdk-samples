#import <UIKit/UIKit.h>
#import <SKPAdBenefit/SABArticle.h>

NS_ASSUME_NONNULL_BEGIN

@interface SABFeedConfig : NSObject <NSMutableCopying>

@property (nonatomic, copy, readonly) NSString *unitId;
@property (nonatomic, copy) NSString *title;
@property (nonatomic, strong) Class headerViewClass;
@property (nonatomic, strong, nullable) Class adViewHolderClass;
@property (nonatomic, strong) Class articleViewHolderClass;
@property (nonatomic, strong) Class errorViewHolderClass;
@property (nonatomic, assign) BOOL articlesEnabled;
@property (nonatomic, assign) BOOL shouldShowAppTrackingTransparencyDialog;
@property (nonatomic, assign) BOOL shouldShowAppTrackingTransparencyGuideBanner;
@property (nonatomic, strong, nullable) NSArray<SABArticleCategoryName> *articleCategories;
@property (nonatomic, assign) CGFloat separatorHeight;
@property (nonatomic, strong) UIColor *separatorColor;
@property (nonatomic, assign) CGFloat separatorHorizontalMargin;
@property (nonatomic, strong) UIImage *inquiryButtonIcon;
@property (nonatomic, strong) UIImage *closeButtonIcon;
@property (nonatomic, strong) UIFont *titleFont;
@property (nonatomic, assign) CGFloat topBarHeight;

@property (nonatomic, strong) UIColor *tabDefaultColor;
@property (nonatomic, strong) UIColor *tabSelectedColor;
@property (nonatomic, strong) UIColor *tabBackgroundColor;
@property (nonatomic, strong) NSArray<NSString *> *tabTextArray;

@property (nonatomic, strong) UIColor *filterBackgroundDefaultColor;
@property (nonatomic, strong) UIColor *filterBackgroundSelectedColor;
@property (nonatomic, strong) UIColor *filterTextDefaultColor;
@property (nonatomic, strong) UIColor *filterTextSelectedColor;
@property (nonatomic, assign) BOOL showInquiryButton;

- (instancetype)initWithUnitId:(NSString *)unitId;

@end

NS_ASSUME_NONNULL_END
