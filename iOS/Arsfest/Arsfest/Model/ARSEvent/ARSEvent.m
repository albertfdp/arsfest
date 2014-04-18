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
    
    
    if ([dictionary objectForKey:@"menu"]){
        NSArray *menu = [dictionary objectForKey:@"menu"];
        NSMutableString *menuDescription = [NSMutableString stringWithString:@""];
        for(NSDictionary *dish in menu) {
            [menuDescription appendString:@"\t"];
            [menuDescription appendString:[dish objectForKey:@"course"]];
            [menuDescription appendString:@"\n"];
            [menuDescription appendString:@"\n"];
            [menuDescription appendString:[dish objectForKey:@"name"]];
            [menuDescription appendString:@"\n"];
            [menuDescription appendString:@"\n"];
        }
        event.description = menuDescription;
    }else{
        event.description = [dictionary objectForKey:@"description"];
    }
    event.image = [dictionary objectForKey:@"image"];
    event.theme = [dictionary objectForKey:@"theme"];
    event.type = [dictionary objectForKey:@"type"];
    event.eventId = [dictionary objectForKey:@"id"];
    
    NSString *startTimeAsString = [dictionary objectForKey:@"startTime"];
    NSString *endTimeAsString = [dictionary objectForKey:@"endTime"];

    event.startTime = [NSDate dateFromString:startTimeAsString];
    event.endTime = [NSDate dateFromString:endTimeAsString];
    
    return event;
}


@end
