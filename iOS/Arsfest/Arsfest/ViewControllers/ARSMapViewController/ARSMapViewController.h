//
//  ARSMapViewController.h
//  Arsfest
//
//  Created by Thibaud Robelain on 26/03/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ARSUserController.h"

@interface ARSMapViewController : UIViewController <ARSUserControllerDelegate, UITableViewDataSource, UITableViewDelegate>

/* Main view outlets */
@property (weak, nonatomic) IBOutlet UIView *containerView;
@property (weak, nonatomic) IBOutlet UISegmentedControl *segmentedControl;

- (IBAction)didChangeSegmentedControl:(id)sender;

/* Register with facebook outlets */

@property (strong, nonatomic) IBOutlet UIView *registerView;

@property (weak, nonatomic) IBOutlet UIButton *loginButton;

- (IBAction)loginUser:(id)sender;

/* Friends list outlets */

@property (strong, nonatomic) IBOutlet UIView *friendListView;

    /* Data view */
@property (weak, nonatomic) IBOutlet UIView *friendListDataView;

@property (weak, nonatomic) IBOutlet UITableView *friendsListTableView;

@property (weak, nonatomic) IBOutlet UITextView *noFriendsRegisteredLabel;

    /* Loading view */
@property (weak, nonatomic) IBOutlet UIView *friendListLoadingView;

/* Map view outlets */

@property (strong, nonatomic) IBOutlet UIView *mapView;

@property (weak, nonatomic) IBOutlet UIScrollView *mapScrollView;


@end
