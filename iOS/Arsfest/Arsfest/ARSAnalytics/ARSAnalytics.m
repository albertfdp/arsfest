//
//  ARSAnalytics.m
//  Arsfest
//
//  Created by Thibaud Robelain on 22/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSAnalytics.h"
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

+ (void)initializeTracker
{
    [[GAI sharedInstance] trackerWithTrackingId:@"UA-50262589-1"];
}

@end
