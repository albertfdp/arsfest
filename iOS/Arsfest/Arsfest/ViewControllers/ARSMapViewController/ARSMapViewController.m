//
//  ARSMapViewController.m
//  Arsfest
//
//  Created by Thibaud Robelain on 26/03/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSMapViewController.h"
#import "ARSAlertManager.h"
#import "ARSFriendListCell/ARSFriendListCell.h"

@interface ARSMapViewController ()

@property (nonatomic, retain) NSArray *friends;

@end

@implementation ARSMapViewController
@synthesize friends;

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
    
    [self.friendListView addObserver:self forKeyPath:@"hidden" options:NSKeyValueObservingOptionNew context:nil];
    [self.friendListDataView addObserver:self forKeyPath:@"hidden" options:NSKeyValueObservingOptionNew context:nil];

    [self addObserver:self
           forKeyPath:@"friends"
              options:0
              context:NULL];

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

- (void)showFriendListProcessingView:(BOOL)processing
{
    self.friendListDataView.hidden = processing;
    self.friendListLoadingView.hidden = !processing;
}

#pragma mark -
#pragma mark - Register with Facebook view

- (IBAction)loginUser:(id)sender {
    [ARSUserController logUserWithDelegate:self];
}

#pragma mark -
#pragma mark - User controller delegate

- (void)userControllerRetrievedUserFriends:(NSArray *)_friends
{
    [self setFriends:_friends];
    [self showFriendListProcessingView:NO];
}

- (void)userControllerFailedToRetrieveFriends
{
    [[ARSUserController sharedUserController] fetchFriendsLocationWithDelegate:self]; 
}

- (void)userLogInCompletedWithError:(ARSUserLoginError)error
{
    self.containerView = self.registerView;
    [ARSAlertManager showErrorWithTitle:@"Registration error" message:@"The registration couldn't complete" cancelTitle:@"OK"];
}

- (void)userLogInCompletedWithSuccess
{
    [self configureViewForSelectedIndex:self.segmentedControl.selectedSegmentIndex];
}

#pragma mark -
#pragma mark - KVO

- (void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary *)change context:(void *)context
{
    if ([keyPath isEqualToString:@"selectedSegmentIndex"]) {
        UISegmentedControl *segmControl = (UISegmentedControl*)object;
        [self configureViewForSelectedIndex:segmControl.selectedSegmentIndex];
    } else if ([keyPath isEqualToString:@"friends"]) {
        [self.friendsListTableView reloadData];
    }
    
    if (object == self.friendListView) {
        if (!self.friendListView.hidden) {
            [self showFriendListProcessingView:YES];
            [[ARSUserController sharedUserController] fetchFriendsLocationWithDelegate:self];
        }
    }
    
    if (object == self.friendListDataView) {
        BOOL shouldHideTableView = (!self.friendListDataView.hidden && [friends count] == 0);
        self.friendsListTableView.hidden = shouldHideTableView;
        self.noFriendsRegisteredLabel.hidden = !shouldHideTableView;
    }
}

#pragma mark -
#pragma mark - Dismiss view controller

- (void)dismissMap
{
    [self dismissViewControllerAnimated:YES completion:nil];
}

#pragma mark -
#pragma mark - Table view delegate and data source

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [friends count];
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return FRIEND_CELL_HEIGHT;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdentifier = @"ARSFriendListCell";
    ARSFriendListCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if (!cell) {
        NSArray *topLevelObjects =[[NSBundle mainBundle] loadNibNamed:@"ARSFriendListCell" owner:self options:nil];
        cell = [topLevelObjects lastObject];
    }
    
    [cell configureCellWithUser:[friends objectAtIndex:indexPath.row]];
    
    return cell;
}

@end
