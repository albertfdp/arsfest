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
    
#warning Add point on map
    

    PFGeoPoint *location = [[PFGeoPoint alloc] init];
    location.latitude = [[dictionary objectForKey:@"latitude"] doubleValue];
    location.longitude = [[dictionary objectForKey:@"longitude"] doubleValue];
    
    newWifi.location = location;
    
    return newWifi;
}

@end
