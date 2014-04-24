//
//  ARSData.m
//  Arsfest
//
//  Created by Thibaud Robelain on 07/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSData.h"
#import "ARSEvent.h"

#define kEventsFileName                @"events"
#define kWifisFileName                 @"wifis"

@interface ARSData()

/** Parses both JSON files to initialize all the data the app needs **/
- (void)parseJSON;

/** Parses a specified JSON file searching for the given master key **/
- (NSArray*)parseJSONWithFileName:(NSString*)filename key:(NSString*)key;

@end

@implementation ARSData
@synthesize locations = _locations;
@synthesize wifis = _wifis;

- (id)init
{
    self = [super init];
    
    if (self) {
        _locations = [[NSMutableArray alloc] init];
        _wifis = [[NSMutableDictionary alloc] init];
        [self parseJSON];
    }
    
    return self;
}

+ (ARSData*)data
{
    static ARSData* data = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        data = [[ARSData alloc] init];
    });
    return data;
}

#pragma mark -
#pragma mark - Data source for the table view

- (NSArray*)eventsIn:(ARSLocationType)locationType
{
    NSMutableArray *eventsArray = [[NSMutableArray alloc] init];
    switch (locationType) {
            //Case where we want all the events
        case ARSLocationAll:
        {
            for (ARSLocation *location in _locations) {
                [eventsArray addObjectsFromArray:location.events];
            }
        }
            break;
        default:
            //Case where we request a specific location
        {
            for (ARSLocation *location in _locations) {
                if (location.type == locationType) {
                    eventsArray = location.events;
                }
            }
            
        }
            break;
    }

    NSArray *returnedArray = [NSArray arrayWithArray:eventsArray];
    return returnedArray;
}

- (ARSEvent*)currentEventInTheParty
{
    NSMutableArray *possibleEvents = [[NSMutableArray alloc] init];
    for (ARSLocation *location in _locations) {
        for (ARSEvent *event in location.events) {
            if ([NSDate currentDateIsBetween:event.startTime and:event.endTime]) {
                [possibleEvents addObject:event];
            }
        }
    }
    NSSortDescriptor *dateDescriptor = [NSSortDescriptor sortDescriptorWithKey:@"startTime"
                                                                     ascending:YES];
    NSArray *sortDescriptors = [NSArray arrayWithObject:dateDescriptor];
    NSArray *sortedArray = [possibleEvents sortedArrayUsingDescriptors:sortDescriptors];
    
    if ([sortedArray lastObject]) {
        return [sortedArray objectAtIndex:0];
    } else {
        return nil;
    }
}

- (ARSEvent*)nextEventInTheParty
{
    
    NSMutableArray *possibleEvents = [[NSMutableArray alloc] init];
    for (ARSLocation *location in _locations) {
        for (ARSEvent *event in location.events) {
            if ([[NSDate date] isEarlierThanDate:event.startTime fromMinutes:0]) {
                [possibleEvents addObject:event];
            }
        }
    }
    NSSortDescriptor *dateDescriptor = [NSSortDescriptor sortDescriptorWithKey:@"startTime"
                                                                     ascending:NO];
    NSArray *sortDescriptors = [NSArray arrayWithObject:dateDescriptor];
    NSArray *sortedArray = [possibleEvents sortedArrayUsingDescriptors:sortDescriptors];
    
    if ([sortedArray lastObject]) {
        return [sortedArray objectAtIndex:0];
    } else {
        return nil;
    }
}

#pragma mark -
#pragma mark - JSON Parsing and Fetching

- (NSArray*)parseJSONWithFileName:(NSString*)filename key:(NSString*)key
{
    NSError *error;
    
    NSString *filePath = [[NSBundle mainBundle] pathForResource:filename ofType:@"json"];
    NSData *jsonData = [NSData dataWithContentsOfFile:filePath];
    
    
    NSJSONSerialization* json = [NSJSONSerialization
                                 JSONObjectWithData:jsonData
                                 options:kNilOptions
                                 error:&error];
    
    if (!error) {
        NSArray *array = [json valueForKey:key];
        return array;
    }
    
    return nil;
}

- (void)createLocations:(NSArray*)locations
{
    for (NSDictionary* locationDictionary in locations) {
        ARSLocation* location = [ARSLocation locationFromDictionary:locationDictionary];
        [_locations addObject:location];
    }
}

- (void)createWifis:(NSArray*)wifis
{
    for (NSDictionary* wifiDictionary in wifis) {
        ARSWifi* wifi = [ARSWifi wifiFromDictionary:wifiDictionary];
        if ([_wifis objectForKey:wifi.bssid] != nil) {
            [[_wifis objectForKey:wifi.bssid] addObject:wifi];
        } else {
            NSMutableArray *bssidArray = [[NSMutableArray alloc] init];
            [bssidArray addObject:wifi];
            [_wifis setValue:bssidArray forKey:wifi.bssid];
        }
    }
}

- (void)parseJSON
{
    NSArray *locations = [self parseJSONWithFileName:kEventsFileName key:@"locations"];
    [self createLocations:locations];
    NSArray *wifis = [self parseJSONWithFileName:kWifisFileName key:@"bssids"];
    [self createWifis:wifis];
}

#pragma mark -
#pragma mark - Wifi to bssid / Bssid to wifi

- (CGPoint)cgPointForWifiBssid:(NSString*)bssid
{
    if ([_wifis objectForKey:bssid] != nil) {
        ARSWifi *wifi = [[_wifis objectForKey:bssid] lastObject];
        return wifi.pointOnMap;
    }
    
    return CGPointMake(0, 0);
}

- (NSString*)locationNameForWifiBssid:(NSString*)bssid
{
    NSString *locationName = nil;
    if ([_wifis objectForKey:bssid] != nil) {
        ARSWifi *wifi = [[_wifis objectForKey:bssid] lastObject];
        locationName = wifi.locationName;
    }
    
    return locationName;
}

- (NSString*)locationNameFromGeoPoint:(PFGeoPoint*)geoPoint
{
    for (NSString* key in [_wifis allKeys]) {
        for (NSArray *array in [_wifis objectForKey:key]) {
            ARSWifi *wifi = [array lastObject];
            if (wifi.location == geoPoint) {
                return wifi.locationName;
            } else {
                break;
            }
        }
    }
    
    return nil;
}

@end
