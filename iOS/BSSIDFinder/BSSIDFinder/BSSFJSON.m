//
//  BSSFJSON.m
//  BSSIDFinder
//
//  Created by Jose Luis Bellod Cisneros on 23/04/14.
//  Copyright (c) 2014 Jose Luis Bellod Cisneros. All rights reserved.
//

#import "BSSFJSON.h"

@implementation BSSFJSON



- (id)init
{
    self = [super init];
    
    if (self) {
    }
    
    return self;
}

+ (BSSFJSON*)data
{
    static BSSFJSON* data = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        data = [[BSSFJSON alloc] init];
    });
    return data;
}

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

@end