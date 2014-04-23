//
//  ARSEventViewController.m
//  Arsfest
//
//  Created by Thibaud Robelain on 13/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSEventViewController.h"
#import "ARSAnalytics.h"

@interface ARSEventViewController ()

@property (nonatomic, strong) NSDate *analyticsStartDate;

@end

@implementation ARSEventViewController
@synthesize event = _event;
@synthesize labelEventTitle = _labelEventTitle;
@synthesize labelEventLocation = _labelEventLocation;
@synthesize descriptionScrollView = _descriptionScrollView;
@synthesize descriptionTextView = _descriptionTextView;
@synthesize labelStartTime = _labelStartTime;
@synthesize labelEndTime = _labelEndTime;
@synthesize eventImageView = _eventImageView;
@synthesize titleView = _titleView;
@synthesize whenView = _whenView;
@synthesize whereView = _whereView;
@synthesize labelEventTheme = _labelEventTheme;
@synthesize analyticsStartDate;

#pragma mark -
#pragma mark - View

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        
    }
    return self;
}

- (UIRectEdge)edgesForExtendedLayout
{
    return UIRectEdgeAll;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    _descriptionTextView.delegate = self;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self configureView];
    [self setupNavigationBar];
    [self setupScrollView];
    
    [ARSAnalytics trackViewOpened:[NSString stringWithFormat:@"Event Viewed: %@", _event.name]];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    
    analyticsStartDate = [NSDate date];
}

- (void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:animated];
    NSTimeInterval interval = [[NSDate date] timeIntervalSinceDate:analyticsStartDate];
    [ARSAnalytics trackSpentTime:interval onScreen:_event.name];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

- (void)configureView
{
    [_labelEventTitle setText:[_event.name uppercaseString]];
    [_labelEventTheme setText:[_event.theme uppercaseString]];
    [_labelEventLocation setText:[_event.location.name uppercaseString]];
    [_labelStartTime setText:[NSDate hourMinuteStringFromDate:_event.startTime]];
    [_labelEndTime setText:[NSDate hourMinuteStringFromDate:_event.endTime]];
    [_eventImageView setImage:[UIImage imageNamed:[NSString stringWithFormat:@"%@.png", _event.image]]];
    [_backgroundView setImage:kBackgroundImage];
    
    [self configureBorders];
}

- (void)configureBorders
{
    [_whereView addBorderTop:YES bottom:YES right:YES left:NO outside:NO];
    [_whenView addBorderTop:YES bottom:YES right:NO left:NO outside:NO];
    [_eventImageView addBorderTop:NO bottom:YES right:NO left:NO outside:YES];
    [self.informationsView addBorderTop:YES bottom:YES right:NO left:NO outside:NO];
}

#pragma mark -
#pragma mark - Navigation Bar setup

- (void)setupNavigationBar
{
    [self setTitle:@"EVENT"];
}

#pragma mark -
#pragma mark - Scroll view setup

- (void)setupScrollView
{
    [_descriptionTextView setText:_event.description];
    [_descriptionScrollView setContentSize:_descriptionTextView.frame.size];
}

@end
