//
//  UIView+Borders.m
//  Arsfest
//
//  Created by Thibaud Robelain on 21/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "UIView+Borders.h"

@implementation UIView (Borders)

- (void)addBorderTop:(BOOL)top bottom:(BOOL)bottom right:(BOOL)right left:(BOOL)left outside:(BOOL)outside
{
    if (top) {
        CALayer *topBorder = [CALayer layer];
        float delta = (outside)?-0.3f:0.3f;
        if ([self isKindOfClass:[UIScrollView class]]) {
            UIScrollView *selfScroll = (UIScrollView*)self;
            topBorder.frame = CGRectMake(0.0f, 0.0f, selfScroll.contentSize.width, delta);
        } else {
            topBorder.frame = CGRectMake(0.0f, 0.0f, self.frame.size.width, delta);
        }
        topBorder.backgroundColor = [UIColor colorWithWhite:0.0f
                                                      alpha:1.0f].CGColor;
        
        [self.layer addSublayer:topBorder];
    }
    if (bottom) {
        CALayer *bottomBorder = [CALayer layer];
        float delta = (outside)?0.3f:-0.3f;
        
        if ([self isKindOfClass:[UIScrollView class]]) {
            UIScrollView *selfScroll = (UIScrollView*)self;
            bottomBorder.frame = CGRectMake(0.0f, selfScroll.contentSize.height, selfScroll.contentSize.width, delta);
        } else {
            bottomBorder.frame = CGRectMake(0.0f, self.frame.size.height, self.frame.size.width, delta);
        }
        bottomBorder.backgroundColor = [UIColor colorWithWhite:0.0f
                                                         alpha:1.0f].CGColor;
        
        [self.layer addSublayer:bottomBorder];
        
    }
    if (left) {
        CALayer *leftBorder = [CALayer layer];
        float delta = (outside)?-0.3f:0.3f;
        leftBorder.frame = CGRectMake(0.0f, 0.0f, delta, self.frame.size.height);
        leftBorder.backgroundColor = [UIColor colorWithWhite:0.0f
                                                       alpha:1.0f].CGColor;
        
        [self.layer addSublayer:leftBorder];
    }
    if (right) {
        CALayer *rightBorder = [CALayer layer];
        
        float delta = (outside)?0.3f:-0.3f;
        rightBorder.frame = CGRectMake(self.frame.size.width, 0.0f, delta, self.frame.size.height);
        rightBorder.backgroundColor = [UIColor colorWithWhite:0.0f
                                                        alpha:1.0f].CGColor;
        
        [self.layer addSublayer:rightBorder];
    }
}

@end
