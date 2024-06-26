#import <Foundation/Foundation.h>

@interface SABUnitSetting : NSObject

@property (nonatomic, assign, readonly) NSUInteger feedAdRatio;
@property (nonatomic, assign, readonly) NSUInteger feedArticleRatio;

- (instancetype)initWithFeedAdRatio:(NSUInteger)feedAdRatio feedArticleRatio:(NSUInteger)feedArticleRatio;

@end
