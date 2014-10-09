//
//  CLSubItemPedidoView.m
//  cardapio
//
//  Created by Julio Rocha on 04/06/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLSubItemPedidoView.h"
#import "CLSubItemDAO.h"
#import "UIFont+DAHFontUtil.h"
#import "UIColor+DAHColorUtil.h"
@interface CLSubItemPedidoView() {
    NSMutableArray *stepLabels;
    CLSubItemDAO *subItemDAO;
}

@end

@implementation CLSubItemPedidoView

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        
    }
    return self;
}

- (void)configure {
    
    if (!subItemDAO) {
        subItemDAO = [[CLSubItemDAO alloc]init];
    }
 
    stepLabels = [NSMutableArray array];

    self.subItens = [subItemDAO getAllPendente];
    
    NSNumber *parcial = @0;
    
    CLSubItem *subItemAtPosition = nil;
    
    for(int i = 0; i<[self.subItens count]; i++) {
        
        subItemAtPosition = [self.subItens objectAtIndex:i];
        
        [self addSubview:[self stepperViewFromPosition:i subItem:subItemAtPosition]];
        
        [self addSubview:[self tamanhoViewFromPosition:i subItem:subItemAtPosition]];
        
        [self addSubview:[self excluirViewFromPosition:i]];
        
        parcial = [NSNumber numberWithDouble: [parcial doubleValue] + ([subItemAtPosition.quantidade doubleValue] * [subItemAtPosition.valor doubleValue]) ];
        
    }
    
    self.valorParcial = parcial;
    
}

- (UIView *)stepperViewFromPosition:(int)position subItem:(CLSubItem *) subItem {
    
    static int verticalStep   = 36;
    static int stepButtonSize = 20;
    static int marginX = 3;
    static int marginY = 8;
    
    UIImageView *stepLeftButton;
    UIImageView *stepRightButton;
    UILabel  *stepLabel;
    
    UIView *view = [[UIView alloc]initWithFrame:CGRectMake(5, position * verticalStep, 75, verticalStep)];
    
    view.backgroundColor = [UIColor whiteColor];
    
    stepLeftButton = [[UIImageView alloc]initWithFrame:CGRectMake(marginX, marginY, stepButtonSize, stepButtonSize)];
    
    stepLeftButton.image = [UIImage imageNamed:@"seta_para_esquerda"];
    
    stepLeftButton.tag = position;
    
    stepLeftButton.contentMode = UIViewContentModeScaleAspectFit;
    
    stepLeftButton.userInteractionEnabled = YES;
    
    [stepLeftButton addGestureRecognizer:[[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(stepLeft:)]];
    
    [view addSubview:stepLeftButton];
    
    stepLabel = [[UILabel alloc]initWithFrame: CGRectMake(stepLeftButton.frame.origin.x + stepButtonSize + marginX, stepLeftButton.frame.origin.y, stepButtonSize, stepButtonSize)];
    
    stepLabel.font = [UIFont appFontWithSize:16];
    
    stepLabel.text = [NSString stringWithFormat:@"%d",[subItem.quantidade intValue]];
    
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

- (UIView *)tamanhoViewFromPosition:(int)position subItem:(CLSubItem *)subItem {
    
    static int verticalStep   = 36;
    
    UILabel  *tamanhoLabel;
    
    UIView *view = [[UIView alloc]initWithFrame:CGRectMake(75, position * verticalStep, 215, verticalStep)];
    
    view.backgroundColor = [UIColor whiteColor];
    
    CALayer *layer = view.layer;
    
    layer.borderWidth = 1;
    
    layer.borderColor = [[UIColor colorFromHexString:@"F2DBB3"]CGColor];
    
    tamanhoLabel = [[UILabel alloc]initWithFrame:CGRectMake(5, 0, 190, verticalStep)];
    
    tamanhoLabel.font = [UIFont appFontWithSize:12];
    
    tamanhoLabel.textColor = [UIColor colorFromHexString:@"#634C47"];
    
    tamanhoLabel.text = [NSString stringWithFormat:@"%@ - %@",subItem.item.nome,subItem.descricao];
    
    tamanhoLabel.numberOfLines=2;
    
    [view addSubview:tamanhoLabel];
    
    return view;
    
}

- (UIView *)excluirViewFromPosition:(int)position {
    
    static int verticalStep   = 36;
    
    UILabel *excluirLabel;
    
    UIView *view = [[UIView alloc]initWithFrame:CGRectMake(285, position * verticalStep, 30, verticalStep)];
    
    view.backgroundColor = [UIColor whiteColor];
    
    CALayer *layer = view.layer;
    
    layer.borderWidth = 1;
    
    layer.borderColor = [[UIColor colorFromHexString:@"F2DBB3"]CGColor];
    
    excluirLabel = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, 30, verticalStep)];
    
    excluirLabel.font = [UIFont appFontWithSize:14];
    
    excluirLabel.textAlignment = NSTextAlignmentCenter;
    
    excluirLabel.textColor = [UIColor colorFromHexString:@"#634C47"];
    
    excluirLabel.text = @"-";
    
    [view addSubview:excluirLabel];
    
    view.tag = position;
    
    [view addGestureRecognizer:[[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(remover:)]];
    
    return view;
    
}

#pragma mark - Actions

- (void)stepRight:(UITapGestureRecognizer *) sender {
    
    CLSubItem *subItem = [self.subItens objectAtIndex:sender.view.tag];
    
    if(subItem.quantidadeSelecionada < 99) {
        
        subItem.quantidadeSelecionada++;
        
        UILabel *label = [stepLabels objectAtIndex:sender.view.tag];
        
        label.text = [NSString stringWithFormat:@"%d",subItem.quantidadeSelecionada];
        
        [subItemDAO alterarPedido:subItem];
        
        self.valorParcial = [NSNumber numberWithDouble:[self.valorParcial doubleValue] + [subItem.valor doubleValue]];
        
    }
    
}

- (void)stepLeft:(UITapGestureRecognizer *) sender {
    
    CLSubItem *subItem = [self.subItens objectAtIndex:sender.view.tag];
    
    if (subItem.quantidadeSelecionada > 0) {
        
        subItem.quantidadeSelecionada--;
        
        UILabel *label = [stepLabels objectAtIndex:sender.view.tag];
        
        label.text = [NSString stringWithFormat:@"%d", subItem.quantidadeSelecionada];
        
        [subItemDAO alterarPedido:subItem];
        
        self.valorParcial = [NSNumber numberWithDouble:[self.valorParcial doubleValue] - [subItem.valor doubleValue]];
        
    }
    
}

- (void)remover:(UITapGestureRecognizer *) sender {
    
    CLSubItem *subItem = [self.subItens objectAtIndex:sender.view.tag];
    
    CLSubItemDAO *dao = [[CLSubItemDAO alloc]init];
    
    [dao excluirPedido:subItem];
    
    [self.subItens removeObject:subItem];
    
    [[NSNotificationCenter defaultCenter] postNotificationName:@"arrayUpdated" object:nil];
    
}



@end
