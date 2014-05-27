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

@interface ARSInfoViewController() <UIScrollViewDelegate>

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
    
    self.informationsScrollView.delegate = self;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    //Init scroll view
    [self configureStatisticsSwitch];
    
    [ARSAnalytics trackViewOpened:@"Informations"];
 
    [self initInformationsScrollView];
    [self configureViewForSelectedIndex:self.segmentedControl.selectedSegmentIndex];
    [self.infoView setNeedsDisplay];
    [self.informationsScrollView setNeedsDisplay];
    [self.pageControl setNeedsDisplay];
}

- (void)viewDidLayoutSubviews
{
    [super viewDidLayoutSubviews];
}

- (void)configureViewForSelectedIndex:(NSInteger)index
{
    switch (index) {
        case 0:
        {
            [self setViewToSettings:NO];
            [self setTitle:@"Informations"];
            
            break;
        }
        case 1:
        {
            [self setViewToSettings:YES];
            [self setTitle:@"Settings"];
            [ARSAnalytics trackViewOpenedOnlyIfWifiAvailable:@"Settings"];            
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
    ARSInfoView *infoView = [[[NSBundle mainBundle] loadNibNamed:@"ARSInfoView" owner:nil options:nil] lastObject];
    [infoView setTitle:@"The Annual Party" description:INFO_ARSFEST_WELCOME image:[UIImage imageNamed:@"dtu-icon.png"]];
    
    ARSInfoView *secondInfoView = [[[NSBundle mainBundle] loadNibNamed:@"ARSInfoView" owner:nil options:nil] lastObject];
    [secondInfoView setTitle:@"Social Integration" description:INFO_FACEBOOK image:[UIImage imageNamed:@"facebook-icon.png"]];

    ARSInfoView *thirdInfoView = [[[NSBundle mainBundle] loadNibNamed:@"ARSInfoView" owner:nil options:nil] lastObject];
    [thirdInfoView setTitle:@"Wi-Fi Geo-Location" description:INFO_WIFI image:[UIImage imageNamed:@"wifi-icon.png"]];
    
    [self addViewsToScrollView:@[infoView, secondInfoView, thirdInfoView]];
}

- (void)addViewsToScrollView:(NSArray*)views
{
    //Add each view
    int i = 0;
    for (ARSInfoView* informationView in views) {
        [self.informationsScrollView addSubview:informationView];
        int yOrigin = self.informationsScrollView.frame.origin.y;
        [informationView setFrame:CGRectMake(i*self.informationsScrollView.bounds.size.width, yOrigin, self.informationsScrollView.bounds.size.width, self.informationsScrollView.bounds.size.height)];
        i++;
    }
    
    [self.informationsScrollView setContentSize:CGSizeMake(i * self.informationsScrollView.bounds.size.width, self.informationsScrollView.bounds.size.height)];
    [self.pageControl setNumberOfPages:[views count]];
    
}

#pragma mark - Scroll view delegate

- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView
{
    int x = self.informationsScrollView.contentOffset.x;
    int currentPage = (x / (int)self.informationsScrollView.frame.size.width);
    [self.pageControl setCurrentPage:currentPage];
    
    switch (currentPage) {
        case 0:
        {
            [ARSAnalytics trackViewOpenedOnlyIfWifiAvailable:@"Arsfest Information"];
        }
            break;
        case 1:
        {
            [ARSAnalytics trackViewOpenedOnlyIfWifiAvailable:@"Facebook Information"];
        }
        case 2:
        {
            [ARSAnalytics trackViewOpenedOnlyIfWifiAvailable:@"Wi-Fi Information"];
        }
        default:
            break;
    }
}

#pragma mark - Statistics button and view

- (IBAction)statisticsSwitchTouched:(id)sender {
    UISwitch *switchTouched = (UISwitch*)sender;
    [self storeStatisticsChange:switchTouched.on];
    [self configureStatisticsSwitch];
}

- (void)storeStatisticsChange:(BOOL)change
{
    [[NSUserDefaults standardUserDefaults] setBool:change forKey:@"allowstatistics"];
    [[NSUserDefaults standardUserDefaults] synchronize];
    
    [ARSAnalytics trackEventWithCategory:@"Analytics" action:(change)?@"Enabled":@"Disabled"];
}

- (void)configureStatisticsSwitch
{
    [self.allowStatisticsSwitch setOn:[[NSUserDefaults standardUserDefaults] boolForKey:@"allowstatistics"]];
}


@end
