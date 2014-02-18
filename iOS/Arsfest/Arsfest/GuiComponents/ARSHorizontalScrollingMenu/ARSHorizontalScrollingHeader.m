//
//  ARSHorizontalScrollingHeader.m
//  Arsfest
//
//  Created by Thibaud Robelain on 09/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSHorizontalScrollingHeader.h"

#define kButtonVisible      3
#define kButtonWidth        self.frame.size.width/kButtonVisible

@interface ARSHorizontalScrollingHeader()
@property(nonatomic, assign) NSInteger buttonsCount;
@end

@implementation ARSHorizontalScrollingHeader
@synthesize selectedIndex, buttonsCount, selectionDelegate = _selectionDelegate;

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
    for (NSString* title in titles) {
        [self addButtonWithTitle:title];
    }
}

- (void)addButtonWithTitle:(NSString *)title
{
    //Initializing button
    CGRect buttonFrame = CGRectMake(kButtonWidth * buttonsCount, 0, kButtonWidth, self.frame.size.height);
    UIButton *newButton = [[UIButton alloc] initWithFrame:buttonFrame];
    [newButton setTitle:title forState:UIControlStateNormal];
    [newButton setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    newButton.tag = buttonsCount;
    buttonsCount+=1;
    
    [newButton addTarget:self action:@selector(didSelectMenuItem:) forControlEvents:UIControlEventTouchUpInside];
    
    //Resizing scroll view
    [self addSubview:newButton];
    CGSize newContentSize = CGSizeMake(kButtonWidth*buttonsCount, self.frame.size.height);
    [self setContentSize:newContentSize];
}

#pragma mark - Selection handler

- (void)didSelectMenuItem:(UIButton*)button
{
    NSInteger tag = button.tag;
    CGPoint menuItemOrigin = button.frame.origin;
    NSLog(@"Selected index %li", (long)tag);
    [UIView animateWithDuration:0.3 animations:^{
        [self setContentOffset:menuItemOrigin];
    }];
    
    if ([_selectionDelegate respondsToSelector:@selector(scrollViewDidSelectMenuItemAtIndex:)]) {
        [_selectionDelegate scrollViewDidSelectMenuItemAtIndex:tag];
    }
    
#warning add small selected graphic to the selected button
}

#pragma mark - Scroll view delegate

- (void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    float maxX = (buttonsCount-kButtonVisible)*kButtonWidth;
    if (scrollView.contentOffset.x > maxX) {
        [scrollView setContentOffset:CGPointMake(maxX, 0)];
    }
}

#pragma mark - Scroll view config

- (BOOL)touchesShouldCancelInContentView:(UIView *)view
{
    return YES;
}

@end