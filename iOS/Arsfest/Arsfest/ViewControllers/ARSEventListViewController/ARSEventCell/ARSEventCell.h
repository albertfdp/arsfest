//
//  ARSEventCell.h
//  Arsfest
//
//  Created by Thibaud Robelain on 13/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ARSEvent.h"

@interface ARSEventCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UIImageView *imageView;
@property (weak, nonatomic) IBOutlet UILabel *progressView;

@property (weak, nonatomic) IBOutlet UILabel *labelTitle;
@property (weak, nonatomic) IBOutlet UILabel *labelLocation;
@property (weak, nonatomic) IBOutlet UILabel *labelTime;

- (void)configureCellWithEvent:(ARSEvent*)event;

@end
