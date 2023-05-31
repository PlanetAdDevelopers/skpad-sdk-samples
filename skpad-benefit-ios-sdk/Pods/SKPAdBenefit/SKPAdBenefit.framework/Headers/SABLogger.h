//
//  SABLogger.h
//  SABAd
//
//  Created by Jaehee Ko on 16/11/2018.
//  Copyright © 2018 Jaehee Ko. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <SKPAdBenefit/SKPAdBenefit.h>
#import "SKPFileLogger.h"

#define SKPAdLog(format, ...) [[SKPFileLogger sharedInstance] log:(@"%s(%d) : "format), __PRETTY_FUNCTION__, __LINE__, ##__VA_ARGS__]

