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


@interface ARSHorizontalScrollingHeader()
@property(nonatomic, assign) NSInteger buttonsOffset;
@property(nonatomic, assign) NSInteger buttonsCount;
@end

@implementation ARSHorizontalScrollingHeader
@synthesize selectedIndex, buttonsCount, buttonsOffset, selectionDelegate = _selectionDelegate;

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
    [newButton setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
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
    NSInteger tag = button.tag;
    CGPoint menuItemOrigin = button.frame.origin;
    NSLog(@"Selected index %d", tag);
//    if (menuItemOrigin.x > )
    [self setContentOffset:menuItemOrigin];
    
    if ([_selectionDelegate respondsToSelector:@selector(scrollViewDidSelectMenuItemAtIndex:)]) {
        [_selectionDelegate scrollViewDidSelectMenuItemAtIndex:tag];
    }
    
#warning add small selected graphic to the selected button
}

- (void)adjustScrollViewOffset
{
    CGFloat maxX = (kColumns-1)*buttonsCount*kButtonWidth;
    CGFloat minX = buttonsCount*kButtonWidth - kButtonVisible*kButtonWidth;
    CGPoint newOffset = CGPointMake(buttonsCount*kButtonWidth, 0);
    if (self.contentOffset.x > maxX) {
        self.contentOffset = newOffset;
    }
    
    if (self.contentOffset.x < minX) {
        self.contentOffset = newOffset;
    }
}

#pragma mark - Scroll view config

- (void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    [self adjustScrollViewOffset];
}

- (BOOL)touchesShouldCancelInContentView:(UIView *)view
{
    return YES;
}

@end
