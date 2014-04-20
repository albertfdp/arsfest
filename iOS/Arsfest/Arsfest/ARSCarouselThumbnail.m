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
            [self.subHeaderLabel setHidden:YES];
            [self.headerLabel setHidden:YES];
            [self.timerLabel setCountDownToDate:ARSFEST_START_DATE];
            [self.labelSoon setText:@"Get more information about the party"];
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
            // Get actual time
            //NSDate *now = [[NSDate alloc] init];
            // Get user location
            //NSString *location = [[ARSUserController sharedUserController] userLocation];
            // Get closer next event
            
            
            break;
        case ARSCarouselThumbnailTypeNextEvent:
            // Get actual time
            //NSDate *now = [[NSDate alloc] init];
            // Get user location
            //NSLog([[ARSUserController sharedUserController] userLocation]);
            // Get closer next event
            break;
        default:
            break;
    }
    
    type = newType;
}





@end
