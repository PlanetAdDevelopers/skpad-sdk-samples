#import <Foundation/Foundation.h>

@interface SABPointConfig : NSObject

@property (nonatomic, assign, readonly) int minRedeemAmount;
@property (nonatomic, assign, readonly) BOOL autoRedeem;
@property (nonatomic, assign, readonly) int pointRate;

@end
