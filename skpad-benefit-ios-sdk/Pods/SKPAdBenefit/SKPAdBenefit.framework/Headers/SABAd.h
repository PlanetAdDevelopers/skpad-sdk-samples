#import <Foundation/Foundation.h>
#import "SABCreative.h"
#import "SABTrackable.h"
#import "SKPEvent.h"
#import "SABProduct.h"
#import "SABRevenueType.h"

NS_ASSUME_NONNULL_BEGIN

typedef enum {
    SABAdRewardStatusUnknown = 0,
    SABAdRewardReceived
} SABAdRewardStatus;

@interface SABAd : NSObject <SABTrackable>

@property (nonatomic, copy, readonly) NSString *Id; //campaign_id
@property (nonatomic, copy, readonly) NSString *name; //campaign_name
@property (nonatomic, assign, readonly) NSUInteger ttl;
@property (nonatomic, assign, readonly) double landingReward;
@property (nonatomic, assign, readonly) double actionReward;
@property (nonatomic, copy, readonly) NSString *payload;
@property (nonatomic, copy, readonly) NSDictionary *extra;
@property (nonatomic, copy, readonly, nullable) NSString *preferredBrowser;
@property (nonatomic, readonly) SABAdRewardStatus rewardStatus;
@property (nonatomic, assign, readonly) SABRevenueType revenueType;
@property (nonatomic, readonly) NSArray<SKPEvent *> *events;
@property (nonatomic, readonly) NSDictionary *eventMap;
@property (nonatomic, strong, readonly) SABCreative *creative;
@property (nonatomic, copy, readonly) NSString *skStoreProductUrl;

@property (nonatomic, strong, readonly) NSString *conversionCheckUrl;

@property (nonatomic, copy, readonly) SABProduct *product;

@property (nonatomic, readonly) double reward;

@property (nonatomic, readonly) NSString *unitId;
@property (nonatomic, readonly, nullable) NSDate *fetchedAt;

- (instancetype)initWithDictionary:(NSDictionary *)dic unitId:(NSString *)unitId fetchedAt:(nullable NSDate *)fetchedAt;

@end

@interface SABAd (Extension)

- (BOOL)isLegacyReward;

- (double)getTotalReward;

- (double)getAvailableReward;

- (BOOL)isActionType;

- (SKPEvent *)getEventWithType:(SKPAdEventType)type;

- (BOOL)hasRewardForEventType:(SKPAdEventType)type;

- (double)getEventReward;

@end

NS_ASSUME_NONNULL_END
