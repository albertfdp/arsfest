//
//  UIImage+Icon.m
//  Arsfest
//
//  Created by Thibaud Robelain on 21/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "UIImage+Icon.h"

@implementation UIImage (Icon)

+ (UIImage*)iconForTheme:(NSString*)theme
{
    NSString *fileName = [NSString stringWithFormat:@"%@_icon.png", theme];
    return [UIImage imageNamed:fileName];
}

@end
