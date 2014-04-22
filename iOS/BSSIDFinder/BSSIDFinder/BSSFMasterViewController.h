//
//  BSSFMasterViewController.h
//  BSSIDFinder
//
//  Created by Jose Luis Bellod Cisneros on 21/04/14.
//  Copyright (c) 2014 Jose Luis Bellod Cisneros. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreLocation/CoreLocation.h>

@interface BSSFMasterViewController : UITableViewController <CLLocationManagerDelegate> {
    CLLocationManager *locationManager;
}
@end

@interface LocationObject : NSObject
    @property (strong, nonatomic) NSString *bssid;
    @property  float   latitude;
    @property  float   longitude;
@end
