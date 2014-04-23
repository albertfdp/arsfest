//
//  ARSCarouselThumbnail.m
//  Arsfest
//
//  Created by Thibaud Robelain on 13/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSCarouselThumbnail.h"
#import "ARSUserController.h"
#import "ARSEvent.h"

@interface ARSCarouselThumbnail() <MZTimerLabelDelegate>

@end

@implementation ARSCarouselThumbnail
@synthesize type;

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
    }
    return self;
}

- (void)dealloc
{
    if (self.type == ARSCarouselThumbnailTypeTimer) {
        [self.moreInfoButton removeObserver:self forKeyPath:@"highlighted"];
        [self.moreInfoButton removeObserver:self forKeyPath:@"selected"];
    }
}

#pragma mark - Thumbnail configuration

- (void)setType:(ARSCarouselThumbnailType)newType
{
    switch (newType) {
        case ARSCarouselThumbnailTypeTimer:
//            [self.separatorView setHidden:NO];
            [self.imageContainerView setHidden:YES];
            [self.headerLabel setHidden:YES];
            [self.timerLabel setDelegate:self];
            [self.timerLabel setCountDownToDate:ARSFEST_REAL_START_DATE];
            [self.subHeaderLabel setHidden:YES];
            [self configureButtonBorders];
            [self.moreInfoButton addObserver:self forKeyPath:@"highlighted" options:0 context:nil];
            [self.moreInfoButton addObserver:self forKeyPath:@"selected" options:0 context:nil];
            [self.moreInfoButton setClipsToBounds:YES];
            [self.timerLabel setTimerType:MZTimerLabelTypeTimer];
            if ([NSDate daysLeftBeforeTheParty] < 1) {
                [self.timerLabel setTimeFormat:@"HH : mm : ss"];
            } else {
                [self.timerLabel setTimeFormat:@"dd 'Days Left'"];
            }
            [self.timerLabel start];
            
            
            break;
        case ARSCarouselThumbnailTypePartyOver:
            [self.imageContainerView setHidden:YES];
            [self.moreInfoButton setHidden:YES];
            [self.timerLabel setHidden:YES];
            [self.labelSoon setHidden:YES];
            [self.headerLabel setText:@"DTU Årsfest 2014 is over"];
            [self.subHeaderLabel setText:@"Thanks for participating, see you next year!"];
            break;
        case ARSCarouselThumbnailTypeEvent:
            [self setImageForEvent:self.event];
            [self.labelSoon setText:[NSString stringWithFormat:@"Happening now: %@", self.event.name]];
            break;
        case ARSCarouselThumbnailTypeNextEvent:
            [self setImageForEvent:self.event];
            [self.labelSoon setText:[NSString stringWithFormat:@"Coming next: %@", self.event.name]];
            break;
        default:
            break;
    }
    
    type = newType;
}

- (void)configureButtonBorders
{
    if (self.moreInfoButton.highlighted) {
        [self.moreInfoButton.layer setCornerRadius:5.0f];
        [self.moreInfoButton.layer setBorderWidth:0.7f];
        [self.moreInfoButton.layer setBorderColor:[[UIColor lightGrayColor] CGColor]];
        
    } else {
        [self.moreInfoButton.layer setCornerRadius:5.0f];
        [self.moreInfoButton.layer setBorderWidth:0.7f];
        [self.moreInfoButton.layer setBorderColor:[[UIColor whiteColor] CGColor]];
    }
}

- (void)setImageForEvent:(ARSEvent*)event
{
    [self.descriptionContainerView setHidden:YES];
    [self.imageView setImage:[UIImage imageNamed:[NSString stringWithFormat:@"%@.png", event.image]]];
}

#pragma mark - Timer delegate

- (void)timerLabel:(MZTimerLabel *)timerLabel finshedCountDownTimerWithTime:(NSTimeInterval)countTime
{
    if ([self.delegate respondsToSelector:@selector(thumbnailTimerFinished)]) {
        [self.delegate thumbnailTimerFinished];
    }
}

#pragma mark - Info button handler

- (IBAction)moreInfoButtonTapped:(id)sender {
    if ([self.delegate respondsToSelector:@selector(thumbnailMoreInfoTapped)]) {
        [self.delegate thumbnailMoreInfoTapped];
    }
}

#pragma mark - KVO

- (void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary *)change context:(void *)context
{
    if ([keyPath isEqualToString:@"highlighted"] || [keyPath isEqualToString:@"selected"]) {
        [self configureButtonBorders];
    }
}
@end
