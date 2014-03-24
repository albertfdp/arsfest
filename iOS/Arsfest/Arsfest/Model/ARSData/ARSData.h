//
//  ARSData.h
//  Arsfest
//
//  Created by Thibaud Robelain on 07/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ARSLocation.h"

@protocol ARSDataDelegate <NSObject>

- (void)didReceiveDataFromTheServer;

@end


@interface ARSData : NSObject

@property (nonatomic, assign) id<ARSDataDelegate> dataDelegate;
@property (nonatomic, strong) NSMutableArray *locations;

- (NSArray*)eventsIn:(ARSLocationType)location;


@end
