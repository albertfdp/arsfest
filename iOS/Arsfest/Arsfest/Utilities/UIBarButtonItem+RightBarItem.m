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
    UIBarButtonItem *item = [[UIBarButtonItem alloc] initWithImage:[image imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal] style:UIBarButtonItemStylePlain target:target action:selector];
    return item;
}


@end
