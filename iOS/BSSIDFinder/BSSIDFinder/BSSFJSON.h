//
//  BSSFJSON.h
//  BSSIDFinder
//
//  Created by Jose Luis Bellod Cisneros on 23/04/14.
//  Copyright (c) 2014 Jose Luis Bellod Cisneros. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface BSSFJSON : NSObject
@property (strong, nonatomic) NSString *latitude;
@property (strong, nonatomic) NSString *longitude;
@property (strong, nonatomic) NSString *bssid;
@property (strong, nonatomic) NSString *location;

@end
