//
//  ARSEventViewController.m
//  Arsfest
//
//  Created by Thibaud Robelain on 13/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSEventViewController.h"

@interface ARSEventViewController ()

@end

@implementation ARSEventViewController
@synthesize event = _event;
@synthesize labelEventTitle = _labelEventTitle;
@synthesize labelEventLocation = _labelEventLocation;
@synthesize descriptionScrollView = _descriptionScrollView;
@synthesize descriptionTextView = _descriptionTextView;
@synthesize labelStartTime = _labelStartTime;
@synthesize labelEndTime = _labelEndTime;

#pragma mark -
#pragma mark - View

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {

    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    _descriptionTextView.delegate = self;
}

- (void)viewWillAppear:(BOOL)animated
{
    [self configureView];
    [self setupNavigationBar];
    [self setupScrollView];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

- (void)configureView
{
    [_labelEventTitle setText:_event.name];
    [_labelEventLocation setText:_event.location.name];
    [_labelStartTime setText:[NSDate hourMinuteStringFromDate:_event.startTime]];
    [_labelEndTime setText:[NSDate hourMinuteStringFromDate:_event.endTime]];
}

#pragma mark -
#pragma mark - Navigation Bar setup

- (void)setupNavigationBar
{
    [self setTitle:_event.name];
}

#pragma mark -
#pragma mark - Scroll view setup

- (void)setupScrollView
{
    [_descriptionTextView setText:_event.description];
    [self textViewDidChange:_descriptionTextView];
    [_descriptionScrollView setContentSize:_descriptionTextView.frame.size];
}

#pragma mark -
#pragma mark - Text view delegate

- (void)textViewDidChange:(UITextView *)textView
{
    CGFloat fixedWidth = textView.frame.size.width;
    CGSize newSize = [textView sizeThatFits:CGSizeMake(fixedWidth, MAXFLOAT)];
    CGRect newFrame = textView.frame;
    newFrame.size = CGSizeMake(fmaxf(newSize.width, fixedWidth), newSize.height);
    textView.frame = newFrame;
}
@end
