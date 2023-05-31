#import <Foundation/Foundation.h>

@class SABUnitSetting;

NS_ASSUME_NONNULL_BEGIN

@interface SABUnitManager : NSObject

@property (nonatomic, copy, readonly) SABUnitSetting *setting;

+ (SABUnitManager *)managerForUnitId:(NSString *)unitId;

@end

NS_ASSUME_NONNULL_END
