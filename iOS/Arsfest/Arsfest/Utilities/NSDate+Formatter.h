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

+ (NSDate*)dateWithYear:(NSInteger)year month:(NSInteger)month day:(NSInteger)day hour:(NSInteger)hour;
+ (NSDate*)dateWithYear:(NSInteger)year month:(NSInteger)month day:(NSInteger)day hour:(NSInteger)hour minute:(NSInteger)minute second:(NSInteger)second;
@end
