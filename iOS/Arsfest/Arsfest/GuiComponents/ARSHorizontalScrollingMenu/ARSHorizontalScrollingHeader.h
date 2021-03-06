//
//  ARSHorizontalScrollingHeader.h
//  Arsfest
//
//  Created by Thibaud Robelain on 09/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef NS_ENUM(NSInteger, kSwipeDirection) {
    kSwipeDirectionLeft,
    kSwipeDirectionRight
};

@protocol ARSHorizontalScrollingHeaderDelegate <NSObject>

- (void)menuDidSelectMenuItemAtIndex:(NSUInteger)index;

@end


@interface ARSHorizontalScrollingHeader : UIScrollView <UIScrollViewDelegate>

@property (nonatomic, assign) id<ARSHorizontalScrollingHeaderDelegate> selectionDelegate;

- (void)addButtonsWithTitles:(NSArray*)titles;
- (void)handleMasterSwipeWithDirection:(kSwipeDirection)direction;

@end
