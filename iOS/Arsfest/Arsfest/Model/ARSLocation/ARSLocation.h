//
//  ARSLocation.h
//  Arsfest
//
//  Created by Thibaud Robelain on 06/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <Parse/Parse.h>

typedef enum {
    ARSLocationSportsHall,
    ARSLocationLibrary,
    ARSLocationOticonSalen,
    ARSLocationTent,
    ARSLocationCanteen,
    ARSLocationAll,
} ARSLocationType;

@class ARSEvent;
@interface ARSLocation : NSObject

@property (nonatomic, assign) ARSLocationType type;

@property (nonatomic, strong) NSString *locationId;
@property (nonatomic, strong) NSString *name;
@property (nonatomic, strong) NSString *description;
@property (nonatomic, strong) PFGeoPoint *coordinates;
@property (nonatomic, strong) NSMutableArray *events;

+ (ARSLocation*)locationFromDictionary:(NSDictionary*)dictionary;

- (void)addEvent:(ARSEvent*)event;

@end
