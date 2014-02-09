//
//  ARSHorizontalScrollingHeader.m
//  Arsfest
//
//  Created by Thibaud Robelain on 09/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "ARSHorizontalScrollingHeader.h"

#define kButtonWidth        self.frame.size.width/3

@interface ARSHorizontalScrollingHeader()
@property(nonatomic, assign) NSInteger buttonsCount;
@end

@implementation ARSHorizontalScrollingHeader
@synthesize selectedIndex, buttonsCount, delegate;

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {

    }
    return self;
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

- (void)didSelectMenuItem:(UIButton*)button
{
    NSInteger tag = button.tag;
    CGPoint menuItemOrigin = button.frame.origin;
    NSLog(@"Selected index %d", tag);
//    if (menuItemOrigin.x > )
    [self setContentOffset:menuItemOrigin];
    
    if ([delegate respondsToSelector:@selector(scrollViewDidSelectMenuItemAtIndex:)]) {
        [delegate scrollViewDidSelectMenuItemAtIndex:tag];
    }
    
#warning add small selected graphic to the selected button
}

- (BOOL)touchesShouldCancelInContentView:(UIView *)view
{
    return YES;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/

@end
