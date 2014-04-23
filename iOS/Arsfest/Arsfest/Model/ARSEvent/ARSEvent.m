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
    // Get prefer language
    NSUserDefaults* defs = [NSUserDefaults standardUserDefaults];
    NSArray* languages = [defs objectForKey:@"AppleLanguages"];
    NSString* preferredLang = [languages objectAtIndex:0];
    
    ARSEvent *event = [[ARSEvent alloc] init];
    
    if ([preferredLang isEqualToString:@"dk"]) {
        event.name = [dictionary objectForKey:@"danishName"];
    }else{
        event.name = [dictionary objectForKey:@"name"];
    }

    if ([dictionary objectForKey:@"menu"]){
        NSArray *menu = [dictionary objectForKey:@"menu"];
        NSMutableString *menuDescription = [NSMutableString stringWithString:@""];
        NSString *cofee = @"";
        for(NSDictionary *dish in menu) {
            //[menuDescription appendString:@"\t"];
            if ([[dish objectForKey:@"course"] isEqualToString:@"Coffee"]) {
                cofee = [dish objectForKey:@"name"];
            
            }else if ([[dish objectForKey:@"course"] isEqualToString:@"Drinks"]){
                [menuDescription appendString:[dish objectForKey:@"course"]];
                [menuDescription appendString:@"\n"];
                [menuDescription appendString:@"\n"];
                [menuDescription appendString:[dish objectForKey:@"name"]];
                [menuDescription appendString:@"\n"];
                [menuDescription appendString:@"\n"];
                [menuDescription appendString:cofee];
            }else{
                [menuDescription appendString:[dish objectForKey:@"course"]];
                [menuDescription appendString:@"\n"];
                [menuDescription appendString:@"\n"];
                [menuDescription appendString:[dish objectForKey:@"name"]];
                [menuDescription appendString:@"\n"];
                [menuDescription appendString:@"\n"];
            }
            
            
        }
        event.description = menuDescription;
    }else{
        if ([preferredLang isEqualToString:@"dk"]) {
            event.description = [dictionary objectForKey:@"danishDescription"];
        }else{
            event.description = [dictionary objectForKey:@"description"];
        }
        
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
