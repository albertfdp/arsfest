//
//  NSDate+Formatter.h
//  Arsfest
//
//  Created by Thibaud Robelain on 13/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSDate (Formatter)

+ (NSString*)stringFromDate:(NSDate*)date;
+ (NSDate*)dateFromString:(NSString*)string;
+ (BOOL)currentDateIsBetween:(NSDate*)startDate and:(NSDate*)endDate;
+ (NSString*)hourMinuteStringFromDate:(NSDate*)date;

@end
