//
//  NSDate+DayTest.m
//  Arsfest
//
//  Created by Thibaud Robelain on 03/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "NSDate+DayTest.h"

@implementation NSDate (DayTest)

- (BOOL)isOnSameDayAsDate:(NSDate *)date inTimeZone:(NSTimeZone *)timeZone
{
    NSCalendar* calendar = [NSCalendar currentCalendar];
    [calendar setTimeZone:timeZone];

    NSDateComponents *date1Components = [calendar components:(NSYearCalendarUnit | NSMonthCalendarUnit | NSDayCalendarUnit) fromDate:self];
    NSDateComponents *date2Components = [calendar components:(NSYearCalendarUnit | NSMonthCalendarUnit | NSDayCalendarUnit) fromDate:date];
    
    NSDate *date1WithoutTime = [calendar dateFromComponents:date1Components];
    NSDate *date2WithoutTime = [calendar dateFromComponents:date2Components];
    
    return [date1WithoutTime isEqualToDate:date2WithoutTime];
}

/**
 * Use this method to determine whether date 2 is at least one day later than date 1, and it has b
 * Example: Date 1 = 10/02/14 | Date 2 = 11/02/14 03:00AM | Threshold = 2  returns true
 */
- (BOOL)isEarlierThanDate:(NSDate*)date fromMinutes:(NSInteger)threshold
{
        NSCalendar* calendar = [NSCalendar currentCalendar];
        unsigned unitFlags = NSYearCalendarUnit | NSMonthCalendarUnit |  NSDayCalendarUnit | NSHourCalendarUnit | NSMinuteCalendarUnit;
        NSDateComponents* comp1 = [calendar components:unitFlags fromDate:self];
        NSDateComponents* comp2 = [calendar components:unitFlags fromDate:date];
    
        return [comp1 minute] + threshold < [comp2 minute] &&
        [comp1 hour] <= [comp2 hour] &&
        [comp1 day]  <=  [comp2 day] &&
        [comp1 month] <= [comp2 month] &&
        [comp1 year]  <= [comp2 year];

}


@end
