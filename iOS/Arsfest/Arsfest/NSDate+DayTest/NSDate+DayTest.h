//
//  NSDate+DayTest.h
//  Arsfest
//
//  Created by Thibaud Robelain on 03/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSDate (DayTest)

- (BOOL)isOnSameDayAsDate:(NSDate*)date inTimeZone:(NSTimeZone*)timeZone;
- (BOOL)isEarlierThanDate:(NSDate*)date fromMinutes:(NSInteger)threshold;

+ (int)daysLeftBeforeTheParty;

@end
