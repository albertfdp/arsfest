//
//  ARSCarousel.m
//  Arsfest
//
//  Created by Thibaud Robelain on 13/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSCarousel.h"
#import "ARSData.h"

@interface ARSCarousel()

@property (nonatomic, strong) NSMutableArray *views;

@end

@implementation ARSCarousel
@synthesize views;


- (id)initWithCoder:(NSCoder *)aDecoder
{
    if (self = [super initWithCoder:aDecoder]) {
    
    }
    
    return self;
}

- (void)awakeFromNib
{

}

- (void)addViews:(NSArray*)newViews
{
    //Animate remove views from carousel
    
    //Add each view
    
    views = [NSMutableArray arrayWithArray:newViews];
}


- (void)configureScrollView
{
    ARSCarouselThumbnail *thumbnail = [[[NSBundle mainBundle] loadNibNamed:@"ARSCarouselThumbnail" owner:self options:nil] lastObject];

    thumbnail.event = [[ARSData data] currentEventInTheParty];
    thumbnail.type = ARSCarouselThumbnailTypeEvent;

    [self.scrollView addSubview:thumbnail];
    [self.scrollView setContentSize:thumbnail.frame.size];
    
//    [self.pageControl setHidden:YES];
    
    
    //Get time.
    //If before the party, set timer (be sure to implement timerfinished)
    //If after start, get current event and next event, create thumbnails
    //If after end, get PartyOver thumbnail
    
    //Add views
}

- (void)timerFinished
{
    
}

@end
