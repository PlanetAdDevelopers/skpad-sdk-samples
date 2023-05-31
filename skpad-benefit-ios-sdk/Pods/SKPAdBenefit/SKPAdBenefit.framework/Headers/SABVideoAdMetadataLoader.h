//
//  SABVideoAdMetadataLoader.h
//  SABAd
//
//  Created by Jaehee Ko on 17/01/2019.
//  Copyright © 2019 Buzzvil. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@class SABError;
@class SABCreative;

@interface SABVideoAdMetadataLoader : NSObject

- (instancetype)initWithVideoCreative:(SABCreative *)videoCreative;
- (void)loadMetadataWithOnSuccess:(void (^)(void))onSuccess onFailure:(void (^)(SABError *error))onFailure;

@end

NS_ASSUME_NONNULL_END
