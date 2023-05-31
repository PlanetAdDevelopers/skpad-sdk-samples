#import <Foundation/Foundation.h>

@interface SABAdLoaderParams : NSObject

@property (nonatomic, copy, readonly) NSNumber *size;
@property (nonatomic, copy, readonly) NSArray<NSNumber *> *revenueTypes;
@property (nonatomic, copy, readonly) NSString *cpsCategory;

+ (SABAdLoaderParams *)paramsWithSize:(NSNumber *)size
                         revenueTypes:(NSArray<NSNumber *> *)revenueTypes
                          cpsCategory:(NSString *)cpsCategory;

@end
