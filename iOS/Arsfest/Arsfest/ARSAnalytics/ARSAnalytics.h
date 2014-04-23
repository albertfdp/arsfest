//
//  ARSAnalytics.h
//  Arsfest
//
//  Created by Thibaud Robelain on 22/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ARSAnalytics : NSObject

/** Initialize the Google Analytics tracker before doing anything else  */
+ (void)initializeTracker;

/** Track a screen opened  */
+ (void)trackViewOpened:(NSString*)viewName;

/** Track a screen opened only if the wifi is available, to avoid data overhead */
+ (void)trackViewOpenedOnlyIfWifiAvailable:(NSString *)viewName;

/* The two methods below only work if the wifi is enabled to avoid using to much date*/
+ (void)trackEventWithCategory:(NSString*)category action:(NSString*)action;
+ (void)trackSpentTime:(NSTimeInterval)interval onScreen:(NSString*)screen;

@end
