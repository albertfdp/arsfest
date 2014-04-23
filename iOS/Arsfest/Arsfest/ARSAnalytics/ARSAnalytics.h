//
//  ARSAnalytics.h
//  Arsfest
//
//  Created by Thibaud Robelain on 22/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ARSAnalytics : NSObject

+ (void)initializeTracker;
+ (void)trackViewOpened:(NSString*)viewName;
+ (void)trackViewOpenedOnlyIfWifiAvailable:(NSString *)viewName;

+ (void)trackEventWithCategory:(NSString*)category action:(NSString*)action;

@end
