//
//  SABRewardHandler.h
//  SABAd
//
//  Created by Jaehee Ko on 23/04/2019.
//  Copyright © 2019 Buzzvil. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SABRewardResult.h"

@class SABAd;

NS_ASSUME_NONNULL_BEGIN

@interface SABRewardHandler : NSObject

- (void)requestRewardForAd:(SABAd *)ad pbUrl:(NSString*)pbUrl onStart:(void (^)(void))onStart onComplete:(void (^)(SABRewardResult result))onComplete;

- (void)requestConversionCheck:(NSString *)conversionCheckUrl
                     onSuccess:(void (^)(NSDictionary *))onSuccess
                     onFailure:(void (^)(SABError *error))onFailure;
@end

NS_ASSUME_NONNULL_END
