//
//  ARSCarousel.m
//  Arsfest
//
//  Created by Thibaud Robelain on 13/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSCarousel.h"

//@interface ARSCarousel()
//- (void)scrollToPage:(NSInteger)aPage;
//@end

@implementation ARSCarousel


- (id)initWithCoder:(NSCoder *)aDecoder
{
    if (self = [super initWithCoder:aDecoder]) {
    
    }
    
    return self;
}

- (void)awakeFromNib
{
}

//-(void)scrollToPage:(NSInteger)aPage{
//    ARSCarouselThumbnail *thumbnail = [[[NSBundle mainBundle] loadNibNamed:@"ARSCarouselThumbnail" owner:self options:nil] lastObject];
//    
//    thumbnail.labelDescription.text = @"cucu";
//}

- (void)configureScrollView
{
    ARSCarouselThumbnail *thumbnail = [[[NSBundle mainBundle] loadNibNamed:@"ARSCarouselThumbnail" owner:self options:nil] lastObject];
    thumbnail.type = ARSCarouselThumbnailTypePartyOver;
    [self.scrollView addSubview:thumbnail];
    [self.scrollView setContentSize:thumbnail.frame.size];
    
    [self.pageControl setHidden:YES];
}

- (void)timerFinished
{
    
}

@end
