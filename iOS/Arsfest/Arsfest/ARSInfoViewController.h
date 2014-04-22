//
//  ARSInfoViewController.h
//  Arsfest
//
//  Created by Jose Luis Bellod Cisneros on 18/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ARSInfoViewController : UIViewController <UIScrollViewDelegate>

@property (weak, nonatomic) IBOutlet UISegmentedControl *segmentedControl;

/* Info view outlets*/
@property (weak, nonatomic) IBOutlet UIView *infoView;
@property (weak, nonatomic) IBOutlet UIPageControl *pageControl;
@property (weak, nonatomic) IBOutlet UIScrollView *informationsScrollView;



/* Settings view outlets*/
@property (weak, nonatomic) IBOutlet UIView *settingsView;

@property (weak, nonatomic) IBOutlet UISwitch *allowStatisticsSwitch;

- (IBAction)statisticsSwitchTouched:(id)sender;
@end
