//
//  ARSLocation.m
//  Arsfest
//
//  Created by Thibaud Robelain on 06/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSLocation.h"
#import "ARSEvent.h"

@implementation ARSLocation
@synthesize locationId = _locationId,
            name = _name,
            description = _description,
            coordinates = _coordinates,
            events = _events;

+ (ARSLocation *)locationFromDictionary:(NSDictionary *)dictionary
{
    ARSLocation *location = [[ARSLocation alloc] init];
    
    location.name = [dictionary objectForKey:@"name"];
    location.locationId = [dictionary objectForKey:@"id"];
    NSString *latitude = [dictionary objectForKey:@"latitude"];
    NSString *longitude = [dictionary objectForKey:@"longitude"];
    location.coordinates = [PFGeoPoint geoPointWithLatitude:[latitude doubleValue] longitude:[longitude doubleValue]];
    location.description = [dictionary objectForKey:@"description"];
    
    NSArray *events = [dictionary objectForKey:@"events"];
    for (NSDictionary* eventDictionary in events) {
        [location addEventFromDictionary:eventDictionary];
    }
    
    return location;
}

- (void)addEvent:(ARSEvent*)event
{
    event.location = self;
    [_events addObject:event];
}

- (void)addEventFromDictionary:(NSDictionary*)eventDictionary
{
    ARSEvent *event = [ARSEvent eventFromDictionary:eventDictionary];
    [event setLocation:self];
    [_events addObject:event];
}

@end
