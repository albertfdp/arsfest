//
//  ARSWifi.h
//  Arsfest
//
//  Created by Thibaud Robelain on 02/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Parse/Parse.h"

@interface ARSWifi : NSObject

@property (strong, nonatomic) NSString *bssid;
@property (strong, nonatomic) PFGeoPoint* location;
@property (assign, nonatomic) CGPoint pointOnMap;
@property (strong, nonatomic) NSString *locationName;
@property (strong, nonatomic) NSString *locationUniqueID;

+ (ARSWifi*)wifiFromDictionary:(NSDictionary*)dictionary;

@end
