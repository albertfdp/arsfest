//
//  UIBarButtonItem+RightBarItem.m
//  Arsfest
//
//  Created by Thibaud Robelain on 26/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "UIBarButtonItem+RightBarItem.h"

@implementation UIBarButtonItem (RightBarItem)

+(UIBarButtonItem*)itemWithImage:(UIImage*)image target:(id)target action:(SEL)selector
{
    UIButton *button = [[UIButton alloc] init];
    button.frame = CGRectMake(0,0,22,22);
    [button setBackgroundImage:image forState:UIControlStateNormal];
    [button addTarget:target action:selector forControlEvents:UIControlEventTouchUpInside];
    
    UIBarButtonItem *item = [[UIBarButtonItem alloc] initWithCustomView:button];
    return item;
}


@end
