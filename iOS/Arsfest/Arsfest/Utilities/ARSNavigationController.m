//
//  ARSNavigationController.m
//  Arsfest
//
//  Created by Thibaud Robelain on 26/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSNavigationController.h"
#import "ARSMapViewController.h"
#import "ARSInfoViewController.h"

@interface ARSNavigationController ()

@end

@implementation ARSNavigationController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.delegate = self;
    }
    return self;
}

- (id)initWithRootViewController:(UIViewController *)rootViewController
{
    self = [super initWithRootViewController:rootViewController];
    
    if (self) {
        self.delegate = self;
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [self.navigationBar setBarTintColor:kArsfestColor];
    [self.navigationBar setTintColor:[UIColor whiteColor]];
    [self.navigationBar setTranslucent:YES];
    
    NSMutableDictionary *titleAttributes = [[NSMutableDictionary alloc] init];
    [titleAttributes setObject:[UIColor whiteColor] forKey:NSForegroundColorAttributeName];
    
    [[UINavigationBar appearance] setTitleTextAttributes:titleAttributes];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)navigationController:(UINavigationController *)navigationController willShowViewController:(UIViewController *)viewController animated:(BOOL)animated
{
    viewController.navigationItem.backBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"" style:UIBarButtonItemStylePlain target:nil action:nil];
    
    // Map
    UIImage *rightImage = [UIImage imageNamed:@"location_icon.png"];
    UIBarButtonItem *rightItem = [UIBarButtonItem itemWithImage:rightImage target:self action:@selector(showMap)];
    
    [viewController.navigationItem setRightBarButtonItem:rightItem];
}

#pragma mark -
#pragma mark - Map handling

- (void)showMap
{
    ARSMapViewController *mapViewController = [[ARSMapViewController alloc] initWithNibName:@"ARSMapViewController" bundle:[NSBundle mainBundle]];
    UINavigationController *navigationController = [[UINavigationController alloc] initWithRootViewController:mapViewController];
    [navigationController.navigationBar setBarTintColor:kArsfestColor];
    
    [self presentViewController:navigationController animated:YES completion:nil];
}

@end
