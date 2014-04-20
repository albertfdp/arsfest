//
//  ARSCarousel.h
//  Arsfest
//
//  Created by Thibaud Robelain on 13/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ARSCarouselThumbnail.h"
//#import "ARSCarouselPartyInfo.h"

@interface ARSCarousel : UIView

@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;
@property (weak, nonatomic) IBOutlet UIPageControl *pageControl;

- (void)configureScrollView;
@end
