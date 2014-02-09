//
//  ARSHorizontalScrollingHeader.h
//  Arsfest
//
//  Created by Thibaud Robelain on 09/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol ARSHorizontalScrollingHeaderDelegate <NSObject>

- (void)scrollViewDidSelectMenuItemAtIndex:(NSUInteger)index;

@end


@interface ARSHorizontalScrollingHeader : UIScrollView <UIScrollViewDelegate>

@property (nonatomic, assign) NSUInteger selectedIndex;
@property (nonatomic, assign) id<ARSHorizontalScrollingHeaderDelegate> selectionDelegate;

- (void)addButtonsWithTitles:(NSArray*)titles;

@end
