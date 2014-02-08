//
//  ARSEvent.m
//  Arsfest
//
//  Created by Thibaud Robelain on 06/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSEvent.h"

@implementation ARSEvent
@synthesize eventId = _eventId, name = _name, image = _image, location = _location, description = _description, theme = _theme, type = _type, startTime = _startTime, endTime = _endTime;

+ (ARSEvent*)eventFromDictionary:(NSDictionary*)dictionary
{
    ARSEvent *event = [[ARSEvent alloc] init];
    
    event.name = [dictionary objectForKey:@"name"];
    event.description = [dictionary objectForKey:@"description"];
    event.image = [dictionary objectForKey:@"image"];
    event.theme = [dictionary objectForKey:@"theme"];
    event.type = [dictionary objectForKey:@"type"];
    event.eventId = [dictionary objectForKey:@"id"];
    
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"dd-MM-yyyy:HH:mm"];
    NSString *startTimeAsString = [dictionary objectForKey:@"startTime"];
    NSString *endTimeAsString = [dictionary objectForKey:@"endTime"];

    event.startTime = [dateFormatter dateFromString:startTimeAsString];
    event.endTime = [dateFormatter dateFromString:endTimeAsString];
    
    return event;
}


@end
