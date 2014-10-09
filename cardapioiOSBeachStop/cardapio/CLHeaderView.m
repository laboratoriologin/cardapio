//
//  CLHeaderView.m
//  cardapio
//
//  Created by Julio Rocha on 05/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLHeaderView.h"
#import "UIFont+DAHFontUtil.h"
@implementation CLHeaderView

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)awakeFromNib {
    self.titulo.font = [UIFont appFontWithSize:15];
}


@end
