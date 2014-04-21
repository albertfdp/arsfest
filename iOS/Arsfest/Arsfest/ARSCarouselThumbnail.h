//
//  ARSCarouselThumbnail.h
//  Arsfest
//
//  Created by Thibaud Robelain on 13/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MZTimerLabel.h"

@class ARSEvent;

@protocol ARSCarouselThumbnailDelegate <NSObject>

- (void)thumbnailTimerFinished;
- (void)thumbnailMoreInfoTapped;

@end

typedef NS_ENUM(NSInteger, ARSCarouselThumbnailType) {
    ARSCarouselThumbnailTypeTimer,
    ARSCarouselThumbnailTypePartyOver,
    ARSCarouselThumbnailTypeEvent,
    ARSCarouselThumbnailTypeNextEvent
};

@interface ARSCarouselThumbnail : UIView

@property (weak, nonatomic) IBOutlet UIView *imageContainerView;
@property (weak, nonatomic) IBOutlet UIView *descriptionContainerView;
@property (weak, nonatomic) IBOutlet UIView *contentView;
@property (weak, nonatomic) IBOutlet UIImageView *imageView;
@property (weak, nonatomic) IBOutlet UIImageView *gradientView;
@property (weak, nonatomic) IBOutlet UILabel *labelSoon;
@property (weak, nonatomic) IBOutlet UILabel *headerLabel;
@property (weak, nonatomic) IBOutlet UILabel *subHeaderLabel;
@property (weak, nonatomic) IBOutlet MZTimerLabel *timerLabel;
@property (weak, nonatomic) IBOutlet UIButton *moreInfoButton;

@property (weak, nonatomic) ARSEvent *event;
@property (nonatomic, assign) ARSCarouselThumbnailType type;
@property (nonatomic, assign) id<ARSCarouselThumbnailDelegate> delegate;

- (IBAction)moreInfoButtonTapped:(id)sender;
@end
