//
//  ARSEventListViewController.m
//  Arsfest
//
//  Created by Thibaud Robelain on 08/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSEventListViewController.h"
#import "ARSEvent.h"

@interface ARSEventListViewController ()

@end

@implementation ARSEventListViewController
@synthesize data = _data, events = _events;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        _data = [[ARSData alloc] init];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    double delayInSeconds = 4.0;
    dispatch_time_t popTime = dispatch_time(DISPATCH_TIME_NOW, (int64_t)(delayInSeconds * NSEC_PER_SEC));
    dispatch_after(popTime, dispatch_get_main_queue(), ^(void){
        _events = [_data eventsIn:ARSLocationLibrary];
        [_eventListTableView reloadData];
    });
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

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *eventCell = [_eventListTableView dequeueReusableCellWithIdentifier:@"EventCell"];
    
    if (eventCell == nil) {
        eventCell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"EventCell"];
    }
    
    ARSEvent *event = (ARSEvent*)[_events objectAtIndex:indexPath.row];
    eventCell.textLabel.text = event.name;
    eventCell.textLabel.textColor = [UIColor blackColor];
    
    return eventCell;
}

@end
