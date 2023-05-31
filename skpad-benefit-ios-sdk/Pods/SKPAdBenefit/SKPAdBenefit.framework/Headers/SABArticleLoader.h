//
//  SABArticleLoader.h
//  SABAd
//
//  Created by Jaehee Ko on 18/03/2019.
//  Copyright © 2019 Buzzvil. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SABArticle.h"

@class SABError;

NS_ASSUME_NONNULL_BEGIN

@interface SABArticleLoader : NSObject

@property (nonatomic, copy, readonly) NSString *unitId;
@property (nonatomic, strong, nullable) NSArray<SABArticleCategoryName> *categories;
@property (nonatomic, readonly) BOOL isLoading;

- (instancetype)initWithUnitId:(NSString *)unitId;
- (void)loadArticlesWithSize:(NSUInteger)size onSuccess:(void (^)(NSArray<SABArticle *> *articles))onSuccess onFailure:(void (^)(SABError *error))onFailure;
- (void)loadNextArticlesWithSize:(NSUInteger)size onSuccess:(void (^)(NSArray<SABArticle *> *articles))onSuccess onFailure:(void (^)(SABError *error))onFailure;

@end

NS_ASSUME_NONNULL_END
