//
//  ARSCarousel.m
//  Arsfest
//
//  Created by Thibaud Robelain on 13/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSCarousel.h"

@implementation ARSCarousel

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
    }
    return self;
}


- (void)awakeFromNib
{
    ARSCarouselThumbnail *thumbnail = [[[NSBundle mainBundle] loadNibNamed:@"ARSCarouselThumbnail" owner:self options:nil] lastObject];
    thumbnail.type = ARSCarouselThumbnailTypeTimer;
    [self addSubview:thumbnail];
    [self setContentSize:thumbnail.frame.size];
}

@end
