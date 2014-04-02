//
//  ARSData.m
//  Arsfest
//
//  Created by Thibaud Robelain on 07/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSData.h"

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
@synthesize dataDelegate = _dataDelegate;
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

- (NSString*)locationNameForWifiBssid:(NSString*)bssid
{
    return @"";
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
#pragma mark - ARSDataDelegate

- (void)notifyDelegate
{
    if ([_dataDelegate respondsToSelector:@selector(didReceiveDataFromTheServer)]) {
        [_dataDelegate didReceiveDataFromTheServer];
    }
}

@end
