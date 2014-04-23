//
//  ARSInfoView.m
//  Arsfest
//
//  Created by Thibaud Robelain on 22/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSInfoView.h"

@implementation ARSInfoView

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)setTitle:(NSString*)title description:(NSString*)description image:(UIImage*)image
{
    [self.titleLabel setText:title];
    [self.descriptionTextView setText:description];
    [self.descriptionTextView setFont:[UIFont fontWithName:@"HelveticaNeue-Light" size:14]];
    [self.descriptionTextView setTextAlignment:NSTextAlignmentLeft];
    [self.imageView setImage:image];
    
    if ([UIScreen mainScreen].bounds.size.height > 480.f) {
        [self.descriptionTextView setFrame:CGRectMake(self.descriptionTextView.frame.origin.x, self.descriptionTextView.frame.origin.y, self.descriptionTextView.frame.size.width, self.descriptionTextView.frame.size.height+88.f)];
    }
}


@end
