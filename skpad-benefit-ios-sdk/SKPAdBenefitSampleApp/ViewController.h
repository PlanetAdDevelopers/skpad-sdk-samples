//
//  ViewController.h
//  SKPAdBenefitSampleApp
//
//  Created by SK Planet on 2022/08/02.
//

#import <UIKit/UIKit.h>

@class SABNativeAdView;
@class SABMediaView;
@class SABAdInfoView;
@class SABInquiryView;

@interface ViewController : UIViewController
@property (nonatomic, strong) IBOutlet SABNativeAdView *adView;
@property (nonatomic, strong) IBOutlet UILabel *titleLabel;
@property (nonatomic, strong) IBOutlet UILabel *descriptionLabel;
@property (nonatomic, strong) IBOutlet UIImageView *iconImageView;
@property (nonatomic, strong) IBOutlet UIButton *ctaButton;
@property (nonatomic, strong) IBOutlet SABMediaView *mediaView;
@property (nonatomic, strong) IBOutlet UISwitch *shouldClickSwitch;
@property (nonatomic, strong) IBOutlet UIBarButtonItem *loginButton;
@property (nonatomic, strong) IBOutlet UIView *container;
@property (nonatomic, strong) IBOutlet SABAdInfoView *adInfoView;
@property (nonatomic, strong) IBOutlet SABInquiryView *inquiryView;

@end

