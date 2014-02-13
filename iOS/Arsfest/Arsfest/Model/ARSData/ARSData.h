//
//  ARSData.h
//  Arsfest
//
//  Created by Thibaud Robelain on 07/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ARSLocation.h"


@interface ARSData : NSObject

@property (nonatomic, strong) NSMutableArray *locations;

- (NSArray*)eventsIn:(ARSLocationType)location;


@end
