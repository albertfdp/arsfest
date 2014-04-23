//
//  ARSEventListViewController.m
//  Arsfest
//
//  Created by Thibaud Robelain on 08/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSEventListViewController.h"
#import "ARSEventViewController.h"
#import "ARSInfoViewController.h"
#import "ARSEventCell.h"
#import "ARSEvent.h"
#import "ARSUserController.h"
#import "ARSAnalytics.h"

#define kEventCellHeight 78

@interface ARSEventListViewController() <ARSUserControllerDelegate, ARSCarouselDelegate>

@property (nonatomic, assign) ARSLocationType currentFilter;
@property (nonatomic, strong) NSArray *events;
@property (nonatomic, strong) NSMutableArray *menuCategories;

@end

@implementation ARSEventListViewController
@synthesize events = _events, currentFilter = _currentFilter, pageControl = _pageControl, menuScrollView = _menuScrollView;
@synthesize menuCategories = _menuCategories;

#pragma mark -
#pragma mark - Views

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        _menuCategories = [[NSMutableArray alloc] init];
        _events = [[NSArray alloc] init];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    //KVO on the Data for the UITableView
    [self addObserver:self
           forKeyPath:@"events"
              options:0
              context:NULL];
    
    //Initialize gesture on the table view
    UISwipeGestureRecognizer *rightSwipeRecognizer = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(handleSwipe:)];
    rightSwipeRecognizer.direction = UISwipeGestureRecognizerDirectionRight;
    
    UISwipeGestureRecognizer *leftSwipeRecognizer = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(handleSwipe:)];
    leftSwipeRecognizer.direction = UISwipeGestureRecognizerDirectionLeft;

    [self.eventListTableView addGestureRecognizer:rightSwipeRecognizer];
    [self.eventListTableView addGestureRecognizer:leftSwipeRecognizer];
    
    //Initialize the menu in the first position
    [self initializeScrollingMenu];
    [self menuDidSelectMenuItemAtIndex:0];
    
    [self.backgroundView setImage:kBackgroundImage];
    
    //Initialize the carousel's delegate
    [self.carouselView setDelegate:self];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self setupNavigationBar];
    [self.carouselView configureScrollView];
    
    [ARSAnalytics trackViewOpened:@"Event List"];
}

- (void)dealloc
{
    [self removeObserver:self forKeyPath:@"events"];
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark -
#pragma mark - Table view

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [_events count];
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return kEventCellHeight;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    ARSEventCell *eventCell = [_eventListTableView dequeueReusableCellWithIdentifier:@"EventCell"];
    
    if (eventCell == nil) {
        NSArray *topLevelObjects =[[NSBundle mainBundle] loadNibNamed:@"ARSEventCell" owner:self options:nil];
        eventCell = [topLevelObjects lastObject];
    }
    
    ARSEvent *event = (ARSEvent*)[_events objectAtIndex:indexPath.row];
    
    [eventCell configureCellWithEvent:event];
    
    return eventCell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    ARSEvent *event = [_events objectAtIndex:indexPath.row];
    
    ARSEventViewController *eventViewController = [[ARSEventViewController alloc] initWithNibName:@"ARSEventViewController" bundle:[NSBundle mainBundle]];
    [eventViewController setEvent:event];
    
    [self.navigationController pushViewController:eventViewController animated:YES];
    [_eventListTableView deselectRowAtIndexPath:indexPath animated:YES];
}

- (void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath
{
    [cell setBackgroundColor:[UIColor clearColor]];
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section
{
    UIView *view = [[UIView alloc] init];
    
    return view;
}

#pragma mark -
#pragma mark - ARSData related methods

/**
 * Use this method to assign a new array to the table view data source
 */
- (void)sortAndStore:(NSArray*)array
{
    NSSortDescriptor *dateDescriptor = [NSSortDescriptor
                                        sortDescriptorWithKey:@"startTime"
                                        ascending:YES];
    NSArray *sortDescriptors = [NSArray arrayWithObject:dateDescriptor];
    NSArray *sortedEventArray = [array
                                 sortedArrayUsingDescriptors:sortDescriptors];
    
    [self setEvents:sortedEventArray];
}

#pragma mark -
#pragma mark - KVO

- (void)observeValueForKeyPath:(NSString *)keyPath
                      ofObject:(id)object
                        change:(NSDictionary *)change
                       context:(void *)context {
    
    if ([keyPath isEqual:@"events"]) {
        dispatch_async(dispatch_get_main_queue(), ^{
            [_eventListTableView reloadData];
        });
    }
}

#pragma mark -
#pragma mark - View setup

- (void)setupNavigationBar
{
    [self.navigationController.navigationBar.topItem setTitle:@"DTU Årsfest 2014"];
    
    // Info button
    UIImage *leftImage = [UIImage imageNamed:@"question.png"];
    UIBarButtonItem *leftItem = [UIBarButtonItem itemWithImage:leftImage target:self action:@selector(showInfo)];
    [self.navigationItem setLeftBarButtonItem:leftItem];
}

- (void)showInfo
{
    ARSInfoViewController *infoViewController = [[ARSInfoViewController alloc] initWithNibName:@"ARSInfoViewController" bundle:[NSBundle mainBundle]];
    UINavigationController *navigationController = [[UINavigationController alloc] initWithRootViewController:infoViewController];
    [navigationController.navigationBar setBarTintColor:kArsfestColor];
    
    [self presentViewController:navigationController animated:YES completion:nil];
}

- (void)initializeScrollingMenu
{
    //Setting up scrolling menu
    [_menuScrollView addButtonsWithTitles:[NSArray arrayWithObjects:@"ALL",@"CANTEEN",@"THE TENT",@"OTICON HALL",@"LIBRARY",@"SPORTS HALL", nil]];
    [_menuScrollView setSelectionDelegate:self];
}


#pragma mark -
#pragma mark - Carousel Initialization



#pragma mark -
#pragma mark - Horizontal menu delegate

- (void)menuDidSelectMenuItemAtIndex:(NSUInteger)index
{
    _currentFilter = kNumberOfLocations - (int)index;
    NSArray *newData = [[ARSData data] eventsIn:_currentFilter];
    [self sortAndStore:newData];
}

#pragma mark -
#pragma mark - User controller delegate

- (void)userLogInCompletedWithSuccess
{
    NSLog(@"Success");
}

- (void)userLogInCompletedWithError:(ARSUserLoginError)error
{
    NSLog(@"Error");    
}

#pragma mark -
#pragma mark - Swipe handler table view

- (IBAction)handleSwipe:(UISwipeGestureRecognizer*)sender
{
    if (sender.direction == UISwipeGestureRecognizerDirectionRight) {
        [self.menuScrollView handleMasterSwipeWithDirection:kSwipeDirectionRight];
    } else {
        [self.menuScrollView handleMasterSwipeWithDirection:kSwipeDirectionLeft];
    }
}

#pragma mark - ARSCarouselDelegate

- (void)carouselTappedForInformation
{
    [self showInfo];
}

- (void)carouselTappedForEvent:(ARSEvent *)event
{
    ARSEventViewController *eventViewController = [[ARSEventViewController alloc] initWithNibName:@"ARSEventViewController" bundle:[NSBundle mainBundle]];
    [eventViewController setEvent:event];
    
    [self.navigationController pushViewController:eventViewController animated:YES];
}

@end
