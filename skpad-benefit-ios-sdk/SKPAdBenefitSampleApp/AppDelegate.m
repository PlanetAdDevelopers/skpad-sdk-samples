//
//  AppDelegate.m
//  SKPAdBenefitSampleApp
//
//  Created by SK Planet on 2022/08/02.
//

#import "AppDelegate.h"
#import <SKPAdBenefit/SKPAdBenefit.h>

#define kDefaultAppId    @"346986921548425"

@interface AppDelegate ()
@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    // Override point for customization after application launch.
    SABConfig *config = [[SABConfig alloc] initWithAppId:kDefaultAppId environment:SABEnvProduction logging:YES];
    [SKPAdBenefit initializeWithConfig:config];
    
    SABUserPreference *userPreference = [[SABUserPreference alloc] initWithAutoPlayType:SABVideoAutoPlayOnWifi];
    [SKPAdBenefit setUserPreference:userPreference];
    
    return YES;
}

@end
