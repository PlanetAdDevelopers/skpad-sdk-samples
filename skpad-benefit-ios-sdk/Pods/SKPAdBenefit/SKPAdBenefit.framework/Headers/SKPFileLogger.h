//
//  SKPFileLogger.h
//  BLESDK
//
//  Created by oro on 2014. 10. 23..
//  Copyright (c) 2014년 skplanet. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SKPFileLogger : NSObject {
    NSFileHandle *logFile;
}

@property (nonatomic, assign) BOOL debug;

+ (SKPFileLogger *)sharedInstance;
- (void)log:(NSString *)format, ...;
- (void) postNotificationForQA:(NSString*)msg;
- (void) removeAndCreateLogFile;
@end

#define FLog(format, ...) [[SKPFileLogger sharedInstance] log:(@"%s(%d) : "format), __PRETTY_FUNCTION__, __LINE__, ##__VA_ARGS__]
