//
//  ARSData.h
//  Arsfest
//
//  Created by Thibaud Robelain on 07/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ARSLocation.h"
#import "ARSWifi.h"

//
// This class is responsible for providing ViewControllers the data they seek corresponding to events or wifis
// Use the wifis to geo-locate a user on the map and get the name of the room in which he's in.
//

@interface ARSData : NSObject

@property (nonatomic, strong) NSMutableArray *locations;
@property (nonatomic, strong) NSDictionary *wifis;


/**
 * Access the ARSData "database" with this singleton object
 */
+ (ARSData*)data;


/**
 * Returns the events that are taking place in the specified ARSLocationType
 */
- (NSArray*)eventsIn:(ARSLocationType)location;


/**
 * Gives the name of the room in which the wi-fi is located
 */
- (NSString*)locationNameForWifiBssid:(NSString*)bssid;


/**
 * Gives the name of the room in which the wi-fi dongle is at the GeoPoint location
 */
- (NSString*)locationNameFromGeoPoint:(PFGeoPoint*)geoPoint;


@end
