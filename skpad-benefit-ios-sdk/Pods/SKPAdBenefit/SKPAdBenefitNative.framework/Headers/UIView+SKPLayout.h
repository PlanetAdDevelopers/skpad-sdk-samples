//
//  UIView+SKPLayout.h
//  SKPAdNative
//
//  Created by Jaehee Ko on 23/01/2019.
//  Copyright © 2019 Buzzvil. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface UIView (SKPLayout)

- (void)skpadEdgesToContainer:(UIView *)container;
- (void)skpadEdgesToContainer:(UIView *)container withEqualMargin:(CGFloat)margin;
- (void)skpadEdgesToContainer:(UIView *)container withMargin:(UIEdgeInsets)margin;

@end

NS_ASSUME_NONNULL_END
