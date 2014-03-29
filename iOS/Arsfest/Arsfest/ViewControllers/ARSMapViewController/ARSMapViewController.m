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
@property (nonatomic, retain) UIBarButtonItem *refreshBarButtonItem;

/* Add the map to the view and set the content size of the scrollview */
- (void)initializeMapScrollView;

/* Configures the view when the selected index of the segmented control changes */
- (void)configureViewForSelectedIndex:(NSInteger)index;

@end

@implementation ARSMapViewController
@synthesize friends;
@synthesize mapImageView;
@synthesize refreshBarButtonItem;

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
    
    //KVO Registration
    [self.friendListView addObserver:self forKeyPath:@"hidden" options:NSKeyValueObservingOptionNew context:nil];
    [self.friendListDataView addObserver:self forKeyPath:@"hidden" options:NSKeyValueObservingOptionNew context:nil];
    [self addObserver:self forKeyPath:@"friends" options:0 context:NULL];
    [self.segmentedControl addObserver:self forKeyPath:@"selectedSegmentIndex" options:NSKeyValueObservingOptionNew context:nil];
    
    //Segmented control initialization
    [self.segmentedControl setSelectedSegmentIndex:0];
    [self.segmentedControl setTintColor:kArsfestColor];
    
    [self initializeMapScrollView];
    
    //Navigation bar buttons
    UIImage *rightImage = [UIImage imageNamed:@"close.png"];
    UIBarButtonItem *rightItem = [UIBarButtonItem itemWithImage:rightImage target:self action:@selector(dismissMap)];
    [self.navigationController.navigationBar.topItem setRightBarButtonItem:rightItem];

    UIImage *refreshImage = [UIImage imageNamed:@"Refresh.png"];
    refreshBarButtonItem = [UIBarButtonItem itemWithImage:refreshImage target:self action:@selector(refreshFriendsLocation)];
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

- (void)dealloc
{
    [self.friendListView removeObserver:self forKeyPath:@"hidden"];
    [self.friendListDataView removeObserver:self forKeyPath:@"hidden"];
    [self removeObserver:self forKeyPath:@"friends"];
    [self.segmentedControl removeObserver:self forKeyPath:@"selectedSegmentIndex"];
}

#pragma mark -
#pragma mark - Scroll view delegate

-(UIView *) viewForZoomingInScrollView:(UIScrollView *)scrollView
{
    return self.mapImageView;
}

#pragma mark -
#pragma mark - View changes

- (void)initializeMapScrollView
{
    self.mapScrollView.delegate = self;
    self.mapImageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"buildingmap.png"]];
    [self.mapScrollView addSubview:self.mapImageView];
    [self.mapScrollView setContentSize:self.mapImageView.image.size];
}

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
            
            [self.navigationController.navigationBar.topItem setLeftBarButtonItem:nil];
            
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
            
            [self.navigationController.navigationBar.topItem setLeftBarButtonItem:refreshBarButtonItem];
            
            break;
        }
    }
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
//    NSMutableArray *arr = [[NSMutableArray alloc] init];
//    [arr addObjectsFromArray:_friends];
//    for (int i =0 ; i<2; i++) {
//        [arr addObject:[arr objectAtIndex:0]];
//    }
//    [self setFriends:[NSArray arrayWithArray:arr]];
    [self setFriends:_friends];
    [self showFriendListProcessingView:NO];
}

- (void)userControllerFailedToRetrieveFriends
{
    [[ARSUserController sharedUserController] fetchFriendsLocationWithDelegate:self enforceRefresh:YES];
}

- (void)userLogInCompletedWithError:(ARSUserLoginError)error
{
    self.containerView = self.registerView;
    [ARSAlertManager showErrorWithTitle:@"Registration error" message:@"The registration couldn't be completed" cancelTitle:@"OK"];
}

- (void)userLogInCompletedWithSuccess
{
    [self configureViewForSelectedIndex:self.segmentedControl.selectedSegmentIndex];
}

- (void)refreshFriendsLocation
{
    [self showFriendListProcessingView:YES];
    [[ARSUserController sharedUserController] fetchFriendsLocationWithDelegate:self enforceRefresh:YES];
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
            [[ARSUserController sharedUserController] fetchFriendsLocationWithDelegate:self enforceRefresh:NO];
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
