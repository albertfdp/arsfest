//
//  ARSEventCell.m
//  Arsfest
//
//  Created by Thibaud Robelain on 13/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSEventCell.h"

@implementation ARSEventCell
@synthesize labelTitle = _labelTitle, labelLocation = _labelLocation, labelTime = _labelTime, imageView = _imageView, progressView = _progressView;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

#pragma mark -
#pragma mark - Custom configuration

- (void)configureCellWithEvent:(ARSEvent *)event
{
    _labelTitle.text = event.name;
    _labelLocation.text = event.location.name;
    _labelTime.text = [NSDate hourMinuteStringFromDate:event.startTime];
    
    UIImage *icon = [UIImage iconForTheme:event.theme];
    if (icon) {
        _imageView.image = icon;
    }
}

@end
