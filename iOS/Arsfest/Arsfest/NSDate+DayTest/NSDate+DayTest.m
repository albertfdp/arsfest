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

- (BOOL)isEarlierThanDate:(NSDate*)date fromMinutes:(NSInteger)threshold
{
    int minutesFromDate = [date timeIntervalSinceDate:self] / 60;
    return (minutesFromDate > threshold);
}

+ (int)daysLeftBeforeTheParty
{
    NSDate *fromDate;
    NSDate *toDate;
    
    NSCalendar *calendar = [NSCalendar currentCalendar];
    
    [calendar rangeOfUnit:NSDayCalendarUnit startDate:&fromDate
                 interval:NULL forDate:[NSDate date]];
    [calendar rangeOfUnit:NSDayCalendarUnit startDate:&toDate
                 interval:NULL forDate:ARSFEST_END_DATE];
    
    NSDateComponents *difference = [calendar components:NSDayCalendarUnit
                                               fromDate:fromDate toDate:toDate options:0];
    
    return [difference day];
}

@end
