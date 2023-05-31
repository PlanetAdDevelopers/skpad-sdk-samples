#import <Foundation/Foundation.h>

typedef enum {
    SKPAdEventTypeLanding
} SKPAdEventType;

@class SKPReward;

@interface SKPEvent : NSObject

@property (nonatomic, assign, readonly) SKPAdEventType type;
@property (nonatomic, copy, readonly) NSArray<NSString *> *trackingUrls;
@property (nonatomic, copy, readonly) NSDictionary *extra;
@property (nonatomic, copy, readonly) SKPReward *reward;

- (instancetype)initWithDictionary:(NSDictionary *)dic;

@end
