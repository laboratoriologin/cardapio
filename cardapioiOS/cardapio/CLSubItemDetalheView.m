//
//  CLSubItemDetalheView.m
//  cardapio
//
//  Created by Julio Rocha on 08/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLSubItemDetalheView.h"
#import "UIFont+DAHFontUtil.h"
#import "UIColor+DAHColorUtil.h"
#import <QuartzCore/QuartzCore.h>
@interface CLSubItemDetalheView() {
    NSMutableArray *stepLabels;
}

@end

@implementation CLSubItemDetalheView

- (id)initWithFrame:(CGRect)frame {
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)configure {
    
    stepLabels = [NSMutableArray array];
    
    for(int i = 0; i<[self.item.subItens count]; i++) {

        [self addSubview:[self stepperViewFromPosition:i]];
        
        [self addSubview:[self tamanhoViewFromPosition:i]];
        
        [self addSubview:[self precoViewFromPosition:i]];

    }
    
}

- (UIView *)stepperViewFromPosition:(int)position {
    
    static int verticalStep   = 36;
    static int stepButtonSize = 25;
    static int marginX = 3;
    static int marginY = 4;
    
    UIImageView *stepLeftButton;
    UIImageView *stepRightButton;
    UILabel  *stepLabel;
    
    UIView *view = [[UIView alloc]initWithFrame:CGRectMake(15, position * verticalStep, 90, verticalStep)];
    
    stepLeftButton = [[UIImageView alloc]initWithFrame:CGRectMake(marginX, marginY, stepButtonSize, stepButtonSize)];
    
    stepLeftButton.image = [UIImage imageNamed:@"seta_para_esquerda"];
    
    stepLeftButton.tag = position;
    
    stepLeftButton.contentMode = UIViewContentModeScaleAspectFit;
    
    stepLeftButton.userInteractionEnabled = YES;
    
    [stepLeftButton addGestureRecognizer:[[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(stepLeft:)]];
    
    [view addSubview:stepLeftButton];
    
    stepLabel = [[UILabel alloc]initWithFrame:
                 CGRectMake(stepLeftButton.frame.origin.x + stepButtonSize + marginX, stepLeftButton.frame.origin.y, stepButtonSize, stepButtonSize)];
    
    stepLabel.font = [UIFont appFontWithSize:16];
    
    stepLabel.text = @"0";
    
    stepLabel.textAlignment = NSTextAlignmentCenter;
    
    [view addSubview:stepLabel];
    
    stepRightButton = [[UIImageView alloc]initWithFrame:CGRectMake(stepLabel.frame.origin.x + stepButtonSize + marginX, stepLeftButton.frame.origin.y, stepButtonSize + marginX, stepButtonSize)];
    
    stepRightButton.image = [UIImage imageNamed:@"seta_para_direita"];
    
    stepRightButton.tag = position;
    
    [stepRightButton addGestureRecognizer:[[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(stepRight:)]];
    
    stepRightButton.contentMode = UIViewContentModeScaleAspectFit;
    
    stepRightButton.userInteractionEnabled = YES;
    
    [view addSubview:stepRightButton];
    
    CALayer *layer = view.layer;
    
    layer.borderWidth = 1;
    
    layer.borderColor = [[UIColor colorFromHexString:@"F2DBB3"]CGColor];
    
    [stepLabels addObject:stepLabel];
    
    return view;
    
}

- (UIView *)tamanhoViewFromPosition:(int)position {
    
    static int verticalStep   = 36;

    UILabel  *tamanhoLabel;
    
    UIView *view = [[UIView alloc]initWithFrame:CGRectMake(105, position * verticalStep, 120, verticalStep)];
    
    CALayer *layer = view.layer;
    
    layer.borderWidth = 1;
    
    layer.borderColor = [[UIColor colorFromHexString:@"F2DBB3"]CGColor];
    
    tamanhoLabel = [[UILabel alloc]initWithFrame:CGRectMake(5, 0, 110, verticalStep)];
    
    tamanhoLabel.font = [UIFont appFontWithSize:14];
    
    tamanhoLabel.textColor = [UIColor colorFromHexString:@"#634C47"];
    
    CLSubItem *subItem = [self.item.subItens objectAtIndex:position];
    
    tamanhoLabel.text = subItem.descricao;
    
    tamanhoLabel.textAlignment = NSTextAlignmentCenter;
    
    [view addSubview:tamanhoLabel];
    
    return view;
    
}

- (UIView *)precoViewFromPosition:(int)position {
    
    static int verticalStep   = 36;
    
    UILabel  *precoLabel;
    
    UIView *view = [[UIView alloc]initWithFrame:CGRectMake(225, position * verticalStep, 80, verticalStep)];
    
    CALayer *layer = view.layer;
    
    layer.borderWidth = 1;
    
    layer.borderColor = [[UIColor colorFromHexString:@"F2DBB3"]CGColor];
    
    precoLabel = [[UILabel alloc]initWithFrame:CGRectMake(5, 0, 73, verticalStep)];
    
    precoLabel.font = [UIFont appFontWithSize:14];
    
    CLSubItem *subItem = [self.item.subItens objectAtIndex:position];
    
    precoLabel.textAlignment = NSTextAlignmentCenter;
    
    precoLabel.textColor = [UIColor colorFromHexString:@"#634C47"];
    
    NSNumberFormatter *formatter = [[NSNumberFormatter alloc]init];
    
    formatter.numberStyle = NSNumberFormatterCurrencyStyle;
    
    formatter.locale = [NSLocale localeWithLocaleIdentifier:@"pt_BR"];
    
    precoLabel.text = [formatter stringFromNumber:subItem.valor];
    
    [view addSubview:precoLabel];
    
    return view;
    
}

- (void)stepRight:(UITapGestureRecognizer *) sender {
    
    CLSubItem *subItem = [self.item.subItens objectAtIndex:sender.view.tag];
    
    if(subItem.quantidadeSelecionada < 99) {
    
        subItem.quantidadeSelecionada++;
        
        UILabel *label = [stepLabels objectAtIndex:sender.view.tag];
        
        label.text = [NSString stringWithFormat:@"%d",subItem.quantidadeSelecionada];
        
    }
    
}

- (void)stepLeft:(UITapGestureRecognizer *) sender {
    
    CLSubItem *subItem = [self.item.subItens objectAtIndex:sender.view.tag];
    
    if (subItem.quantidadeSelecionada > 0) {
    
        subItem.quantidadeSelecionada--;
        
        UILabel *label = [stepLabels objectAtIndex:sender.view.tag];
        
        label.text = [NSString stringWithFormat:@"%d", subItem.quantidadeSelecionada];
        
    }
    
}

@end
