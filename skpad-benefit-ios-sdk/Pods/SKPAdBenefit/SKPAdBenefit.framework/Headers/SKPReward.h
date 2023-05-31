#import <Foundation/Foundation.h>

typedef enum {
    SKPAdRewardStatusReceivable,
    SKPAdRewardStatusAlreadyReceived,
    SKPAdRewardStatusUnknown
} SKPAdRewardStatus;

extern NSString * const kSABExtraMinimumStayDuration;

@interface SKPReward : NSObject

@property (nonatomic, copy, readonly) NSString *issueMethod;
@property (nonatomic, assign, readonly) NSUInteger amount;
@property (nonatomic, assign) SKPAdRewardStatus status;
@property (nonatomic, copy, readonly) NSString *statusCheckUrl;
@property (nonatomic, assign, readonly) NSUInteger ttl;
@property (nonatomic, copy, readonly) NSDictionary *extra;

- (instancetype)initWithDictionary:(NSDictionary *)dic;

- (NSUInteger)getReceivableAmount;

- (BOOL)isReceivable;

- (void)markAsRewarded;

- (BOOL)isRewarded;

@end
