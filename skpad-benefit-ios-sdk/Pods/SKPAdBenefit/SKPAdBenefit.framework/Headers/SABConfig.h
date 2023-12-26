#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

typedef enum {
    SABEnvDev = 0,
    SABEnvAlp,
    SABEnvPrd
} SABEnv;

@interface SABConfig : NSObject

@property (nonatomic, assign, readonly) SABEnv environment;
@property (nonatomic, assign, readonly) BOOL logging;
@property (nonatomic, copy, readonly) NSString *appId;
@property (nonatomic, assign) BOOL enableWidget;
@property (nonatomic, copy) NSString *appGroupId;

- (instancetype)initWithAppId:(NSString *)appId;
- (instancetype)initWithAppId:(NSString *)appId environment:(SABEnv)environment logging:(BOOL)logging;
@end

NS_ASSUME_NONNULL_END

