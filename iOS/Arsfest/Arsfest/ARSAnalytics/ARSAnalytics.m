//
//  ARSAnalytics.m
//  Arsfest
//
//  Created by Thibaud Robelain on 22/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSAnalytics.h"
#import "Parse/Parse.h"

@implementation ARSAnalytics

+ (void)trackViewOpened:(NSString*)name
{
    [PFAnalytics trackEvent:name dimensions:nil];
}

@end
