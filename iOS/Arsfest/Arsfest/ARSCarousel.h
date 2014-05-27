//
//  ARSCarousel.h
//  Arsfest
//
//  Created by Thibaud Robelain on 13/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ARSCarouselThumbnail.h"

@class ARSEvent;
@protocol ARSCarouselDelegate <NSObject>

- (void)carouselTappedForInformation;
- (void)carouselTappedForEvent:(ARSEvent*)event;

@end

@interface ARSCarousel : UIView

@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;
@property (weak, nonatomic) IBOutlet UIPageControl *pageControl;

@property (nonatomic, assign) id<ARSCarouselDelegate> delegate;

- (void)configureScrollView;
@end
