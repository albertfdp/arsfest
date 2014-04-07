//
//  ARSAlertManager.m
//  Arsfest
//
//  Created by Thibaud Robelain on 25/03/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSAlertManager.h"

@implementation ARSAlertManager

+ (void)showErrorWithTitle:(NSString*)title message:(NSString*)message cancelTitle:(NSString*)cancelTitle
{
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:title message:message delegate:nil cancelButtonTitle:cancelTitle otherButtonTitles:nil];
    
    if ([UIApplication sharedApplication].applicationState == UIApplicationStateActive) {
        [alertView show];
    }
}

@end
