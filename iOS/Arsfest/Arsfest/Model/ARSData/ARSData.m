//
//  ARSData.m
//  Arsfest
//
//  Created by Thibaud Robelain on 07/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSData.h"
#import "AFNetworking.h"

#define kJSONURL                [NSURL URLWithString:@"https://raw.github.com/albertfdp/arsfest/master/iOS/test.json"]

@interface ARSData()

- (void)downloadJSON;
- (void)parseJSON:(id)jsonObject;

@end

@implementation ARSData
@synthesize locations = _locations;
@synthesize dataDelegate = _dataDelegate;

- (id)init
{
    self = [super init];
    
    if (self) {
        _locations = [[NSMutableArray alloc] init];
        [self downloadJSON];
    }
    
    return self;
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

#pragma mark -
#pragma mark - JSON Parsing and Fetching

- (void)parseJSON:(id)jsonObject
{
    NSError *error;
    NSData *data = (NSData*)jsonObject;
    
    NSJSONSerialization* json = [NSJSONSerialization
                          JSONObjectWithData:data
                          options:kNilOptions
                          error:&error];
    
    if (!error) {
        NSArray *array = [json valueForKey:@"locations"];
        [self createLocations:array];
        [self notifyDelegate];
    }
}

- (void)createLocations:(NSArray*)locations
{
    for (NSDictionary* locationDictionary in locations) {
        ARSLocation* location = [ARSLocation locationFromDictionary:locationDictionary];
        [_locations addObject:location];
    }
}

- (void)downloadJSON
{
    NSURLRequest *request = [[NSURLRequest alloc] initWithURL:kJSONURL];
    
    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    
    [operation setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
            [self parseJSON:responseObject];
        });
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"Error: %@", error);
    }];
    
    [operation start];
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
