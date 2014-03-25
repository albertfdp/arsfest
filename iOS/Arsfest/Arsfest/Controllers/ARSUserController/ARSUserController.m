//
//  ARSUserController.m
//  Arsfest
//
//  Created by Thibaud Robelain on 23/03/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSUserController.h"
#import <Parse/Parse.h>

@implementation ARSUserController


+ (ARSUserController*)sharedUserController
{
    static ARSUserController* sharedController = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedController = [[ARSUserController alloc] init];
    });
    
    return sharedController;
}


+ (void)logUserWithDelegate:(id<ARSUserControllerDelegate>)delegate
{
    // The permissions requested from the user
    NSArray *permissionsArray = @[ @"user_friends" ];
    
    // Login PFUser using Facebook
    [PFFacebookUtils logInWithPermissions:permissionsArray block:^(PFUser *user, NSError *error) {
        
        if (!user) {
            if (!error) {
                [delegate userLogInCompletedWithError:ARSUserLoginCancelled];
            } else {
                [delegate userLogInCompletedWithError:ARSUserLoginUnknownError];
            }
        } else {
            [delegate userLogInCompletedWithSuccess];
        }
    }];
}

+ (void)logOutUser
{
    [PFUser logOut];
}

+ (BOOL)isUserLoggedIn
{
    return ([PFUser currentUser] && // Check if a user is cached
            [PFFacebookUtils isLinkedWithUser:[PFUser currentUser]]); // Check if user is linked to Facebook
}


@end
