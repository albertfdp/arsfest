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
    
    [self customizeFacebookLoginButton];

    [self.segmentedControl addObserver:self forKeyPath:@"selectedSegmentIndex" options:NSKeyValueObservingOptionNew context:nil];
    [self.segmentedControl setSelectedSegmentIndex:0];
    [self.segmentedControl setTintColor:kArsfestColor];
    
}

- (void)viewWillAppear:(BOOL)animated
{
    [self configureViewForSelectedIndex:self.segmentedControl.selectedSegmentIndex];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)initializeViews
{
    [self.containerView addSubview:self.registerView];
    [self.registerView setHidden:YES];
    [self.containerView addSubview:self.friendListView];
    [self.friendListView setHidden:YES];
    [self.containerView addSubview:self.mapView];
    [self.mapView setHidden:YES];
}

#pragma mark -
#pragma mark - View changes

- (void)customizeFacebookLoginButton
{
    [self.loginButton.layer setBorderColor:[[UIColor lightGrayColor] CGColor]];
    [self.loginButton.layer setBorderWidth:1.0f];
    [self.loginButton.layer setCornerRadius:5.0f];
}

- (void)configureViewForSelectedIndex:(NSInteger)index
{
    switch (index) {
        case 0:
        {
            [self.mapView setHidden:NO];
            [self.registerView setHidden:YES];
            [self.friendListView setHidden:YES];
            
            [self setTitle:@"Map"];
            break;
        }
        case 1:
        {
            if ([ARSUserController isUserLoggedIn]) {
                [self.mapView setHidden:YES];
                [self.registerView setHidden:YES];
                [self.friendListView setHidden:NO];
            } else {
                [self.mapView setHidden:YES];
                [self.registerView setHidden:NO];
                [self.friendListView setHidden:YES];
            }
            
            [self setTitle:@"Friends"];
            break;
        }
    }
}

- (IBAction)didChangeSegmentedControl:(id)sender {

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

#pragma mark -
#pragma mark - KVO

- (void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary *)change context:(void *)context
{
    if ([keyPath isEqualToString:@"selectedSegmentIndex"]) {
        UISegmentedControl *segmControl = (UISegmentedControl*)object;
        [self configureViewForSelectedIndex:segmControl.selectedSegmentIndex];
    }
}

@end
