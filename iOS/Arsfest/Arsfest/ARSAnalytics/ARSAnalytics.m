//
//  ARSAnalytics.m
//  Arsfest
//
//  Created by Thibaud Robelain on 22/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSAnalytics.h"
#import "ARSUserController.h"
#import "Parse/Parse.h"
#import "GAI.h"
#import "GAIDictionaryBuilder.h"
#import "GAIFields.h"

@implementation ARSAnalytics

+ (void)trackViewOpened:(NSString*)name
{
    if ([[NSUserDefaults standardUserDefaults] boolForKey:@"allowstatistics"]) {
        id<GAITracker> tracker = [[GAI sharedInstance] defaultTracker];
        
        [tracker set:kGAIScreenName
               value:name];
        
        [tracker send:[[GAIDictionaryBuilder createAppView] build]];
    }
}

+ (void)trackViewOpenedOnlyIfWifiAvailable:(NSString *)viewName
{
    if ([[ARSUserController sharedUserController] localWiFiAvailable]) {
        [ARSAnalytics trackViewOpened:viewName];
    }
}

+ (void)initializeTracker
{
    [[GAI sharedInstance] trackerWithTrackingId:@"UA-50262589-1"];
}

+ (void)trackEventWithCategory:(NSString*)category action:(NSString*)action
{
    if ([[ARSUserController sharedUserController] localWiFiAvailable]) {
        id<GAITracker> tracker = [[GAI sharedInstance] defaultTracker];
        
        [tracker send:[[GAIDictionaryBuilder createEventWithCategory:category     // Event category (required)
                                                              action:action  // Event action (required)
                                                               label:nil          // Event label
                                                               value:nil] build]];    // Event value
    }
}

+ (void)trackSpentTime:(NSTimeInterval)interval onScreen:(NSString*)screen
{
    if ([[ARSUserController sharedUserController] localWiFiAvailable]) {
        id tracker = [[GAI sharedInstance] defaultTracker];
        NSNumber *intervalNumber = [NSNumber numberWithDouble:interval];
        [tracker send:[[GAIDictionaryBuilder createTimingWithCategory:@"Event"    // Timing category (required)
                                                            interval:intervalNumber        // Timing interval (required)
                                                                name:screen  // Timing name
                                                               label:nil] build]];    // Timing label
    }
}


@end
