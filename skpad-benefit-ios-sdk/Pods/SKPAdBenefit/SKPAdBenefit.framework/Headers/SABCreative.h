#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

typedef enum {
    SABCreativeLandingDefaultBrowser = 1,
    SABCreativeLandingOverlay,
    SABCreativeLandingInApp,
    SABCreativeLandingYoutubePlayer = 10
} SABCreativeLandingType;

typedef enum {
    SABCreativeUnsupported = 0,
    SABCreativeNative,
    SABCreativeVideo,
    SABCreativeVast,
    SABCreativeWebBanner,
} SABCreativeType;

typedef enum {
    SABCreativeTemplateVertical = 0,
    SABCreativeTemplateLandscape
} SABCreativeTemplate;

typedef enum {
    SABCreativeVideoAutoplayDisabled = 1,
    SABCreativeVideoAutoplayEnabled,
    SABCreativeVideoAutoplayOnWifi
} SABCreativeVideoAutoplayMode;

@class SABVideoAdMetadata;

@interface SABCreative : NSObject

@property (nonatomic, assign, readonly) double width;
@property (nonatomic, assign, readonly) double height;
@property (nonatomic, copy, readonly, nullable) NSString *clickUrl;
//jylee remove pub_id : new reward api
@property (nonatomic, copy, readonly, nullable) NSString *pbUrl;
@property (nonatomic, copy, readonly, nullable) NSString *title;
@property (nonatomic, copy, readonly, nullable) NSString *body;
@property (nonatomic, copy, readonly, nullable) NSString *callToAction;
@property (nonatomic, copy, readonly, nullable) NSString *iconUrl;
@property (nonatomic, copy, readonly, nullable) NSString *imageUrl;
@property (nonatomic, copy, readonly, nullable) NSString *overlayImageUrl;
@property (nonatomic, copy, readonly, nullable) NSString *streamingVideoUrl;
@property (nonatomic, copy, readonly, nullable) NSString *vastTag;
@property (nonatomic, copy, readonly, nullable) NSString *vastClickUrl;
@property (nonatomic, copy, readonly, nullable) NSArray<NSString *> *vastClickTrackings;
@property (nonatomic, copy, readonly, nullable) NSString *adNetworkCampaignId;
@property (nonatomic, assign, readonly) BOOL isDeeplink;
@property (nonatomic, assign, readonly) SABCreativeVideoAutoplayMode autoplay;
@property (nonatomic, assign, readonly) SABCreativeTemplate templateType;
@property (nonatomic, assign, readonly) SABCreativeType type;
@property (nonatomic, assign, readonly) SABCreativeLandingType landingType;

@property (nonatomic, strong, nullable) NSString *encodedClickUrl;
//jylee encodedClickUrlAfterRewarded : 참여 완료 후에 click_url에 reward를 0으로 보내야 헤서 추가로 만들었음. (220826)
@property (nonatomic, strong, nullable) NSString *encodedClickUrlAfterRewarded;
@property (nonatomic, strong, nullable) SABVideoAdMetadata *videoAdMetadata;

//jylee webjs 추가
@property (nonatomic, copy, readonly, nullable) NSString *bgImageUrl; //webbanner는 사용안함. (추후 banner타입일 때 사용할 수 있어서 남겨둠)
@property (nonatomic, copy, readonly, nullable) NSString *htmlTag;

- (instancetype)initWithDictionary:(NSDictionary *)dic;

@end

NS_ASSUME_NONNULL_END
