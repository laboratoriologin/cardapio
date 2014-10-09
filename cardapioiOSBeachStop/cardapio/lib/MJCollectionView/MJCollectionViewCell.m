//
//  MJCollectionViewCell.m
//  RCCPeakableImageSample
//
//  Created by Mayur on 4/1/14.
//  Copyright (c) 2014 RCCBox. All rights reserved.
//

#import "MJCollectionViewCell.h"
#import "UIColor+DAHColorUtil.h"
@interface MJCollectionViewCell()

@property (nonatomic, strong, readwrite) UIImageView *MJImageView;

@property (nonatomic, strong) UILabel *titulo;

@property (nonatomic, strong) UILabel *descricao;

@end

@implementation MJCollectionViewCell

const int MJLarguraCelula = 158;
const int MJAlturaCelula = 158;

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) [self setupImageView];
    return self;
}

- (id)initWithCoder:(NSCoder *)aDecoder {
    self = [super initWithCoder:aDecoder];
    if (self) [self setupImageView];
    return self;
}

- (void)setFrame:(CGRect) frame {
    [super setFrame:frame];
    [self setNeedsDisplay];
    [self.MJImageView setNeedsDisplay]; // force drawRect:
}


#pragma mark - Setup Method
- (void)setupImageView {
    // Clip subviews
    self.clipsToBounds = YES;
    
    // Add image subview
    self.MJImageView = [[UIImageView alloc] initWithFrame:CGRectMake(self.bounds.origin.x, self.bounds.origin.y, self.bounds.size.width, IMAGE_HEIGHT)];
    
    self.MJImageView.backgroundColor = [UIColor brownColor];
  
    self.MJImageView.contentMode = UIViewContentModeScaleAspectFill;
   
    self.MJImageView.clipsToBounds = NO;
  
    [self addSubview:self.MJImageView];
    
    CGRect frame = CGRectMake(0,self.frame.size.height-40, MJLarguraCelula, 20);
    
    self.titulo = [[UILabel alloc]initWithFrame:frame];
    
    self.titulo.backgroundColor = [UIColor whiteColor];
    
    self.titulo.textColor = [UIColor colorFromHexString:@"#4F3832"];
    
    self.titulo.font = [UIFont fontWithName:@"CenturyGothic" size:13];
    
    [self addSubview:self.titulo];
    
    frame = CGRectMake(0,self.frame.size.height-20, MJLarguraCelula,20);
    
    self.descricao = [[UILabel alloc]initWithFrame:frame];
    
    self.descricao.backgroundColor = [UIColor whiteColor];
    
    self.descricao.textColor = [UIColor colorFromHexString:@"4F3832"];
    
    self.descricao.numberOfLines = 0;
    
    self.descricao.font = [UIFont fontWithName:@"CenturyGothic" size:12];
    
    [self addSubview:self.descricao];
    
}

# pragma mark - Setters

- (void)setImage:(UIImage *)image {
    
    self.MJImageView.frame = CGRectMake(0, 0, 150, IMAGE_HEIGHT);
    
    self.MJImageView.image = image;
    
    self.backgroundColor = [UIColor whiteColor];
    
    [self setImageOffset:self.imageOffset];
    
}

- (void) setTitulo:(NSString *)titulo descricao:(NSString *)descricao {
    
    self.titulo.text = titulo;
    
    self.descricao.text = descricao;
    
}

- (void)setImageOffset:(CGPoint)imageOffset
{
    // Store padding value
    _imageOffset = imageOffset;
    
    // Grow image view
    CGRect frame = self.MJImageView.bounds;
    CGRect offsetFrame = CGRectOffset(frame, _imageOffset.x, _imageOffset.y);
    self.MJImageView.frame = offsetFrame;
}

- (NSString *) reuseIdentifier {
    return @"MJCell";
}

@end
