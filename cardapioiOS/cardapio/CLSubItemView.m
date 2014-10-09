//
//  CLSubItemView.m
//  cardapio
//
//  Created by Julio Rocha on 29/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLSubItemView.h"
#import <QuartzCore/QuartzCore.h>
#import "UIColor+DAHColorUtil.h"
#import "UIFont+DAHFontUtil.h"
@implementation CLSubItemView

- (id)initWithFrame:(CGRect)frame {
    self = [super initWithFrame:frame];
    if (self) {
     
    }
    return self;
}

- (void) awakeFromNib {
    
    self.backgroundColor = [UIColor colorFromHexString:@"#F2DBB3"];
    
}

- (void) configure {

    UILabel *valorLabel        = nil;
    UILabel *quantidadeLabel   = nil;
    
    for(UIView *view in self.subviews) {
        [view removeFromSuperview];
    }
    
    int yAxis = 0;
    
    UIFont *font = [UIFont appFontWithSize:10];
  
    int posItem = 0;
    
    for(CLSubItem *subItem in self.item.subItens) {
        
        quantidadeLabel = [[UILabel alloc]initWithFrame:CGRectMake(4, yAxis, 69, 12)];
        
        quantidadeLabel.text = [[[subItem quantidade] stringValue] stringByAppendingFormat:@" %@",subItem.tipoDescricao];
        
        quantidadeLabel.font = font;
        
        [self addSubview:quantidadeLabel];

        valorLabel = [[UILabel alloc]initWithFrame:CGRectMake(75, yAxis, 55, 12)];
        
        NSNumberFormatter *formatter = [[NSNumberFormatter alloc]init];
        
        formatter.numberStyle = NSNumberFormatterCurrencyStyle;
        
        formatter.locale = [NSLocale localeWithLocaleIdentifier:@"pt_BR"];
        
        valorLabel.text = [formatter stringFromNumber:subItem.valor];
        
        valorLabel.font = font;
        
        valorLabel.textAlignment = NSTextAlignmentRight;

        [self addSubview:valorLabel];
        
        yAxis = yAxis + 14;
        
        posItem++;

        if(posItem==4) {
            break;
        }
        
    }
    
    CGRect frame = self.frame;
    
    frame.size = CGSizeMake(self.frame.size.width, yAxis);
    
    self.frame = frame;
}

@end
