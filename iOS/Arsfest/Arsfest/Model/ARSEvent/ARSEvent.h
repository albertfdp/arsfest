//
//  ARSEvent.h
//  Arsfest
//
//  Created by Thibaud Robelain on 06/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ARSLocation.h"

@interface ARSEvent : NSObject
{
    ARSLocation *location;
}

+ (ARSEvent*)eventFromDictionary:(NSDictionary*)dictionary;

@property (nonatomic, strong) NSString *eventId;
@property (nonatomic, strong) NSString *name;
@property (nonatomic, strong) NSString *image;
@property (nonatomic, weak) ARSLocation *location;
@property (nonatomic, strong) NSString *description;
@property (nonatomic, strong) NSString *type;
@property (nonatomic, strong) NSString *theme;

@end
