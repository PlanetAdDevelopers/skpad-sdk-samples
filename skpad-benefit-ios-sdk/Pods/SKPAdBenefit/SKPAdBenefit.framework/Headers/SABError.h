//
//  SABError.h
//  SABAd
//
//  Created by Jaehee Ko on 04/07/2019.
//  Copyright © 2019 Buzzvil. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

typedef enum {
    SABUnknownError = 0,
    SABServerError,
    SABInvalidRequest,
    SABRequestTimeout,
    SABEmptyResponse
} SABErrorCode;

@interface SABError : NSObject

@property (nonatomic, strong, readonly) NSError *rawError;
@property (nonatomic, assign, readonly) NSInteger httpStatusCode;
@property (nonatomic, assign) SABErrorCode code;

- (instancetype)initWithCode:(SABErrorCode)code;
- (instancetype)initWithRawError:(NSError *)rawError httpStatusCode:(NSInteger)httpStatusCode;
+ (instancetype)errorWithRawError:(NSError *)rawError httpStatusCode:(NSInteger)httpStatusCode;

@end

NS_ASSUME_NONNULL_END
