//
//  ARSEventListViewController.m
//  Arsfest
//
//  Created by Thibaud Robelain on 08/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSEventListViewController.h"

@interface ARSEventListViewController ()

@end

@implementation ARSEventListViewController
@synthesize horizontalHeader;

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
    // Do any additional setup after loading the view from its nib.
    [horizontalHeader setScrollEnabled:YES];
    [horizontalHeader setCanCancelContentTouches:YES];
    [horizontalHeader setBackgroundColor:[UIColor grayColor]];
    [horizontalHeader addButtonsWithTitles:[NSArray arrayWithObjects:@"ALL", @"LIBRARY", @"LOST", @"HELP", nil]];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
