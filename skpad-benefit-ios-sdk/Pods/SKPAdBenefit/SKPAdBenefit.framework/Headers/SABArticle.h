//
//  SABArticle.h
//  SABAd
//
//  Created by Jaehee Ko on 18/03/2019.
//  Copyright © 2019 Buzzvil. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SABCreative.h"
#import "SABCategory.h"
#import "SABChannel.h"
#import "SABTrackable.h"
#import "SKPEvent.h"

NS_ASSUME_NONNULL_BEGIN

typedef NSString *SABArticleCategoryName;

static const SABArticleCategoryName SABArticleCategoryAnimal = @"animal";
static const SABArticleCategoryName SABArticleCategoryBusiness = @"business";
static const SABArticleCategoryName SABArticleCategoryEntertainment = @"entertainment";
static const SABArticleCategoryName SABArticleCategoryFashion = @"fashion";
static const SABArticleCategoryName SABArticleCategoryFood = @"food";
static const SABArticleCategoryName SABArticleCategoryFun = @"fun";
static const SABArticleCategoryName SABArticleCategoryGame = @"game";
static const SABArticleCategoryName SABArticleCategoryHealth = @"health";
static const SABArticleCategoryName SABArticleCategoryLifestyle = @"lifestyle";
static const SABArticleCategoryName SABArticleCategoryNews = @"news";
static const SABArticleCategoryName SABArticleCategoryRelationship = @"relationship";
static const SABArticleCategoryName SABArticleCategorySports = @"sports";
static const SABArticleCategoryName SABArticleCategoryTechnology = @"technology";

@interface SABArticle : NSObject <SABTrackable>

@property (nonatomic, copy, readonly) NSString *Id;
@property (nonatomic, copy, readonly) NSString *payload;
@property (nonatomic, copy, readonly) NSDictionary *extra;
@property (nonatomic, copy, readonly) NSString *sourceUrl;
@property (nonatomic, strong, readonly) NSDate *createdAt;

@property (nonatomic, readonly) NSArray<SKPEvent *> *events;
@property (nonatomic, readonly) NSDictionary *eventMap;

@property (nonatomic, strong, readonly) SABCreative *creative;
@property (nonatomic, strong, readonly) SABCategory *category;
@property (nonatomic, strong, readonly) SABChannel *channel;

- (instancetype)initWithDictionary:(NSDictionary *)dic;

- (SKPEvent *)getEventWithType:(SKPAdEventType)type;
- (BOOL)hasRewardForEventType:(SKPAdEventType)type;

@end

NS_ASSUME_NONNULL_END
