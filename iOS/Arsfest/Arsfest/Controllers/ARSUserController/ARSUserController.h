//
//  ARSUserController.h
//  Arsfest
//
//  Created by Thibaud Robelain on 23/03/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef NS_ENUM(NSInteger, ARSUserLoginError) {
    ARSUserLoginCancelled,
    ARSUserLoginUnknownError
};

@protocol ARSUserControllerDelegate <NSObject>

- (void)userLogInCompletedWithSuccess;
- (void)userLogInCompletedWithError:(ARSUserLoginError)error;

@end

/**
 * This class is responsible for handling API calls to Facebook in order to retrieve
 * informations about the user of the application and its friends.
 * It is a singleton and needs to be accessed by its class accessor. 
 * However, some methods may change to just access the controller through class methods
 */

@interface ARSUserController : NSObject

+ (ARSUserController*)sharedUserController;

/* Log the user to Facebook with the specified delegate */
+ (void)logUserWithDelegate:(id<ARSUserControllerDelegate>)delegate;

/* Log out the user */
+ (void)logOutUser;

/* Asks if a user is currently logged in
 * Warning: It does not specify if the session is active but invalidated
 */
+ (BOOL)isUserLoggedIn;


- (void)updateUserLocation;


@end
