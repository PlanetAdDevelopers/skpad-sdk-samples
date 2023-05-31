#import <Foundation/Foundation.h>
#import "SABAd.h"

@class SABAdLoaderParams;
@class SABError;

NS_ASSUME_NONNULL_BEGIN

@interface SABAdLoader : NSObject

@property (nonatomic, copy, readonly) NSString *unitId;
@property (nonatomic, readonly) BOOL isLoading;

- (instancetype)initWithUnitId:(NSString *)unitId;
- (void)loadAdWithOnSuccess:(void (^)(SABAd *ad))onSuccess onFailure:(void (^)(SABError *error))onFailure;
- (void)loadAdsWithSize:(NSUInteger)size onSuccess:(void (^)(NSArray<SABAd *> *ads))onSuccess onFailure:(void (^)(SABError *error))onFailure;
- (void)loadNextAdsWithSize:(NSUInteger)size onSuccess:(void (^)(NSArray<SABAd *> *ads))onSuccess onFailure:(void (^)(SABError *error))onFailure;

- (void)loadAdsWithParams:(SABAdLoaderParams *)params onSuccess:(void (^)(NSArray<SABAd *> *ads))onSuccess onFailure:(void (^)(SABError *error))onFailure;

@end

NS_ASSUME_NONNULL_END
