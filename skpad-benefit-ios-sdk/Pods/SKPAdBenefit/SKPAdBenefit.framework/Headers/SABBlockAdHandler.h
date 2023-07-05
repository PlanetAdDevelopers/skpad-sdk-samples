//
//  SABBlockAdHandler.h
//  SAB
//
//  Created by 이주연(Juyeon Lee)/Context-Aware기술개발팀/SKP on 2023/05/30.
//  Copyright © 2023 SKP. All rights reserved.
//

#import <Foundation/Foundation.h>

@class SABAd;

NS_ASSUME_NONNULL_BEGIN

@interface SABBlockAdHandler : NSObject

- (void)requestBlockAd:(SABAd *)ad onSuccess:(void (^)(void))onSuccess onFailure:(void (^)(SABError *error))onFailure;

@end

NS_ASSUME_NONNULL_END
