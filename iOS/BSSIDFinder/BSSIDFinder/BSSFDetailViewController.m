//
//  BSSFDetailViewController.m
//  BSSIDFinder
//
//  Created by Jose Luis Bellod Cisneros on 21/04/14.
//  Copyright (c) 2014 Jose Luis Bellod Cisneros. All rights reserved.
//

#import "BSSFDetailViewController.h"

@interface BSSFDetailViewController ()
- (void)configureView;
@end

@implementation BSSFDetailViewController

#pragma mark - Managing the detail item

- (void)setLocation:(LocationObject *)newLocation
{
    if (_location != newLocation) {
        _location = newLocation;
        
        // Update the view.
        [self configureView];
    }
}

- (void)configureView
{
    // Update the user interface for the detail item.

    if (self.location) {
        self.bssid.text = self.location.bssid;
        self.latitude.text = [NSString stringWithFormat:@"%f",self.location.latitude];
        self.longitude.text = [NSString stringWithFormat:@"%f",self.location.longitude];
    }
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    [self configureView];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
