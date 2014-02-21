//
//  ARSEventListViewController.m
//  Arsfest
//
//  Created by Thibaud Robelain on 08/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSEventListViewController.h"
#import "ARSEventViewController.h"
#import "ARSEventCell.h"
#import "ARSEvent.h"

#define kEventCellHeight 78

@interface ARSEventListViewController ()

@property (nonatomic, assign) ARSLocationType currentFilter;
@property (nonatomic, strong) ARSData *data;
@property (nonatomic, strong) NSArray *events;
@property (nonatomic, strong) NSMutableArray *menuCategories;

@end

@implementation ARSEventListViewController
@synthesize data = _data, events = _events, currentFilter = _currentFilter, carouselScrollView = _carouselScrollView, pageControl = _pageControl, menuScrollView = _menuScrollView;
@synthesize menuCategories = _menuCategories;

#pragma mark -
#pragma mark - Views

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        _data = [[ARSData alloc] init];
        _data.dataDelegate = self;
        _currentFilter = ARSLocationAll;
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
    
    [self.backgroundView setImage:kBackgroundImage];
    
    [self initializeScrollingMenu];
}

- (void)viewWillAppear:(BOOL)animated
{
    [self setupNavigationBar];
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
#pragma mark - ARSDataDelegate

- (void)didReceiveDataFromTheServer
{
    NSArray *receivedData = [_data eventsIn:_currentFilter];
    [self sortAndStore:receivedData];
    //TODO: Handle UI Logic
    
}

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
    [self.navigationController.navigationBar.topItem setTitle:@"DTU Årsfest"];
    
    NSMutableDictionary *titleAttributes = [[NSMutableDictionary alloc] init];
    [titleAttributes setObject:[UIColor whiteColor] forKey:UITextAttributeTextColor];
    
    [[UINavigationBar appearance] setTitleTextAttributes:titleAttributes];
//    [[UINavigationBar appearance] setBarTintColor:[UIColor colorWithRed:0.20 green:0.07 blue:0.3 alpha:0.8]];
    
    self.navigationItem.backBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"" style:UIBarButtonItemStylePlain target:nil action:nil];
}

- (void)initializeScrollingMenu
{
    //Setting up scrolling menu
    [_menuScrollView addButtonsWithTitles:[NSArray arrayWithObjects:@"ALL",@"KANTINE",@"OTICON SALEN",@"LIBRARY",@"SPORTS HALL", nil]];
    [_menuScrollView setSelectionDelegate:self];
    [_menuScrollView addBorderTop:YES bottom:YES right:NO left:NO outside:NO];
}

#pragma mark -
#pragma mark - Carousel Initialization

- (void)setupCarousel
{
    
}

- (void)addImageToScrollView
{
    
}

#pragma mark -
#pragma mark - Horizontal menu delegate

- (void)menuDidSelectMenuItemAtIndex:(NSUInteger)index
{
    _currentFilter = kNumberOfLocations - index;
    NSArray *newData = [_data eventsIn:_currentFilter];
    [self sortAndStore:newData];
}

@end
