//
//  ARSInfoViewController.m
//  Arsfest
//
//  Created by Jose Luis Bellod Cisneros on 18/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSInfoViewController.h"

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
    [self setTitle:@"Info"];

}

- (void)viewWillAppear:(BOOL)animated
{
    
    [self configureViewForSelectedIndex:self.segmentedControl.selectedSegmentIndex];
}

- (void)configureViewForSelectedIndex:(NSInteger)index
{
    switch (index) {
        case 0:
        {
            [self.info setHidden:NO];
            [self.ticketSale setHidden:YES];
            self.header.text= @"Commemoration Party: 9th of May 2014";
            
            break;
        }
        case 1:
        {
            [self.info setHidden:YES];
            [self.ticketSale setHidden:NO];
            self.header.text= @"Ticket Sale";
            break;
        }
    }
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

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/

@end
