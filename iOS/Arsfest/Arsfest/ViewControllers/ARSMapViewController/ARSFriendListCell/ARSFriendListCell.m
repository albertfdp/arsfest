//
//  ARSFriendListCell.m
//  Arsfest
//
//  Created by Thibaud Robelain on 28/03/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSFriendListCell.h"


@implementation ARSFriendListCell

- (void)awakeFromNib
{
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

#pragma mark -
#pragma mark - Configuration

- (void)configureCellWithUser:(PFUser*)user
{
    //Informations
    self.nameLabel.text = [user objectForKey:@"name"];
    self.picture.imageURL = [NSURL URLWithString:[user objectForKey:@"pictureURL"]];
    NSString *locationName = [user objectForKey:@"locationName"];
    if (locationName && ![locationName isEqualToString:@""]) {
        self.locationLabel.text = [user objectForKey:@"locationName"];
    } else {
        self.locationLabel.text = @"Unknown Location";
    }
    
    
    //Picture rounding
    [self.picture setClipsToBounds:YES];
    [self.picture.layer setCornerRadius:25.0f];
    [self.picture.layer setBorderWidth:0.8f];
    [self.picture.layer setBorderColor:kFriendCellColor];
}

- (NSString*)stringFromUpdateDate:(NSDate*)date
{
    return @"";
}

@end
