//
//  ARSUserController.m
//  Arsfest
//
//  Created by Thibaud Robelain on 23/03/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSUserController.h"
#import "ARSAlertManager.h"
#import "ARSData.h"
#import <Parse/Parse.h>
#import <SystemConfiguration/CaptiveNetwork.h>

#import <arpa/inet.h> // For AF_INET, etc.
#import <ifaddrs.h> // For getifaddrs()
#import <net/if.h> // For IFF_LOOPBACK

typedef NS_ENUM(NSInteger, kService) {
    kFacebookService,
    kParseService
};

@interface ARSUserController()

@property (nonatomic, retain) NSString *lastBSSID;
@property (nonatomic, retain) NSArray *userFriends;
@property (nonatomic, retain) NSArray *parseUsers;
@property (nonatomic, retain) NSString *userPosition;

- (NSString *)currentWifiBSSID;
- (BOOL)localWiFiAvailable;
- (void)queryUserFriendsWithIds:(NSArray*)friendsIds delegate:(id<ARSUserControllerDelegate>)delegate enforceRefresh:(BOOL)refreshCache;

@end

@implementation ARSUserController
@synthesize lastBSSID;
@synthesize userFriends;
@synthesize parseUsers;
@synthesize userPosition;

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
    NSArray *permissionsArray = @[ @"user_about_me" , @"user_friends" ];
    
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
            //Fetching user's facebook id and assigning it to the PFUSer
            [FBRequestConnection startForMeWithCompletionHandler:^(FBRequestConnection *connection, id result, NSError *error) {
                if (!error) {
                    [[PFUser currentUser] setObject:[result objectForKey:@"id"]
                                             forKey:@"fbId"];
                    [[PFUser currentUser] setObject:[result objectForKey:@"name"] forKey:@"name"];
                    NSString *pictureURL = [NSString stringWithFormat:@"https://graph.facebook.com/%@/picture?type=large&return_ssl_resources=1", [result objectForKey:@"id"]];
                    [[PFUser currentUser] setObject:pictureURL forKey:@"pictureURL"];
                    [[PFUser currentUser] saveInBackground];
                    
                }
            }];
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
#pragma mark - Location handler

- (NSString *)currentWifiBSSID {
    
    NSString *bssid = nil;
    NSArray *ifs = (__bridge_transfer id)CNCopySupportedInterfaces();
    for (NSString *ifnam in ifs) {
        NSDictionary *info = (__bridge_transfer id)CNCopyCurrentNetworkInfo((__bridge CFStringRef)ifnam);
        
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

- (void)updateUserLocation
{
    if ([self localWiFiAvailable] && [ARSUserController isUserLoggedIn]) {
        
        NSString *locationName = [self userLocation];
        if (locationName) {
            [[PFUser currentUser] setObject:locationName forKey:@"locationName"];
            [[PFUser currentUser] saveInBackground];   
        } else if ( ![[PFUser currentUser] objectForKey:@"locationName"] ) {
            [[PFUser currentUser] setObject:@"Unknown Location" forKey:@"locationName"];
        }
    } else {
#warning How to handle this?
        //WiFi not available
//        if (![self localWiFiAvailable] && ! [ARSUserController isUserLoggedIn]) {
//            [ARSAlertManager showErrorWithTitle:@"Wi-Fi Not Enabled" message:@"Please enable the Wi-Fi to get your current location" cancelTitle:@"OK"];
//        }
    }
}

- (NSString*)userLocation
{
    NSString *currentBSSID = [self currentWifiBSSID];
    
    //Get name of the BSSID's location
    NSString *locationName = [[ARSData data] locationNameForWifiBssid:currentBSSID];
    
    userPosition = locationName;
    return locationName;
}

#pragma mark -
#pragma mark - Friends Handler

- (void)fetchFriendsLocationWithDelegate:(id<ARSUserControllerDelegate>)delegate enforceRefresh:(BOOL)refreshCache
{
    BOOL shouldRefreshFacebookFriends = [self shouldRefreshUsersFromService:kFacebookService];
    if (!userFriends || shouldRefreshFacebookFriends) {
        // Issue a Facebook Graph API request to get your user's friend list
        [FBRequestConnection startForMyFriendsWithCompletionHandler:^(FBRequestConnection *connection, id result, NSError *error) {
            if (!error) {
                // result will contain an array with your user's friends in the "data" key
                userFriends = [result objectForKey:@"data"];
                [self queryUserFriendsWithIds:userFriends delegate:delegate enforceRefresh:refreshCache];
            } else {
#warning Implement Facebook Error Mechanism
            }
        }];
    } else {
        [self queryUserFriendsWithIds:userFriends delegate:delegate enforceRefresh:refreshCache];
    }
}

- (void)queryUserFriendsWithIds:(NSArray*)friendsIds delegate:(id<ARSUserControllerDelegate>)delegate enforceRefresh:(BOOL)refreshCache
{
    refreshCache = ( refreshCache || [self shouldRefreshUsersFromService:kParseService] );
    if (parseUsers && !refreshCache) {
        [delegate userControllerRetrievedUserFriends:parseUsers];
    } else {
        NSMutableArray *friendIds = [NSMutableArray arrayWithCapacity:userFriends.count];
        // Create a list of friends' Facebook IDs
        for (NSDictionary *friendObject in userFriends) {
            [friendIds addObject:[friendObject objectForKey:@"id"]];
        }
        
        PFQuery *friendQuery = [PFUser query];
        //    [friendQuery whereKey:@"fbId" containedIn:friendIds];
        
        [friendQuery findObjectsInBackgroundWithBlock:^(NSArray *objects, NSError *error){
            if (!error) {
                if ([delegate respondsToSelector:@selector(userControllerRetrievedUserFriends:)]) {
                    parseUsers = objects;
                    [delegate userControllerRetrievedUserFriends:objects];
                }
            } else {
                if ([delegate respondsToSelector:@selector(userControllerFailedToRetrieveFriends)]) {
                    [delegate userControllerFailedToRetrieveFriends];
                }
            }
        }];
    }
}

- (BOOL)shouldRefreshUsersFromService:(kService)service
{
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    NSString *key = (service == kFacebookService)?LAST_FACEBOOK_REFRESH:LAST_PARSE_REFRESH;
    if ([userDefaults objectForKey:key]) {
        NSDate *oldDate = (NSDate*)[userDefaults objectForKey:key];
        BOOL shouldRefresh = nil;
        if (service == kFacebookService) {
            shouldRefresh = ![oldDate isOnSameDayAsDate:[NSDate date] inTimeZone:[NSTimeZone systemTimeZone]];
        } else {
            shouldRefresh = [oldDate isEarlierThanDate:[NSDate date] fromMinutes:REFRESH_PARSE_AFTER];
        }
        return shouldRefresh;
    } else {
        [userDefaults setObject:[NSDate date] forKey:key];
        return YES;
    }
}

@end
