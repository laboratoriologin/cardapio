//
//  CLCollectionViewNoImageCell.m
//  cardapio
//
//  Created by Julio Rocha on 07/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLCollectionViewNoImageCell.h"
#import "UIColor+DAHColorUtil.h"
#import "UIFont+DAHFontUtil.h"
@implementation CLCollectionViewNoImageCell

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)awakeFromNib {
    
    self.titulo.textColor = [UIColor colorFromHexString:@"#4F3832"];
    
    self.titulo.font = [UIFont appFontWithSize:15];
    
}


@end
