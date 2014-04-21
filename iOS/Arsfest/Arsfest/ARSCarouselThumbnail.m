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


- (void)setType:(ARSCarouselThumbnailType)newType
{
    switch (newType) {
        case ARSCarouselThumbnailTypeTimer:
            [self.imageContainerView setHidden:YES];
            [self.headerLabel setHidden:YES];
            [self.timerLabel setDelegate:self];
            [self.timerLabel setCountDownToDate:ARSFEST_START_DATE];
            [self.subHeaderLabel setHidden:YES];
            [self.moreInfoButton.layer setCornerRadius:5.0f];
            [self.moreInfoButton.layer setBorderWidth:0.6f];
            [self.moreInfoButton.layer setBorderColor:[[UIColor whiteColor] CGColor]];
//            [self.moreInfoButton setBackgroundColor:kArsfestColor];
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
            [self.timerLabel setHidden:YES];
            [self.labelSoon setHidden:YES];
            [self.headerLabel setText:@"DTU Årsfest 2014 is over"];
            [self.subHeaderLabel setText:@"Thanks for participating, see you next year!"];
            break;
        case ARSCarouselThumbnailTypeEvent:
            [self.descriptionContainerView setHidden:YES];
            [self.labelSoon setText:[NSString stringWithFormat:@"Happening now: %@", self.event.name]];
            [self.imageView setImage:[UIImage imageNamed:[NSString stringWithFormat:@"%@.png", self.event.image]]];
            
            break;
        case ARSCarouselThumbnailTypeNextEvent:
            [self.descriptionContainerView setHidden:YES];
            [self.labelSoon setText:[NSString stringWithFormat:@"Coming next: %@", self.event.name]];
            [self.imageView setImage:[UIImage imageNamed:[NSString stringWithFormat:@"%@.png", self.event.image]]];
            break;
        default:
            break;
    }
    
    type = newType;
}

- (void)timerLabel:(MZTimerLabel *)timerLabel finshedCountDownTimerWithTime:(NSTimeInterval)countTime
{
    if ([self.delegate respondsToSelector:@selector(thumbnailTimerFinished)]) {
        [self.delegate thumbnailTimerFinished];
    }
}




- (IBAction)moreInfoButtonTapped:(id)sender {
}
@end
