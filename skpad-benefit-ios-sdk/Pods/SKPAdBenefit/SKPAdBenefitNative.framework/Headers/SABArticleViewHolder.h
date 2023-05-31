//
//  SABArticleViewHolder.h
//  SKPAdNative
//
//  Created by Jaehee Ko on 25/03/2019.
//  Copyright © 2019 Buzzvil. All rights reserved.
//

#import <UIKit/UIKit.h>

@class SABArticle;

NS_ASSUME_NONNULL_BEGIN

@interface SABArticleViewHolder : UIView

@property (nonatomic, readonly) SABArticle *article;

- (void)renderArticle:(SABArticle *)article;

@end

NS_ASSUME_NONNULL_END
