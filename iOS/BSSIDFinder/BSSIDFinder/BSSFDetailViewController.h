//
//  BSSFDetailViewController.h
//  BSSIDFinder
//
//  Created by Jose Luis Bellod Cisneros on 21/04/14.
//  Copyright (c) 2014 Jose Luis Bellod Cisneros. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BSSFMasterViewController.h"

@interface BSSFDetailViewController : UIViewController

@property (strong, nonatomic) LocationObject *location;

@property (weak, nonatomic) IBOutlet UILabel *bssid;
@property (weak, nonatomic) IBOutlet UILabel *latitude;
@property (weak, nonatomic) IBOutlet UILabel *longitude;
@end
