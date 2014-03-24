//
//  ARSHorizontalScrollingHeader.m
//  Arsfest
//
//  Created by Thibaud Robelain on 09/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSHorizontalScrollingHeader.h"

#define kButtonVisible              3
#define kButtonWidth                self.frame.size.width/kButtonVisible
#define kFeedbackHeight             -self.frame.size.height/10
#define kFeedbackSelectedColor      [UIColor colorWithRed:119/255.f green:6/255.f blue:148/255.f alpha:1]
#define kFeedbackDeselectedColor    [UIColor colorWithRed:153/255.f green:48/255.f blue:179/255.f alpha:1]

static BOOL kSwipeEnabled = NO;

@interface ARSHorizontalScrollingHeader() <UIGestureRecognizerDelegate>
@property (nonatomic, assign) NSInteger selectedIndex;
@property (nonatomic, assign) NSInteger buttonsCount;
@property (nonatomic, strong) NSMutableArray *buttonsFeedback;
@end

@implementation ARSHorizontalScrollingHeader
@synthesize selectedIndex = _selectedIndex, buttonsCount, selectionDelegate = _selectionDelegate;
@synthesize buttonsFeedback = _buttonsFeedback;

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
    
    _buttonsFeedback = [[NSMutableArray alloc] init];
    _selectedIndex = 0;
    
    if (kSwipeEnabled) {
        [self setScrollEnabled:NO];
        
        UISwipeGestureRecognizer *swipeRecognizer = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(handleSwipe:)];
        swipeRecognizer.direction = (UISwipeGestureRecognizerDirectionLeft);
        [self addGestureRecognizer:swipeRecognizer];
        
        UISwipeGestureRecognizer *rightRecognizer = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(handleSwipe:)];
        rightRecognizer.direction = UISwipeGestureRecognizerDirectionRight;
        [self addGestureRecognizer:rightRecognizer];
    }
    
    return self;
}

#pragma mark - Populating the rolling view

- (void)addButtonsWithTitles:(NSArray*)titles
{
    int i = 0;
    for (NSString* title in titles) {
        [self addButtonWithTitle:title];
        [self addFeedbackToButtonAtIndex:i];
        i++;
    }
}

- (void)addButtonWithTitle:(NSString *)title
{
    //Initializing button
    CGRect buttonFrame = CGRectMake(kButtonWidth * buttonsCount, 0, kButtonWidth, self.frame.size.height);
    UIButton *newButton = [[UIButton alloc] initWithFrame:buttonFrame];
    [newButton setTitle:title forState:UIControlStateNormal];
    [newButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [newButton.titleLabel setFont:[UIFont fontWithName:@"HelveticaNeue-Bold" size:12]];
    newButton.tag = buttonsCount;
    buttonsCount+=1;
    
    [newButton addTarget:self action:@selector(didSelectMenuItem:) forControlEvents:UIControlEventTouchUpInside];
    
    //Resizing scroll view
    [self addSubview:newButton];
    CGSize newContentSize = CGSizeMake(kButtonWidth*buttonsCount, self.frame.size.height);
    [self setContentSize:newContentSize];
}

- (void)addFeedbackToButtonAtIndex:(NSInteger)index
{
    float originX = kButtonWidth*index;
    float originY = self.frame.size.height;
    CGRect feedbackFrame = CGRectMake(originX, originY, kButtonWidth, kFeedbackHeight);
    
    UIView *feedbackView = [[UIView alloc] initWithFrame:feedbackFrame];
    UIColor *feedbackColor = (index == 0)?kFeedbackSelectedColor:kFeedbackDeselectedColor;
    [feedbackView setBackgroundColor:feedbackColor];
    
    [_buttonsFeedback addObject:feedbackView];
    [self addSubview:feedbackView];
}

#pragma mark - Selection handler

- (void)didSelectMenuItem:(UIButton*)button
{
    NSInteger tag = button.tag;
    [self didSelectButtonAtIndex:tag];
}

- (void)didSelectButtonAtIndex:(NSInteger)index
{
    __block int originX = kButtonWidth*index;
    [UIView animateWithDuration:0.2 animations:^{
        originX = MIN(originX, (buttonsCount-kButtonVisible)*kButtonWidth);
        CGPoint menuItemOrigin = CGPointMake(originX, 0);
        [self setContentOffset:menuItemOrigin];
    }];
    
    [self changeFeedbackForSelectedIndex:index deselectedIndex:_selectedIndex];
    _selectedIndex = index;
    
    if ([_selectionDelegate respondsToSelector:@selector(menuDidSelectMenuItemAtIndex:)]) {
        [_selectionDelegate menuDidSelectMenuItemAtIndex:_selectedIndex];
    }
}

- (void)changeFeedbackForSelectedIndex:(NSInteger)selectedIndex deselectedIndex:(NSInteger)deselectedIndex
{
    UIView *deselectedFeedback = [_buttonsFeedback objectAtIndex:deselectedIndex];
    UIView *selectedFeedback = [_buttonsFeedback objectAtIndex:selectedIndex];
    
    [UIView animateWithDuration:0.2 animations:^{
        [deselectedFeedback setBackgroundColor:kFeedbackDeselectedColor];
        [selectedFeedback setBackgroundColor:kFeedbackSelectedColor];
    }];
}

#pragma mark - Scroll view delegate

- (void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    float maxX = (buttonsCount-kButtonVisible)*kButtonWidth;
    float minX = 0;
    if (scrollView.contentOffset.x > maxX) {
        [scrollView setContentOffset:CGPointMake(maxX, 0)];
    }
    if (scrollView.contentOffset.x < minX) {
        [scrollView setContentOffset:CGPointMake(minX, 0)];
    }
}

#pragma mark - Scroll view config

- (BOOL)touchesShouldCancelInContentView:(UIView *)view
{
    return YES;
}

#pragma mark - Custom swiping
- (IBAction)handleSwipe:(UISwipeGestureRecognizer*)sender
{
    NSInteger selectedIndex;
    if (sender.direction == UISwipeGestureRecognizerDirectionRight) {
        selectedIndex = MAX(0, _selectedIndex - 1);
    } else {
        selectedIndex = MIN(buttonsCount-1, _selectedIndex + 1);
    }
    [self didSelectButtonAtIndex:selectedIndex];
}

@end