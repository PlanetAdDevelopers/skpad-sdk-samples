//
//  SABArticleView.h
//  SKPAdNative
//
//  Created by Jaehee Ko on 20/12/2018.
//  Copyright © 2018 Buzzvil. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SABArticleViewProtocol.h"
#import "SKPImpressionTrackableView.h"

NS_ASSUME_NONNULL_BEGIN

@class SABArticle;
@class SABArticleView;
@class SABMediaView;

@protocol SABArticleViewDelegate <NSObject>
@optional
- (void)SABArticleView:(SABArticleView *)adView didImpressArticle:(SABArticle *)article;
- (void)SABArticleView:(SABArticleView *)adView didClickArticle:(SABArticle *)article;
@end

@interface SABArticleView : UIView <SABArticleViewProtocol, SKPImpressionTrackableView>

@property (nonatomic, weak) id<SABArticleViewDelegate> delegate;
@property (nonatomic, strong) NSArray<UIView *> *clickableViews;
@property (nonatomic, strong) SABArticle *article;

@end

NS_ASSUME_NONNULL_END
