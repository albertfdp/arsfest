//
//  UIBarButtonItem+RightBarItem.h
//  Arsfest
//
//  Created by Thibaud Robelain on 26/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIBarButtonItem (RightBarItem)

+(UIBarButtonItem*)itemWithImage:(UIImage*)image target:(id)target action:(SEL)selector;

@end
