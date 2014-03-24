//
//  UIView+Borders.h
//  Arsfest
//
//  Created by Thibaud Robelain on 21/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIView (Borders)

- (void)addBorderTop:(BOOL)top bottom:(BOOL)bottom right:(BOOL)right left:(BOOL)left outside:(BOOL)outside;

@end
