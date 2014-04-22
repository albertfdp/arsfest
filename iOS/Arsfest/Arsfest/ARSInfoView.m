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
    [self.imageView setImage:image];
}


@end
