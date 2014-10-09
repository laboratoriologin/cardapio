//
//  CLCollectionViewSingleCell.m
//  cardapio
//
//  Created by Julio Rocha on 29/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLCollectionViewSingleCell.h"
#import "UIColor+DAHColorUtil.h"
#import <QuartzCore/QuartzCore.h>
#import "UIFont+DAHFontUtil.h"
@implementation CLCollectionViewSingleCell

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void) awakeFromNib {
    
    self.nomeLabel.textColor = [UIColor appButtonTextColor];
    
    self.nomeLabel.font = [UIFont appFontWithSize:12];
    
    self.ingredienteLabel.textColor = [UIColor appButtonTextColor];
    
    self.ingredienteLabel.font = [UIFont appFontWithSize:10];
    
    self.imageView.contentMode = UIViewContentModeScaleAspectFill;
    
    self.imageView.clipsToBounds = YES;
    
    [self setImageOffset:self.imageOffset];
    
    self.detalhesButton.titleLabel.font = [UIFont appFontWithSize:11];
    
}

- (void)setImageOffset:(CGPoint)imageOffset
{
    // Store padding value
   _imageOffset = imageOffset;
    
    // Grow image view
    CGRect frame = self.imageView.bounds;
    CGRect offsetFrame = CGRectOffset(frame, _imageOffset.x, _imageOffset.y);
    self.imageView.frame = offsetFrame;
}

@end
