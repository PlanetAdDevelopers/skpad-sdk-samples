//
//  SABUserPreference.h
//  SABAd
//
//  Created by Jaehee Ko on 18/12/2018.
//  Copyright © 2018 Buzzvil. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

typedef enum {
    SABVideoAutoPlayNotSet = 0,
    SABVideoAutoPlayDisabled,
    SABVideoAutoPlayEnabled,
    SABVideoAutoPlayOnWifi
} SABVideoAutoPlayType;

@interface SABUserPreference : NSObject

- (instancetype)initWithAutoPlayType:(SABVideoAutoPlayType)autoplayType;

@property (nonatomic, readonly) SABVideoAutoPlayType autoplayType;

@end

NS_ASSUME_NONNULL_END
