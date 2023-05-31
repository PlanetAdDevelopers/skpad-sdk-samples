//
//  SABUserProfile.h
//  SABAd
//
//  Created by Jaehee Ko on 17/12/2018.
//  Copyright © 2018 Buzzvil. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SABUserProfile.h"

NS_ASSUME_NONNULL_BEGIN

@class SABSession;

typedef enum {
    SABUserGenderUnknown = 0,
    SABUserGenderMale,
    SABUserGenderFemale
} SABUserGender;

@interface SABUserProfile : NSObject

@property (nonatomic, copy, readonly) NSString *userId;
@property (nonatomic, assign, readonly) SABUserGender gender;
@property (nonatomic, assign, readonly) NSUInteger birthYear;
@property (nonatomic, copy, readonly, nullable) NSString *region;
@property (nonatomic, copy) NSString *adId;
@property (nonatomic, copy) NSString *ifv;
@property (nonatomic, strong) SABSession *session;

- (instancetype)initWithUserId:(NSString *)userId;
- (instancetype)initWithUserId:(NSString *)userId birthYear:(NSUInteger)birthYear gender:(SABUserGender)gender;
- (instancetype)initWithDictionary:(NSDictionary *)dictionary;
- (NSDictionary *)toDictionary;
- (BOOL)isSessionRegistered;

@end

NS_ASSUME_NONNULL_END
