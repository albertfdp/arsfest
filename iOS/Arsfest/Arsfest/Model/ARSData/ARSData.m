//
//  ARSData.m
//  Arsfest
//
//  Created by Thibaud Robelain on 07/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSData.h"
#import "ARSLocation.h"
#import "AFNetworking.h"

#define kJSONURL                [NSURL URLWithString:@"https://raw2.github.com/albertfdp/arsfest/master/iOS/test.json"]

@interface ARSData()

- (void)downloadJSON;
- (void)parseJSON:(id)jsonObject;

@end

@implementation ARSData
@synthesize locations = _locations;

- (id)init
{
    self = [super init];
    
    if (self) {
        [self downloadJSON];
    }
    
    return self;
}

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

@end
