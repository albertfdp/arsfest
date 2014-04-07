//
//  ARSAppDelegate.m
//  Arsfest
//
//  Created by Thibaud Robelain on 06/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#define kParseApplicationId @"vDLtONCuZIlrt6wEnfHrFbIDVhoW3cxUCAGEx2j5"
#define kParseClientKey @"ZLgCiJAv5TkA4ChPgeLSnKb5S4dszCMBuVjypPcc"

#import "ARSAppDelegate.h"
#import <Parse/Parse.h>
#import "ARSEventListViewController.h"

@implementation ARSAppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    // Override point for customization after application launch.
    self.window.backgroundColor = [UIColor whiteColor];
    
    //Parse setup
    [Parse setApplicationId:kParseApplicationId
                  clientKey:kParseClientKey];
    [PFAnalytics trackAppOpenedWithLaunchOptions:launchOptions];
    
    
    ARSEventListViewController *eventListViewController = [[ARSEventListViewController alloc] initWithNibName:@"ARSEventListViewController" bundle:nil];
    
    ARSNavigationController *navigationController = [[ARSNavigationController alloc]
                                                    initWithRootViewController:eventListViewController];
    
    [self.window setRootViewController:navigationController];
    
    [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleLightContent];
    
    [self.window makeKeyAndVisible];
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (BOOL)application:(UIApplication *)application
            openURL:(NSURL *)url
  sourceApplication:(NSString *)sourceApplication
         annotation:(id)annotation {
    return [FBAppCall handleOpenURL:url
                  sourceApplication:sourceApplication
                        withSession:[PFFacebookUtils session]];
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
    [FBAppCall handleDidBecomeActiveWithSession:[PFFacebookUtils session]];
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}


@end
