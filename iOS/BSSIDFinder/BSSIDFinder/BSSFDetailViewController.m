//
//  BSSFDetailViewController.m
//  BSSIDFinder
//
//  Created by Jose Luis Bellod Cisneros on 21/04/14.
//  Copyright (c) 2014 Jose Luis Bellod Cisneros. All rights reserved.
//

#import "BSSFDetailViewController.h"

@interface BSSFDetailViewController ()
- (void)configureView;
+ (NSMutableArray*)data;
@end

@implementation BSSFDetailViewController

#pragma mark - Managing the detail item

- (id)init
{
    self = [super init];
    
    if (self) {
    }
    
    return self;
}

+ (NSMutableArray*)data
{
    static NSMutableArray* data = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        data = [[NSMutableArray alloc] init];
    });
    return data;
}

- (void)setLocation:(LocationObject *)newLocation
{
    if (_location != newLocation) {
        _location = newLocation;
        
        // Update the view.
        [self configureView];
    }
}

- (void)configureView
{
    // Update the user interface for the detail item.

    if (self.location) {
        self.bssid.text = self.location.bssid;
        self.latitude.text = [NSString stringWithFormat:@"%f",self.location.latitude];
        self.longitude.text = [NSString stringWithFormat:@"%f",self.location.longitude];
    }
}

-(IBAction) appendNewLocation:(id)sender
{
    NSLog(@"executing");
    BSSFJSON *location = [[BSSFJSON alloc] init];
    location.location = self.locationName.text;
    location.longitude = [NSString stringWithFormat:@"%f",self.location.latitude];
    location.latitude = [NSString stringWithFormat:@"%f",self.location.latitude];
    location.bssid = self.bssid.text;
    NSMutableArray *locations = [BSSFDetailViewController data];
    [locations addObject:location];
    
}

-(IBAction) saveJSON:(id)sender
{
    NSMutableArray *locations = [BSSFDetailViewController data];
    NSLog(@"%d", (int)locations.count);
    
    
    NSString *filePath = [[NSBundle mainBundle] pathForResource:@"new_wifis" ofType:@"json"];
    NSMutableArray *answer = [[NSMutableArray alloc] init];
    for (BSSFJSON*location in locations) {
        NSDictionary *dic = [[NSDictionary alloc] initWithObjectsAndKeys:
                             @"bssid",location.bssid,
                             @"latitude",location.latitude,
                             @"longitude",location.longitude,
                             @"x","",
                             @"y","",
                             @"locationName",location.location,nil];
        

        [answer addObject: dic];
    }
    
//
//    NSData *data = [NSKeyedArchiver archivedDataWithRootObject:locations];
//    
//    
    NSOutputStream *os = [[NSOutputStream alloc] initToFileAtPath:filePath append:NO];
//
    [os open];
    [NSJSONSerialization writeJSONObject:answer toStream:os options:0 error:nil];
    [os close];
//
}

- (NSManagedObjectContext *)managedObjectContext {
    NSManagedObjectContext *context = nil;
    id delegate = [[UIApplication sharedApplication] delegate];
    if ([delegate performSelector:@selector(managedObjectContext)]) {
        context = [delegate managedObjectContext];
    }
    return context;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    [self configureView];
    
    [self.locationName setDelegate:self];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    
    return YES;
}

@end
