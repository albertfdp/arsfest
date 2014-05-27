//
//  ARSInfoView.h
//  Arsfest
//
//  Created by Thibaud Robelain on 22/04/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ARSInfoView : UIView

@property (weak, nonatomic) IBOutlet UILabel *titleLabel;
@property (weak, nonatomic) IBOutlet UITextView *descriptionTextView;
@property (weak, nonatomic) IBOutlet UIImageView *imageView;

- (void)setTitle:(NSString*)title description:(NSString*)description image:(UIImage*)image;

@end
