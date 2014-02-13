//
//  ARSEventListViewController.m
//  Arsfest
//
//  Created by Thibaud Robelain on 08/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSEventListViewController.h"
#import "ARSEventCell.h"
#import "ARSEvent.h"

#define kEventCellHeight 78

@interface ARSEventListViewController ()

@property (nonatomic, assign) ARSLocationType currentFilter;
@property (nonatomic, strong) ARSData *data;
@property (nonatomic, strong) NSArray *events;

@end

@implementation ARSEventListViewController
@synthesize data = _data, events = _events, currentFilter = _currentFilter;

#pragma mark -
#pragma mark - Views

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        _data = [[ARSData alloc] init];
        _data.dataDelegate = self;
        _currentFilter = ARSLocationAll;
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

@end