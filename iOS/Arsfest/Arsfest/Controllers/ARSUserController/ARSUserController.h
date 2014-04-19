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

@optional
/* Callbacks to notify the delegates of the result of the facebook login flow */
- (void)userLogInCompletedWithSuccess;
- (void)userLogInCompletedWithError:(ARSUserLoginError)error;
/* Notifies the delegate that the UserController fetched the current user friends and their locations */
- (void)userControllerRetrievedUserFriends:(NSArray*)friends;
- (void)userControllerFailedToRetrieveFriends;

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

/*
 * Updates the user location to the server if the user is currently connected to Facebook and to the WiFi
 */
- (void)updateUserLocation;
- (NSString*)userLocation;

/* Returns true if the iPhone is on a wifi*/
- (BOOL)localWiFiAvailable;

/*
 * Fetches the user friends location and notifies the specified delegate
 */
- (void)fetchFriendsLocationWithDelegate:(id<ARSUserControllerDelegate>)delegate enforceRefresh:(BOOL)refreshCache;


@end
