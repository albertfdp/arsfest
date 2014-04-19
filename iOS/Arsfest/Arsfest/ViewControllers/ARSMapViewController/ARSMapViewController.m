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
@property (nonatomic, retain) UIBarButtonItem *locateMeButtonItem;
@property (nonatomic, retain) UIBarButtonItem *logoutButtonItem;
@property (nonatomic, retain) UIBarButtonItem *flexibleButtonItem;

//Location indicator on map
@property (nonatomic, weak) CAShapeLayer *circleLayer;
@property (nonatomic) CGPoint circleCenter;
@property (nonatomic) CGFloat circleRadius;

/* Add the map to the view and set the content size of the scrollview */
- (void)initializeMapScrollView;

/* Configures the view when the selected index of the segmented control changes */
- (void)configureViewForSelectedIndex:(NSInteger)index;

@end

@implementation ARSMapViewController
@synthesize friends;
@synthesize mapImageView;
@synthesize refreshBarButtonItem;
@synthesize locateMeButtonItem;
@synthesize logoutButtonItem;
@synthesize flexibleButtonItem;

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
    
    //Important: initialize these buttons before anything else
    refreshBarButtonItem = [UIBarButtonItem itemWithImage:[UIImage imageNamed:@"refresh.png"] target:self action:@selector(refreshFriendsLocation)];
    locateMeButtonItem = [UIBarButtonItem itemWithImage:[UIImage imageNamed:@"locateme.png"] target:self action:@selector(locateUser)];
    logoutButtonItem = [UIBarButtonItem itemWithImage:[UIImage imageNamed:@"logoutbutton.png"] target:self action:@selector(logOut)];
    flexibleButtonItem = [[UIBarButtonItem alloc]
                          initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace
                          target:nil action:nil];

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
    
    [self.mapScrollView setZoomScale:0.6];
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
            
            [self.toolbar setItems:@[flexibleButtonItem, self.segmentedControlButtonItem, flexibleButtonItem, locateMeButtonItem]];
            
            break;
        }
        case 1:
        {
            if ([ARSUserController isUserLoggedIn]) {
                [self.mapView setHidden:YES];
                [self.registerView setHidden:YES];
                [self.friendListView setHidden:NO];
                
                [self.toolbar setItems:@[logoutButtonItem, flexibleButtonItem, self.segmentedControlButtonItem, flexibleButtonItem, refreshBarButtonItem]];
            } else {
                [self.mapView setHidden:YES];
                [self.registerView setHidden:NO];
                [self.friendListView setHidden:YES];
                
                [self.toolbar setItems:@[flexibleButtonItem, self.segmentedControlButtonItem, flexibleButtonItem]];
            }
            
            [self setTitle:@"Friends"];
            
            break;
        }
    }
}

- (void)showFriendListProcessingView:(BOOL)processing
{
    self.friendListDataView.hidden = processing;
    self.friendListLoadingView.hidden = !processing;
}

#pragma mark - Locate user

- (void)locateUser
{
    if ([[ARSUserController sharedUserController] localWiFiAvailable]) {
        CGPoint coordinates = [self mapPointFromLocation:[[ARSUserController sharedUserController] userLocation]];
        [self addLocationIndicatorToMapAt:coordinates];
    } else {
        [ARSAlertManager showErrorWithTitle:@"Wi-Fi Not Available" message:@"Please activate the Wi-Fi and try again" cancelTitle:@"OK"];
    }
}

- (CGPoint)mapPointFromLocation:(NSString*)locationName
{
    if ([locationName isEqualToString:@"Library"]) {
        return CGPointMake(380, 265);
    } else if ([locationName isEqualToString:@"Cantine"]) {
        return CGPointMake(725, 290);
    } else if ([locationName isEqualToString:@"Oticon Hall"]) {
        return CGPointMake(1050, 115);
    } else if ([locationName isEqualToString:@"Sports Hall"]) {
        return CGPointMake(1065, 535);
    }
    return CGPointMake(0, 0);
}

- (UIBezierPath *)makeCircleAtLocation:(CGPoint)location radius:(CGFloat)radius
{
    self.circleCenter = location;
    self.circleRadius = radius;
    
    UIBezierPath *path = [UIBezierPath bezierPath];
    [path addArcWithCenter:self.circleCenter
                    radius:self.circleRadius
                startAngle:0.0
                  endAngle:M_PI * 2.0
                 clockwise:YES];
    
    return path;
}

- (void)addLocationIndicatorToMapAt:(CGPoint)location
{
    if ((location.x != location.y) && (location.x != 0)) {
        [self.circleLayer removeFromSuperlayer];
        
        CAShapeLayer *shapeLayer = [CAShapeLayer layer];
        shapeLayer.path = [[self makeCircleAtLocation:location radius:40] CGPath];
        shapeLayer.strokeColor = [[UIColor colorWithRed:0.0f green:120/255.f blue:1.0f alpha:0.7f] CGColor];
        shapeLayer.fillColor = [[UIColor colorWithRed:0.0f green:168/255.f blue:1.0f alpha:0.4f] CGColor];
        shapeLayer.lineWidth = 2.0f;
        
        // Add CAShapeLayer to the view
        [self.mapImageView.layer addSublayer:shapeLayer];
        self.circleLayer = shapeLayer;
    } else {
        [ARSAlertManager showErrorWithTitle:@"Position Unknown" message:@"We couldn't locate you on the map" cancelTitle:@"OK"];
    }
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
    [[ARSUserController sharedUserController] fetchFriendsLocationWithDelegate:self enforceRefresh:YES];
}

- (void)userLogInCompletedWithError:(ARSUserLoginError)error
{
    self.containerView = self.registerView;
    [ARSAlertManager showErrorWithTitle:@"Registration Error" message:@"There was an error during the registration" cancelTitle:@"OK"];
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

#pragma mark - Logout handling

- (void)logOut
{
    //Show alert first
    UIAlertView *confirmAlert = [[UIAlertView alloc] initWithTitle:@"Confirmation" message:@"Do you wish to log out of Facebook?" delegate:self cancelButtonTitle:@"CANCEL" otherButtonTitles:@"LOG OUT", nil];
    [confirmAlert show];
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    switch (buttonIndex) {
        case 1:
        {
                [ARSUserController logOutUser];
                [self configureViewForSelectedIndex:self.segmentedControl.selectedSegmentIndex];
        }
        default:
            break;
    }
}



@end
