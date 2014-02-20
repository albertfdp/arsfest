//
//  ARSEventListViewController.h
//  Arsfest
//
//  Created by Thibaud Robelain on 08/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ARSHorizontalScrollingHeader.h"
#import "ARSData.h"

@interface ARSEventListViewController : UIViewController <UITableViewDelegate, UITableViewDataSource, ARSDataDelegate, ARSHorizontalScrollingHeaderDelegate>

@property (weak, nonatomic) IBOutlet UITableView *eventListTableView;
@property (weak, nonatomic) IBOutlet UIScrollView *carouselScrollView;
@property (weak, nonatomic) IBOutlet UIPageControl *pageControl;
@property (weak, nonatomic) IBOutlet ARSHorizontalScrollingHeader *menuScrollView;

@end
