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

@interface ARSEventListViewController : UIViewController <UITableViewDelegate, UITableViewDataSource, ARSDataDelegate>

@property (weak, nonatomic) IBOutlet UITableView *eventListTableView;

@end