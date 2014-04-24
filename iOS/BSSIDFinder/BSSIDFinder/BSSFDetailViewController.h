//
//  BSSFDetailViewController.h
//  BSSIDFinder
//
//  Created by Jose Luis Bellod Cisneros on 21/04/14.
//  Copyright (c) 2014 Jose Luis Bellod Cisneros. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreData/CoreData.h>
#import "BSSFMasterViewController.h"
#import "BSSFJSON.h"

@interface BSSFDetailViewController : UIViewController <UITextFieldDelegate>

@property (strong, nonatomic) LocationObject *location;

@property (weak, nonatomic) IBOutlet UILabel *bssid;
@property (weak, nonatomic) IBOutlet UILabel *latitude;
@property (weak, nonatomic) IBOutlet UILabel *longitude;
@property (weak, nonatomic) IBOutlet UITextField *locationName;
@property (weak, nonatomic) IBOutlet UITextView *json;

-(IBAction) appendNewLocation:(id)sender;

-(IBAction) saveJSON:(id)sender;

@end
