//
//  ARSAlertManager.h
//  Arsfest
//
//  Created by Thibaud Robelain on 25/03/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ARSAlertManager : NSObject

/*  Show an alert message with the specified arguments */
+ (void)showErrorWithTitle:(NSString*)title message:(NSString*)message cancelTitle:(NSString*)cancelTitle;

@end
