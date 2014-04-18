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

//    // Set up timer
//    [[NSTimer scheduledTimerWithTimeInterval:5
//                                      target:self
//                                    selector:@selector(scrollToPage)
//                                    userInfo:Nil
//                                     repeats:YES] fire];
    
}

//-(void)scrollToPage:(NSInteger)aPage{
//    ARSCarouselThumbnail *thumbnail = [[[NSBundle mainBundle] loadNibNamed:@"ARSCarouselThumbnail" owner:self options:nil] lastObject];
//    
//    thumbnail.labelDescription.text = @"cucu";
//}

- (void)timerFinished
{
    
}

@end
