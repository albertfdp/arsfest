//
//  ARSFriendListCell.h
//  Arsfest
//
//  Created by Thibaud Robelain on 28/03/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Parse/Parse.h"
#import "AsyncImageView.h"

@interface ARSFriendListCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *nameLabel;
@property (weak, nonatomic) IBOutlet AsyncImageView *picture;
@property (weak, nonatomic) IBOutlet UILabel *locationLabel;
@property (weak, nonatomic) IBOutlet UILabel *timeLabel;

- (void)configureCellWithUser:(PFUser*)user;

@end
