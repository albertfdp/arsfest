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

@protocol ARSDataDelegate <NSObject>

- (void)didReceiveDataFromTheServer;

@end


@interface ARSData : NSObject

@property (nonatomic, assign) id<ARSDataDelegate> dataDelegate;
@property (nonatomic, strong) NSMutableArray *locations;
@property (nonatomic, strong) NSDictionary *wifis;

+ (ARSData*)data;

- (NSArray*)eventsIn:(ARSLocationType)location;
- (NSString*)locationNameForWifiBssid:(NSString*)bssid;


@end
