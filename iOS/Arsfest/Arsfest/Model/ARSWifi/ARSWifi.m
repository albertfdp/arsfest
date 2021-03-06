//
//  ARSWifi.m
//  Arsfest
//
//  Created by Thibaud Robelain on 02/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSWifi.h"

@implementation ARSWifi
@synthesize location, locationName, locationUniqueID, bssid, pointOnMap;


+ (ARSWifi*)wifiFromDictionary:(NSDictionary*)dictionary
{
    ARSWifi *newWifi = [[ARSWifi alloc] init];
    
    newWifi.locationName = [dictionary objectForKey:@"locationName"];
    newWifi.bssid = [dictionary objectForKey:@"bssid"];
    CGFloat x = [[dictionary objectForKey:@"x"] floatValue];
    CGFloat y = [[dictionary objectForKey:@"y"] floatValue];
    newWifi.pointOnMap = CGPointMake(x, y);    

    PFGeoPoint *location = [[PFGeoPoint alloc] init];
    location.latitude = [[dictionary objectForKey:@"latitude"] doubleValue];
    location.longitude = [[dictionary objectForKey:@"longitude"] doubleValue];
    
    newWifi.location = location;
    
    return newWifi;
}

@end
