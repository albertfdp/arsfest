//
//  BSSFMasterViewController.m
//  BSSIDFinder
//
//  Created by Jose Luis Bellod Cisneros on 21/04/14.
//  Copyright (c) 2014 Jose Luis Bellod Cisneros. All rights reserved.
//

#import "BSSFMasterViewController.h"

#import "BSSFDetailViewController.h"

#import "ARSUserController.h"





@implementation LocationObject
    @synthesize bssid;
    @synthesize latitude;
    @synthesize longitude;
    - (id)init
    {
        self = [super init];
        if (self) {
            self.bssid = nil;
        }
        return self;
    }
@end

@interface BSSFMasterViewController () {
    
    NSMutableArray *_objects;
}
- (void) targetMethod;
@end

@implementation BSSFMasterViewController

- (void)awakeFromNib
{
    [super awakeFromNib];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    self.navigationItem.leftBarButtonItem = self.editButtonItem;

    UIBarButtonItem *addButton = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemAdd target:self action:@selector(insertNewObject:)];
    self.navigationItem.rightBarButtonItem = addButton;
    
    // Create de timer
    
    [NSTimer scheduledTimerWithTimeInterval:2.0
                                     target:self
                                   selector:@selector(targetMethod)
                                   userInfo:nil
                                    repeats:YES];
    ARSUserController* sharedController = [ARSUserController sharedUserController];
    sharedController.lastBSSID = nil;
}

- (void) targetMethod
{
    ARSUserController* sharedController = [ARSUserController sharedUserController];

    
    NSString *bssid = [sharedController currentWifiBSSID];


    
    if (!_objects) {
        _objects = [[NSMutableArray alloc] init];
    }
    
    if (![sharedController.lastBSSID isEqualToString:bssid]){
        // update
        sharedController.lastBSSID = bssid;
        LocationObject *location = [[LocationObject alloc] init];
        location.bssid = bssid;
        locationManager = [[CLLocationManager alloc] init];
        
        [locationManager startUpdatingLocation];
        location.latitude =locationManager.location.coordinate.latitude;
        location.longitude = locationManager.location.coordinate.longitude;
        [locationManager stopUpdatingLocation];
        
        [_objects insertObject:location atIndex:0];
        NSIndexPath *indexPath = [NSIndexPath indexPathForRow:0 inSection:0];
        [self.tableView insertRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationAutomatic];
    }

}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)insertNewObject:(id)sender
{
    if (!_objects) {
        _objects = [[NSMutableArray alloc] init];
    }
    [_objects insertObject:[NSDate date] atIndex:0];
    NSIndexPath *indexPath = [NSIndexPath indexPathForRow:0 inSection:0];
    [self.tableView insertRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationAutomatic];
}

#pragma mark - Table View

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return _objects.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"Cell" forIndexPath:indexPath];

    LocationObject *object = _objects[indexPath.row];
    cell.textLabel.text = object.bssid;
    return cell;
}

- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}

- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        [_objects removeObjectAtIndex:indexPath.row];
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view.
    }
}

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([[segue identifier] isEqualToString:@"showDetail"]) {
        NSIndexPath *indexPath = [self.tableView indexPathForSelectedRow];
        LocationObject *object = _objects[indexPath.row];
        [[segue destinationViewController] setLocation:object];
    }
}

@end
