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
@synthesize eventImageView = _eventImageView;
@synthesize titleView = _titleView;
@synthesize whenView = _whenView;
@synthesize whereView = _whereView;

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
    [_labelEventTitle setText:[_event.name uppercaseString]];
    [_labelEventLocation setText:[_event.location.name uppercaseString]];
    [_labelStartTime setText:[NSDate hourMinuteStringFromDate:_event.startTime]];
    [_labelEndTime setText:[NSDate hourMinuteStringFromDate:_event.endTime]];
    [_eventImageView setImage:[UIImage imageNamed:[NSString stringWithFormat:@"%@.png", _event.image]]];
    [_backgroundView setImage:[UIImage imageNamed:@"default_background.png"]];
    
    [self configureBorders];
}

- (void)configureBorders
{
    [self addBordersToView:_whereView];
    [self addBordersToView:_whenView];
    
}

- (void)addBordersToView:(UIView*)view
{
//    [view.layer setBorderColor:[[UIColor whiteColor] CGColor]];
//    [view.layer setBorderWidth:0.2f];

    CALayer *bottomBorder = [CALayer layer];
    
    bottomBorder.frame = CGRectMake(0.0f, 43.0f, view.frame.size.width, 0.2f);
    bottomBorder.backgroundColor = [UIColor colorWithWhite:0.8f
                                                     alpha:1.0f].CGColor;

    [view.layer addSublayer:bottomBorder];
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
    newFrame.size = CGSizeMake(fmaxf(newSize.width, fixedWidth), newSize.height + 5);
    textView.frame = newFrame;
}
@end
