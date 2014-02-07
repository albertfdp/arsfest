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

- (void)addEvent:(ARSEvent*)event
{
    event.location = self;
    [_events addObject:event];
}

@end
