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
#import "ARSCarousel.h"

@interface ARSEventListViewController : UIViewController <UITableViewDelegate, UITableViewDataSource, ARSHorizontalScrollingHeaderDelegate>

@property (weak, nonatomic) IBOutlet UITableView *eventListTableView;
@property (weak, nonatomic) IBOutlet UIImageView *backgroundView;
@property (weak, nonatomic) IBOutlet ARSCarousel *carouselScrollView;
@property (weak, nonatomic) IBOutlet UIPageControl *pageControl;
@property (weak, nonatomic) IBOutlet ARSHorizontalScrollingHeader *menuScrollView;

@property (weak, nonatomic) IBOutlet UIView *bottomView;
@end
