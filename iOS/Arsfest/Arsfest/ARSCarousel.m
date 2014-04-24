//
//  ARSCarousel.m
//  Arsfest
//
//  Created by Thibaud Robelain on 13/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSCarousel.h"
#import "ARSData.h"

@interface ARSCarousel() <UIScrollViewDelegate, ARSCarouselThumbnailDelegate>

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

- (void)addViews:(NSArray*)newViews
{
    for (ARSCarouselThumbnail* thumbnail in views) {
        [thumbnail removeFromSuperview];
    }
    
    //Add each view
    int i = 0;
    for (ARSCarouselThumbnail* thumbnail in newViews) {
        [self.scrollView addSubview:thumbnail];
        [thumbnail setFrame:CGRectMake(i*self.frame.size.width, 0, self.frame.size.width, self.frame.size.height)];
        i++;
    }
    
    [self.scrollView setContentSize:CGSizeMake(i * self.frame.size.width, self.frame.size.height)];
    [self.pageControl setNumberOfPages:[newViews count]];
    views = [NSMutableArray arrayWithArray:newViews];
}


- (void)configureScrollView
{
    [self.scrollView setDelegate:self];
    
    
    if ([NSDate currentDateIsBetween:ARSFEST_REAL_START_DATE and:ARSFEST_END_DATE]) {

        ARSCarouselThumbnail *currentEventThumbnail = [[[NSBundle mainBundle] loadNibNamed:@"ARSCarouselThumbnail" owner:self options:nil] lastObject];
        currentEventThumbnail.event = [[ARSData data] currentEventInTheParty];
        currentEventThumbnail.type = ARSCarouselThumbnailTypeEvent;
        [currentEventThumbnail addGestureRecognizer:[self aTapRecognizer]];
        
        ARSCarouselThumbnail *nextEventThumbnail = [[[NSBundle mainBundle] loadNibNamed:@"ARSCarouselThumbnail" owner:self options:nil] lastObject];
        nextEventThumbnail.event = [[ARSData data] nextEventInTheParty];
        nextEventThumbnail.type = ARSCarouselThumbnailTypeNextEvent;
        
        [nextEventThumbnail addGestureRecognizer:[self aTapRecognizer]];
        if (currentEventThumbnail.event && nextEventThumbnail.event) {
            [self addViews:@[currentEventThumbnail, nextEventThumbnail]];
        } else if (currentEventThumbnail.event) {
            [self addViews:@[currentEventThumbnail]];
        } else {
            [self addViews:@[nextEventThumbnail]];
        }
 
    } else if ([ARSFEST_END_DATE isEarlierThanDate:[NSDate date] fromMinutes:0]) {
        ARSCarouselThumbnail *thumbnail = [[[NSBundle mainBundle] loadNibNamed:@"ARSCarouselThumbnail" owner:self options:nil] lastObject];
        thumbnail.type = ARSCarouselThumbnailTypePartyOver;
        [self addViews:@[thumbnail]];
    } else {
        ARSCarouselThumbnail *thumbnail = [[[NSBundle mainBundle] loadNibNamed:@"ARSCarouselThumbnail" owner:self options:nil] lastObject];
        thumbnail.type = ARSCarouselThumbnailTypeTimer;
        thumbnail.delegate = self;
        
        [self addViews:@[thumbnail]];
    }
    
}

- (UITapGestureRecognizer*)aTapRecognizer
{
    UITapGestureRecognizer *tapRecognizer = [[UITapGestureRecognizer alloc] init];
    [tapRecognizer addTarget:self action:@selector(tappedView:)];
    [tapRecognizer setNumberOfTapsRequired:1];
    return tapRecognizer;
}

#pragma mark - Thumbnail delegate

- (void)thumbnailTimerFinished
{
    [self configureScrollView];
}

- (void)thumbnailMoreInfoTapped
{
    if ([self.delegate respondsToSelector:@selector(carouselTappedForInformation)]) {
        [self.delegate carouselTappedForInformation];
    }
}

#pragma mark - Scroll view delegate

- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView
{
    int x = self.scrollView.contentOffset.x;
    int currentPage = (x / (int)self.scrollView.frame.size.width);
    [self.pageControl setCurrentPage:currentPage];
}

#pragma mark - Tap handling

- (void)tappedView:(id)sender
{
    UITapGestureRecognizer *recognizer = (UITapGestureRecognizer*)sender;
    ARSCarouselThumbnail *thumbnailTapped = (ARSCarouselThumbnail*)recognizer.view;
    
    if (thumbnailTapped.type == ARSCarouselThumbnailTypeEvent || thumbnailTapped.type == ARSCarouselThumbnailTypeNextEvent) {
        if ([self.delegate respondsToSelector:@selector(carouselTappedForEvent:)]) {
            [self.delegate carouselTappedForEvent:thumbnailTapped.event];
        }
    }
}

@end
