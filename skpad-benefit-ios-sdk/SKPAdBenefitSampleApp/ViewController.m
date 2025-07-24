//
//  ViewController.m
//  SKPAdBenefitSampleApp
//
//  Created by SK Planet on 2022/08/02.
//

#import "ViewController.h"
#import <SKPAdBenefit/SKPAdBenefit.h>
#import <SKPAdBenefitNative/SKPAdBenefitNative.h>
#import <SKPAdBenefitInterstitial/SKPAdBenefitInterstitial.h>
#import <SKPAdBenefitFeed/SKPAdBenefitFeed.h>
#import <AdSupport/ASIdentifierManager.h>
#import <MessageUI/MessageUI.h>
#import <Toast/Toast.h>
#import <SDWebImage/UIImageView+WebCache.h>
#import "WebViewController.h"

#define kDefaultNativeUnitId @"323370048750095"
#define kDefaultFeedUnitId   @"522571474749710"
#define kDefaultInterstitialUnitId @"228405186298966"
#define websdk_url @"https://m.planetad.co.kr/pages/integration/websdk.html"

@interface ViewController () <SABNativeAdViewDelegate, SABNativeAdViewVideoDelegate, SABNativeAdViewUIDelegate, SABInterstitialAdHandlerDelegate>

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    NSLog(@"SDK VERSION : %@", [SKPAdBenefit getSDKVersion]);
    
    _adView.delegate = self;
    _adView.videoDelegate = self;
    _adView.uiDelegate = self;
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(sessionRegistered:) name:SABSessionRegisteredNotification object:nil];
}

- (BOOL)shouldAutorotate {
    return NO;
}

- (UIInterfaceOrientationMask)supportedInterfaceOrientations {
    return UIInterfaceOrientationMaskPortrait;
}

//로그인
- (IBAction)loginButtonTapped:(id)sender {
    if (SKPAdBenefit.sharedInstance.userProfile.isSessionRegistered) {
        [self logout];
    } else {
        SABUserProfile *userProfile = [[SABUserProfile alloc] initWithUserId:@"USER_ID"
                                                                   birthYear:1995
                                                                      gender:SABUserGenderMale];
        [SKPAdBenefit setUserProfile:userProfile];
    }
}
//로그아웃
- (void)logout {
    [SKPAdBenefit setUserProfile:nil];
    [_loginButton setTitle:@"LOGIN"];
}

- (void)sessionRegistered:(NSNotification *)notification {
    [_loginButton setTitle:@"LOGOUT"];
    [self.navigationController.view.window makeToast:@"Session registered"];
}

#pragma mark - NativeType
//Native Type 광고 할당 및 표시하기
- (IBAction)loadNativeButtonTapped:(id)sender {
    SABAdLoader *adLoader = [[SABAdLoader alloc] initWithUnitId:kDefaultNativeUnitId];
    [adLoader loadAdWithOnSuccess:^(SABAd * _Nonnull ad) {
        self.adView.hidden = NO;
        [self.container bringSubviewToFront:self.adView];

        //할당된 광고가 있으면 호출됩니다.
        //광고를 바인딩 합니다.
        self.titleLabel.text = ad.creative.title;
        self.descriptionLabel.text = ad.creative.body;
        [self.iconImageView sd_setImageWithURL:[NSURL URLWithString:ad.creative.iconUrl]];
        
        if (ad.isParticipated || ad.reward <= 0) {
            [self.ctaButton setTitle:[NSString stringWithFormat:@"%@", ad.creative.callToAction] forState:UIControlStateNormal];
        } else {
            [self.ctaButton setTitle:[NSString stringWithFormat:@"+%d %@", (int)(ad.reward), ad.creative.callToAction] forState:UIControlStateNormal];
        }
        
        self.adView.ad = ad;
        self.adView.mediaView = self.mediaView;
        self.adView.clickableViews = @[self.ctaButton, self.iconImageView, self.mediaView];
        self.adView.adInfoView = self.adInfoView;
        
    } onFailure:^(SABError *error) {
        self.adView.hidden = YES;

        //할당된 광고가 없으면 호출됩니다.
        NSString *errorMsg;
        switch(error.code) {
            case SABUnknownError:
                errorMsg = @"Unknown";
                break;
            case SABServerError:
                errorMsg = @"ServerError";
                break;
            case SABInvalidRequest:
                errorMsg = @"InvalidRequest";
                break;
            case SABRequestTimeout:
                errorMsg = @"Timeout";
                break;
            case SABEmptyResponse:
                errorMsg = @"EmptyResponse";
                break;
            default:
                errorMsg = @"Unknown";
                break;
        }
        NSString * msg = [NSString stringWithFormat:@"Ad load failed with error: %@", errorMsg];
        NSLog(@"%@", msg);
    }];
}

#pragma mark - SABNativeAdViewDelegate - 네이티브 광고 이벤트 리스너 등록하기
- (void)SABNativeAdView:(SABNativeAdView *)adView didImpressAd:(SABAd *)ad {
    [self.view.window makeToast:[NSString stringWithFormat:@"\"%@\" impressed", ad.creative.title]];
}

- (BOOL)SABNativeAdView:(SABNativeAdView *)adView shouldClickAd:(SABAd *)ad {
    [self.view.window makeToast:[NSString stringWithFormat:@"\"%@\" should click: %@", ad.creative.title, _shouldClickSwitch.isOn ? @"YES" : @"NO"]];
    return _shouldClickSwitch.isOn;
}

- (void)SABNativeAdView:(SABNativeAdView *)adView didClickAd:(SABAd *)ad {
    [self.view.window makeToast:[NSString stringWithFormat:@"\"%@\" clicked", ad.creative.title]];
}

- (void)SABNativeAdView:(SABNativeAdView *)adView willRequestRewardForAd:(SABAd *)ad {
    [self.view.window makeToast:[NSString stringWithFormat:@"\"%@\" reward request started", ad.creative.title]];
}

- (void)SABNativeAdView:(SABNativeAdView *)adView didRewardForAd:(SABAd *)ad withResult:(SABRewardResult)result {
    switch (result) {
        case SABRewardResultSuccess:
            [self.view.window makeToast:[NSString stringWithFormat:@"\"%@\" rewarded", ad.creative.title]];
            break;
        case SABRewardResultBrowserNotLaunched:
            [self.view.window makeToast:[NSString stringWithFormat:@"\"%@\" browser not launched", ad.creative.title]];
            break;
        case SABRewardResultTooShortToParticipate:
            [self.view.window makeToast:[NSString stringWithFormat:@"\"%@\" too short to participate", ad.creative.title]];
            break;
        case SABRewardResultAlreadyParticipated:
            [self.view.window makeToast:[NSString stringWithFormat:@"\"%@\" already participated", ad.creative.title]];
            break;
        default:
            [self.view.window makeToast:[NSString stringWithFormat:@"\"%@\" reward failed: %d", ad.creative.title, result]];
            break;
    }
}
- (void)SABNativeAdView:(SABNativeAdView *)adView didParticipateAd:(SABAd *)ad {
    if ([ad isClicked] && [ad isActionType] && ![ad isParticipated]) {
        [self.ctaButton setTitle:@"참여 확인 중" forState:UIControlStateNormal];
    } else if (ad.isParticipated || ad.reward <= 0) {
        [self.ctaButton setTitle:@"참여 완료" forState:UIControlStateNormal];
    } else {
        [self.ctaButton setTitle:[NSString stringWithFormat:@"+%d %@", (int)(ad.reward), ad.creative.callToAction] forState:UIControlStateNormal];
    }
}

#pragma mark - SABNativeAdViewVideoDelegate - 네이티브 비디오 광고 리스너 등록하기
- (void)SABNativeAdViewWillStartPlayingVideo:(SABNativeAdView *)adView {
}
- (void)SABNativeAdViewDidResumeVideo:(SABNativeAdView *)adView {
}
- (void)SABNativeAdViewWillReplayVideo:(SABNativeAdView *)adView {
}
- (void)SABNativeAdViewDidPauseVideo:(SABNativeAdView *)adView {
}
- (void)SABNativeAdView:(SABNativeAdView *)adView didFinishPlayingVideoAd:(SABAd *)ad {
}
- (void)SABNativeAdView:(SABNativeAdView *)adView didVideoError:(NSError *)error {
}

#pragma mark - SABNativeAdViewUIDelegate - 네이티브 UI 리스너 등록하기
- (void)SABNativeAdView:(SABNativeAdView *)adView didExtractedBackgroundColor:(UIColor *)bgColor {
    _adView.backgroundColor = bgColor;
}

#pragma mark - FeedType
- (IBAction)loadFeedButtonTapped:(id)sender {
    SABFeedConfig *feedConfig = [[SABFeedConfig alloc] initWithUnitId:kDefaultFeedUnitId];
    
    SABFeedHandler *feedHandler = [[SABFeedHandler alloc] initWithConfig:feedConfig];
    [self.navigationController pushViewController:[feedHandler populateViewController] animated:YES];
}

#pragma mark - Interstitial
//Dialog
- (IBAction)loadInterstitialButtonTapped:(id)sender {
    SABInterstitialConfig *interstitialConfig = [[SABInterstitialConfig alloc] init];
    
    SABInterstitialAdHandler *adLoader = [[SABInterstitialAdHandler alloc] initWithUnitId:kDefaultInterstitialUnitId
                                                                                     type:SABInterstitialDialog];
    adLoader.delegate = self;
    [adLoader show:self withConfig:interstitialConfig];
}

//BottomSheet
- (IBAction)loadBottomSheetButtonTapped:(id)sender {
    SABInterstitialConfig *interstitialConfig = [[SABInterstitialConfig alloc] init];
    
    SABInterstitialAdHandler *adLoader = [[SABInterstitialAdHandler alloc] initWithUnitId:kDefaultInterstitialUnitId
                                                                                     type:SABInterstitialBottomSheet];
    adLoader.delegate = self;
    [adLoader show:self withConfig:interstitialConfig];
}

//FullScreen
- (IBAction)loadFullScreenButtonTapped:(id)sender {
    SABInterstitialConfig *interstitialConfig = [[SABInterstitialConfig alloc] init];
    
    SABInterstitialAdHandler *adLoader = [[SABInterstitialAdHandler alloc] initWithUnitId:kDefaultInterstitialUnitId
                                                                                     type:SABInterstitialFullScreen];
    adLoader.delegate = self;
    [adLoader show:self withConfig:interstitialConfig];
}

#pragma mark - SABInterstitialAdHandlerDelegate - Interstitial 광고 이벤트 리스너 등록하기
- (void)SABInterstitialAdHandlerDidSucceedLoadingAd:(SABInterstitialAdHandler *)adLoader {
}

- (void)SABInterstitialAdHandler:(SABInterstitialAdHandler *)adLoader didFailToLoadAdWithError:(SABError *)error {
}

- (void)SABInterstitialViewControllerDidFinish:(UIViewController *)viewController {
}

#pragma mark - Web SDK
- (IBAction)loadTestWebPage:(id)sender {
    WebViewController *webViewController = [[WebViewController alloc] init];
    NSString *samplePageUrl = websdk_url;
    webViewController.url = [NSURL URLWithString:samplePageUrl];
    [self.navigationController pushViewController:webViewController animated:YES];
}
@end
