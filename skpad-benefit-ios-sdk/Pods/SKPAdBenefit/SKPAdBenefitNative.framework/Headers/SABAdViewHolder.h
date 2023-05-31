//
//  SABAdViewHolder.h
//  SKPAdNative
//
//  Created by Jaehee Ko on 25/03/2019.
//  Copyright © 2019 Buzzvil. All rights reserved.
//

#import <UIKit/UIKit.h>

@class SABAd;

NS_ASSUME_NONNULL_BEGIN

@interface SABAdViewHolder : UIView

@property (nonatomic, readonly) SABAd *ad;

- (void)renderAd:(SABAd *)ad;

@end

NS_ASSUME_NONNULL_END
