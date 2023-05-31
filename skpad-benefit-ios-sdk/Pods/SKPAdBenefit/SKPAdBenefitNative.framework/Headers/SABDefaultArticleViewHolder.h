//
//  SABDefaultArticleViewHolder.h
//  SKPAdNative
//
//  Created by Jaehee Ko on 21/02/2019.
//  Copyright © 2019 Buzzvil. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SABArticleViewHolder.h"

NS_ASSUME_NONNULL_BEGIN

@interface SABDefaultArticleViewHolder : SABArticleViewHolder

@property (nonatomic, assign) CGFloat detailViewPadding;
@property (nonatomic, assign) CGFloat mediaViewCornerRadius;

+ (NSString *)nibName;

@end

NS_ASSUME_NONNULL_END
