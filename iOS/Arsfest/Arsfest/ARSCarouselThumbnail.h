//
//  ARSCarouselThumbnail.h
//  Arsfest
//
//  Created by Thibaud Robelain on 13/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MZTimerLabel.h"
#import "ARSEvent.h"

typedef NS_ENUM(NSInteger, ARSCarouselThumbnailType) {
    ARSCarouselThumbnailTypeTimer,
    ARSCarouselThumbnailTypeLaunched,
    ARSCarouselThumbnailTypeEvent,
    ARSCarouselThumbnailTypeNextEvent
};

@interface ARSCarouselThumbnail : UIView

@property (weak, nonatomic) IBOutlet UILabel *labelDescription;
@property (weak, nonatomic) IBOutlet UIView *contentView;
@property (weak, nonatomic) IBOutlet UIImageView *imageView;
@property (weak, nonatomic) IBOutlet UILabel *labelSoon;
@property (weak, nonatomic) IBOutlet MZTimerLabel *timerLabel;

@property (weak, nonatomic) ARSEvent *event;
@property (nonatomic, assign) ARSCarouselThumbnailType type;


@end
