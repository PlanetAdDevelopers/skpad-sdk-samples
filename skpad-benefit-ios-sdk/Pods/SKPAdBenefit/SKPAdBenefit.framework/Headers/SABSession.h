//
//  SABSession.h
//  SABAd
//
//  Created by Jaehee Ko on 18/12/2018.
//  Copyright © 2018 Buzzvil. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface SABSession : NSObject

@property (nonatomic, copy, readonly) NSString *sessionKey;
@property (nonatomic, copy, readonly) NSString *deviceId;
//jylee vdid
@property (nonatomic, copy, readonly) NSString *vdid;

- (instancetype)initWithDictionary:(NSDictionary *)dic;
- (NSDictionary *)toDictionary;

@end

NS_ASSUME_NONNULL_END
