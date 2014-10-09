//
//  CATitleView.m
//  cardapio
//
//  Created by Julio Rocha on 14/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLTitleView.h"
#import "UIFont+DAHFontUtil.h"
@implementation CLTitleView

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        
    }
    return self;
}

- (void) awakeFromNib {
    
    [super awakeFromNib];
    
    self.title.font = [UIFont appFontWithSize:15];
    
}

- (void) configure {
    
    self.title.text = _titulo;
    
    self.image.image = [UIImage imageNamed:self.imagem];
    
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/

@end
