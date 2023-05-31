//
//  SABTracker.h
//  SABAd
//
//  Created by Jaehee Ko on 22/01/2019.
//  Copyright © 2019 Buzzvil. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@class SABAd;
@protocol SABTrackable;

@interface SABTracker : NSObject

- (void)impressed:(id<SABTrackable>)trackable;
- (void)clicked:(id<SABTrackable>)trackable;
- (void)videoPlayedForSeconds:(NSTimeInterval)seconds onTrackable:(id<SABTrackable>)trackable;
- (void)vastClicked:(id<SABTrackable>)trackable;

@end

NS_ASSUME_NONNULL_END
