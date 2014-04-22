//
//  ARSInfoViewController.m
//  Arsfest
//
//  Created by Jose Luis Bellod Cisneros on 18/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSInfoViewController.h"
#import "ARSAnalytics.h"
#import "ARSInfoView.h"

@interface ARSInfoViewController()

/* Configures the view when the selected index of the segmented control changes */
- (void)configureViewForSelectedIndex:(NSInteger)index;

@end

@implementation ARSInfoViewController

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

    [self.segmentedControl addObserver:self forKeyPath:@"selectedSegmentIndex" options:NSKeyValueObservingOptionNew context:nil];
    
    //Segmented control initialization
    [self.segmentedControl setSelectedSegmentIndex:0];
    [self.segmentedControl setTintColor:kArsfestColor];
    
    //Navigation bar buttons
    UIImage *rightImage = [UIImage imageNamed:@"close.png"];
    UIBarButtonItem *rightItem = [UIBarButtonItem itemWithImage:rightImage target:self action:@selector(dismissInfo)];
    [self.navigationController.navigationBar.topItem setRightBarButtonItem:rightItem];
    
    //Init scroll view
    [self initInformationsScrollView];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self configureViewForSelectedIndex:self.segmentedControl.selectedSegmentIndex];
    
    [ARSAnalytics trackViewOpened:@"Informations"];
}

- (void)configureViewForSelectedIndex:(NSInteger)index
{
    switch (index) {
        case 0:
        {
            [self setViewToSettings:NO];
            [self setTitle:@"Information"];
            
            break;
        }
        case 1:
        {
            [self setViewToSettings:YES];
            [self setTitle:@"Settings"];
            break;
        }
    }
}

- (void)setViewToSettings:(BOOL)yes
{
    [self.infoView setHidden:yes];
    [self.settingsView setHidden:!yes];
}

- (void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary *)change context:(void *)context
{
    if ([keyPath isEqualToString:@"selectedSegmentIndex"]) {
        UISegmentedControl *segmControl = (UISegmentedControl*)object;
        [self configureViewForSelectedIndex:segmControl.selectedSegmentIndex];
    }
}

#pragma mark -
#pragma mark - Dismiss view controller

- (void)dismissInfo
{
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void) dealloc
{
    [self.segmentedControl removeObserver:self forKeyPath:@"selectedSegmentIndex"];
}

#pragma mark - Informations scroll view

- (void)initInformationsScrollView
{
    NSArray *nibContents = [[NSBundle mainBundle] loadNibNamed:@"ARSInfoView" owner:nil options:nil];
    ARSInfoView *infoView = [nibContents lastObject];
    
    
}

@end
