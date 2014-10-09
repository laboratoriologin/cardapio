//
//  CLCollectionViewCell.m
//  cardapio
//
//  Created by Julio Rocha on 29/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLCollectionViewCell.h"
#import "UIColor+DAHColorUtil.h"
#import "UIFont+DAHFontUtil.h"
@implementation CLCollectionViewCell

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        
    }
    return self;
}

- (void) awakeFromNib {
    
    UIColor *textColor            = [UIColor colorFromHexString:@"#4F3832"];
    
    self.tituloLabel.textColor    = textColor;
    
    self.tituloLabel.font         = [UIFont appFontWithSize:12];
    
    self.descricaoLabel.font      = [UIFont appFontWithSize:11];
    
    self.descricaoLabel.textColor = textColor;
    
    self.descricaoLabel.numberOfLines = 0;
    
    self.imageView.contentMode = UIViewContentModeScaleAspectFill;
    
    self.imageView.clipsToBounds = NO;
    
    [self setImageOffset:self.imageOffset];
    
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
