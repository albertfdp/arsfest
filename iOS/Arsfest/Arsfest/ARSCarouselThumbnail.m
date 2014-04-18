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
        self.userInteractionEnabled = YES;
    }
    return self;
}


- (void)setType:(ARSCarouselThumbnailType)newType
{
    switch (newType) {
        case ARSCarouselThumbnailTypeTimer:
            [self.imageView setHidden:YES];
            [self.timerLabel setCountDownToDate:ARSFEST_START_DATE];
            [self.timerLabel setTimerType:MZTimerLabelTypeTimer];
            [self.timerLabel setTimeFormat:@"dd 'days' HH 'hours'"];
            [self.timerLabel start];
            break;
        case ARSCarouselThumbnailTypeLaunched:
            //
            NSLog(@"..");
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

-(void)touchesBegan:(NSSet*)touches withEvent:(UIEvent*)event
{
    UITouch *touch = [touches anyObject];
    
    if(touch.view.tag == 99){
        //self.labelDescription.text = @"it worked!";
        // Update view
        NSString *description = @"Commemoration Party on the 9th of May 2014\nThe Commemoration Party is the festive peak of the academic year, where achievements, perspectives and\ncollaborations are celebrated with the students, employees and partners of the university.\nDTU also highlights select achievements of the past year by the awarding academic honours.\nThe Commemoration Party can be divided into three. The official part, followed by dinner and then a large\nball. DTU and Polytechnical Association hope you have a fantastic evening.";
        
        [self.labelDescription setHidden:YES];
        [self.labelSoon setHidden:YES];
        [self.description setText:description];
        [self.description setHidden:NO];
        [self.timerLabel setHidden:YES];
    }
}





@end
