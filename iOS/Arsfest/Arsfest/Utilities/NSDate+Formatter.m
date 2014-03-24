//
//  NSDate+Formatter.m
//  Arsfest
//
//  Created by Thibaud Robelain on 13/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "NSDate+Formatter.h"

@implementation NSDate (Formatter)

+ (NSString*)stringFromDate:(NSDate*)date
{
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"dd-MM-yyyy:HH:mm"];
    
    return [dateFormatter stringFromDate:date];
}

+ (NSDate*)dateFromString:(NSString*)string
{
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"dd-MM-yyyy:HH:mm"];

    return [dateFormatter dateFromString:string];
}

+ (NSString*)hourMinuteStringFromDate:(NSDate*)date
{
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateFormat:@"HH:mm"];
    
    return [formatter stringFromDate:date];
}

+ (BOOL)currentDateIsBetween:(NSDate*)startDate and:(NSDate*)endDate
{
    NSDate *currentDate = [NSDate date];
    
    return ([startDate compare:currentDate] == NSOrderedAscending
            && [endDate compare:currentDate] == NSOrderedDescending);
}

@end
