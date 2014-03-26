//
//  ARSUserController.m
//  Arsfest
//
//  Created by Thibaud Robelain on 23/03/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSUserController.h"
#import "ARSAlertManager.h"
#import <Parse/Parse.h>
#import <SystemConfiguration/CaptiveNetwork.h>

#import <arpa/inet.h> // For AF_INET, etc.
#import <ifaddrs.h> // For getifaddrs()
#import <net/if.h> // For IFF_LOOPBACK

@interface ARSUserController()

@property (nonatomic, retain) NSString *lastBSSID;
@property (nonatomic, retain) NSArray *userFriends;

- (NSString *)currentWifiBSSID;
- (BOOL)localWiFiAvailable;

@end

@implementation ARSUserController
@synthesize lastBSSID;
@synthesize userFriends;

#pragma mark -
#pragma mark - Initialization

- (id)init
{
    self = [super init];
    if (self) {
        lastBSSID = nil;
    }
    return self;
}

+ (ARSUserController*)sharedUserController
{
    static ARSUserController* sharedController = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedController = [[ARSUserController alloc] init];
    });
    
    return sharedController;
}

#pragma mark -
#pragma mark - Log in/out methods

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
            //Fetching user's facebook id and assigning it to the PFUSer
            [FBRequestConnection startForMeWithCompletionHandler:^(FBRequestConnection *connection, id result, NSError *error) {
                if (!error) {
                    [[PFUser currentUser] setObject:[result objectForKey:@"id"]
                                             forKey:@"fbId"];
                    [[PFUser currentUser] saveInBackground];
                }
            }];
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

#pragma mark -
#pragma mark - Location methods

- (NSString *)currentWifiBSSID {
    
    NSString *bssid = nil;
    NSArray *ifs = (__bridge_transfer id)CNCopySupportedInterfaces();
    for (NSString *ifnam in ifs) {
        NSDictionary *info = (__bridge_transfer id)CNCopyCurrentNetworkInfo((__bridge CFStringRef)ifnam);
        
        NSLog(@"info:%@",info);
        
        if (info[@"BSSID"]) {
            bssid = info[@"BSSID"];
        }
    }
    
    return bssid;
}

- (BOOL)localWiFiAvailable
{
    struct ifaddrs *addresses;
    struct ifaddrs *cursor;
    BOOL wiFiAvailable = NO;
    if (getifaddrs(&addresses) != 0) return NO;
    
    cursor = addresses;
    while (cursor != NULL) {
    	if (cursor -> ifa_addr -> sa_family == AF_INET
    		&& !(cursor -> ifa_flags & IFF_LOOPBACK)) // Ignore the loopback address
    	{
    		// Check for WiFi adapter
    		if (strcmp(cursor -> ifa_name, "en0") == 0) {
    			wiFiAvailable = YES;
    			break;
    		}
    	}
    	cursor = cursor -> ifa_next;
    }
    
    freeifaddrs(addresses);
    return wiFiAvailable;
}

- (PFGeoPoint*)locationFromBSSID:(NSString*)bssid
{
#warning YET TO BE IMPLEMENTED
    
    PFGeoPoint *location = [[PFGeoPoint alloc] init];
    location.longitude = 52;
    location.latitude = 52;
    return location;
}

- (void)updateUserLocation
{
    if ([self localWiFiAvailable] && [ARSUserController isUserLoggedIn]) {
        lastBSSID = [self currentWifiBSSID];
        
        //Get coordinates of the BSSID
        PFGeoPoint *location = [self locationFromBSSID:lastBSSID];
        //If BSSID or WiFi not connected, location is updated to nil
        [[PFUser currentUser] setObject:location forKey:@"location"];
        [[PFUser currentUser] saveInBackground];
        
    } else {
        //WiFi not available
        if (![self localWiFiAvailable]) {
            [ARSAlertManager showErrorWithTitle:@"Wi-Fi Not Enabled" message:@"Please enable the Wi-Fi to get your current location" cancelTitle:@"OK"];
        }
    }
}

- (void)fetchFriendsLocationWithDelegate:(id<ARSUserControllerDelegate>)delegate
{
    if (!userFriends) {
        // Issue a Facebook Graph API request to get your user's friend list
        [FBRequestConnection startForMyFriendsWithCompletionHandler:^(FBRequestConnection *connection, id result, NSError *error) {
            if (!error) {
                // result will contain an array with your user's friends in the "data" key
                userFriends = [result objectForKey:@"data"];
                [self queryUserFriendsWithIds:userFriends delegate:delegate];
            }
        }];
    } else {
        [self queryUserFriendsWithIds:userFriends delegate:delegate];
    }
}

- (void)queryUserFriendsWithIds:(NSArray*)friendsIds delegate:(id<ARSUserControllerDelegate>)delegate
{
    NSMutableArray *friendIds = [NSMutableArray arrayWithCapacity:userFriends.count];
    // Create a list of friends' Facebook IDs
    for (NSDictionary *friendObject in userFriends) {
        [friendIds addObject:[friendObject objectForKey:@"id"]];
    }
    
    // Construct a PFUser query that will find friends whose facebook ids
    // are contained in the current user's friend list.
    PFQuery *friendQuery = [PFUser query];
    [friendQuery whereKey:@"fbId" containedIn:friendIds];
    
    // findObjects will return a list of PFUsers that are friends
    // with the current user
    
    [friendQuery findObjectsInBackgroundWithBlock:^(NSArray *objects, NSError *error){
        if (!error) {
            if ([delegate respondsToSelector:@selector(userControllerRetrievedUserFriends:)]) {
                [delegate userControllerRetrievedUserFriends:objects];
            }
        } else {
            if ([delegate respondsToSelector:@selector(userControllerFailedToRetrieveFriends)]) {
                [delegate userControllerFailedToRetrieveFriends];
            }
        }
    }];
}


@end
