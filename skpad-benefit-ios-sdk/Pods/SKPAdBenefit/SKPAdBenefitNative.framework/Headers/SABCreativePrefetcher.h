//
//  SABCreativePrefetcher.h
//  SKPAdNative
//
//  Created by Jaehee Ko on 01/04/2019.
//  Copyright © 2019 Buzzvil. All rights reserved.
//

#import <Foundation/Foundation.h>

@class SABCreative;

NS_ASSUME_NONNULL_BEGIN

@interface SABCreativePrefetcher : NSObject

- (instancetype)initWithCreatives:(NSArray<SABCreative *> *)creatives;
- (void)prefetch;

@end

NS_ASSUME_NONNULL_END
