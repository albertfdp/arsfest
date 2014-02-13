//
//  ARSHorizontalScrollingHeader.m
//  Arsfest
//
//  Created by Thibaud Robelain on 09/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSHorizontalScrollingHeader.h"

#define kButtonVisible      3
#define kColumns            3
#define kButtonWidth        self.frame.size.width/kButtonVisible

typedef enum ScrollDirection {
    ScrollDirectionNone,
    ScrollDirectionRight,
    ScrollDirectionLeft,
    ScrollDirectionUp,
    ScrollDirectionDown,
    ScrollDirectionCrazy,
} ScrollDirection;

@interface ARSHorizontalScrollingHeader()
@property(nonatomic, assign) NSInteger buttonsOffset;
@property(nonatomic, assign) NSInteger buttonsCount;
@property (nonatomic, assign) NSInteger lastContentOffset;
@property (nonatomic, assign) ScrollDirection scrollDirection;
@end

@implementation ARSHorizontalScrollingHeader
@synthesize selectedIndex, buttonsCount, buttonsOffset, selectionDelegate = _selectionDelegate, lastContentOffset = _lastContentOffset, scrollDirection = _scrollDirection;

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.delegate = self;
    }
    return self;
}

- (id)initWithCoder:(NSCoder *)aDecoder {
    if (!(self = [super initWithCoder:aDecoder]))
        return nil;
    self.delegate = self;
    return self;
}

#pragma mark - Populating the rolling view

- (void)addButtonsWithTitles:(NSArray*)titles
{    
    buttonsCount = titles.count;
    for (int i = 0; i < kColumns; i++) {
        for (NSString* title in titles) {
            [self addButtonWithTitle:title];
        }
    }
    NSUInteger numberOfItems = [titles count];
    [self setContentOffset:CGPointMake(numberOfItems*kButtonWidth, 0)];
}

- (void)addButtonWithTitle:(NSString *)title
{
    //Initializing button
    CGRect buttonFrame = CGRectMake(kButtonWidth * buttonsOffset, 0, kButtonWidth, self.frame.size.height);
    UIButton *newButton = [[UIButton alloc] initWithFrame:buttonFrame];
    [newButton setTitle:title forState:UIControlStateNormal];
    [newButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    newButton.tag = buttonsOffset % buttonsCount;
    buttonsOffset += 1;
    
    [newButton addTarget:self action:@selector(didSelectMenuItem:) forControlEvents:UIControlEventTouchUpInside];
    
    //Resizing scroll view
    [self addSubview:newButton];
    CGSize newContentSize = CGSizeMake(kButtonWidth*buttonsOffset, self.frame.size.height);
    [self setContentSize:newContentSize];
}

#pragma mark -

- (void)didSelectMenuItem:(UIButton*)button
{
    [self didSelectIndex:selectedIndex];
}

- (void)didSelectIndex:(int)index
{
    if ( index < -kButtonVisible+1 || index > buttonsCount-1) {
        float offset;
        if (index<0) {
            offset = (kButtonVisible+1)*kButtonWidth;
        } else {
            offset = -kButtonWidth*buttonsCount;
        }

        NSLog(@"%@",NSStringFromCGPoint(self.contentOffset));
        CGPoint indexPoint = CGPointMake(self.contentOffset.x + offset, 0);
        [self setContentOffset:indexPoint];
        NSLog(@"%@",NSStringFromCGPoint(self.contentOffset));
    }
    index = index%4;
    selectedIndex = index;
    CGPoint indexPoint = CGPointMake(buttonsCount*kButtonWidth + index*kButtonWidth, 0);
    [UIView animateWithDuration:0.5 animations:^{
        NSLog(@"%@",NSStringFromCGPoint(self.contentOffset));
        [self setContentOffset:indexPoint];
                NSLog(@"%@",NSStringFromCGPoint(self.contentOffset));
    }];
    
    if ([_selectionDelegate respondsToSelector:@selector(scrollViewDidSelectMenuItemAtIndex:)]) {
        [_selectionDelegate scrollViewDidSelectMenuItemAtIndex:index];
    }
}

- (void)adjustScrollViewOffset
{
    CGFloat maxX = (kColumns-1)*buttonsCount*kButtonWidth;
    CGFloat minX = buttonsCount*kButtonWidth - kButtonVisible*kButtonWidth;

    if (self.contentOffset.x > maxX) {
        CGPoint newOffset = CGPointMake(buttonsCount*kButtonWidth, 0);
        self.contentOffset = newOffset;
    }
    
    if (self.contentOffset.x < minX) {
        CGPoint newOffset = CGPointMake((buttonsCount*2-kButtonVisible)*kButtonWidth, 0);
        self.contentOffset = newOffset;
    }
}

#pragma mark - Scroll view config

- (void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    if (_lastContentOffset < scrollView.contentOffset.x) {
        _scrollDirection = ScrollDirectionRight;
    }
    else if (self.lastContentOffset > scrollView.contentOffset.x) {
        _scrollDirection = ScrollDirectionLeft;
    }
    
    self.lastContentOffset = scrollView.contentOffset.x;
    
    [self adjustScrollViewOffset];
}

- (void)scrollViewDidEndDragging:(UIScrollView *)scrollView willDecelerate:(BOOL)decelerate
{
    if (_scrollDirection == ScrollDirectionRight) {
        int index = (selectedIndex+1);
            NSLog(@"%i", index);
        [self didSelectIndex:index];
    } else if (_scrollDirection == ScrollDirectionLeft) {
        int index = (selectedIndex-1);;
            NSLog(@"%i", index);
        [self didSelectIndex:index];
    }
}


- (BOOL)touchesShouldCancelInContentView:(UIView *)view
{
    return YES;
}

@end
