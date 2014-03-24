//
//  UIViewController+Edges.m
//  Arsfest
//
//  Created by Thibaud Robelain on 14/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import "UIViewController+Edges.h"

@implementation UIViewController (Edges)

#if __IPHONE_OS_VERSION_MAX_ALLOWED >= 70000
- (UIRectEdge)edgesForExtendedLayout
{
    return UIRectEdgeNone;
}

#endif

@end
