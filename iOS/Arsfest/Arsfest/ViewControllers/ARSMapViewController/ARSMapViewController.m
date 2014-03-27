//
//  ARSMapViewController.m
//  Arsfest
//
//  Created by Thibaud Robelain on 26/03/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSMapViewController.h"
#import "ARSAlertManager.h"

@interface ARSMapViewController ()

@end

@implementation ARSMapViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
}

- (void)viewWillAppear:(BOOL)animated
{
    [self setTitle:@"Map"];
    
    [self configureViewForSelectedIndex:self.segmentedControl.selectedSegmentIndex];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark -
#pragma mark - View changes

- (void)configureViewForSelectedIndex:(NSInteger)index
{
    switch (index) {
        case 0:
        {
            [self.containerView addSubview:self.mapView];
            break;
        }
        case 1:
        {
            if ([ARSUserController isUserLoggedIn]) {
                [self.containerView addSubview:self.friendListView];
            } else {
                [self.containerView addSubview:self.registerView];
            }
            break;
        }
    }
}

- (IBAction)didChangeSegmentedControl:(id)sender {
    UISegmentedControl *segmentedControl = (UISegmentedControl*)sender;
    [self configureViewForSelectedIndex:segmentedControl.selectedSegmentIndex];
}

#pragma mark -
#pragma mark - Register with Facebook view
- (IBAction)loginUser:(id)sender {
    [ARSUserController logUserWithDelegate:self];
}

#pragma mark -
#pragma mark - User controller delegate

- (void)userControllerRetrievedUserFriends:(NSArray *)friends
{
    
}

- (void)userControllerFailedToRetrieveFriends
{
    
}

- (void)userLogInCompletedWithError:(ARSUserLoginError)error
{
    self.containerView = self.registerView;
    [ARSAlertManager showErrorWithTitle:@"Facebook Error" message:@"The registration couldn't complete" cancelTitle:@"OK"];
}

- (void)userLogInCompletedWithSuccess
{
    self.containerView = self.friendListView;
}

- (void)removeLoadingView
{
    
}

@end
