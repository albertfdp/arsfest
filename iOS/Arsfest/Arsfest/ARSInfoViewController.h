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

@property (weak, nonatomic) IBOutlet UIView *infoView;
@property (weak, nonatomic) IBOutlet UIView *settingsView;

@end
