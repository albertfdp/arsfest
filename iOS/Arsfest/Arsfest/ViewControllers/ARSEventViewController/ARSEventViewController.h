//
//  ARSEventViewController.h
//  Arsfest
//
//  Created by Thibaud Robelain on 13/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ARSEvent.h"

@interface ARSEventViewController : UIViewController <UITextViewDelegate>

@property (nonatomic, strong) ARSEvent *event;

@property (weak, nonatomic) IBOutlet UIImageView *backgroundView;
@property (weak, nonatomic) IBOutlet UILabel *labelEventTitle;
@property (weak, nonatomic) IBOutlet UILabel *labelEventLocation;
@property (weak, nonatomic) IBOutlet UIScrollView *descriptionScrollView;
@property (weak, nonatomic) IBOutlet UITextView *descriptionTextView;

@end
