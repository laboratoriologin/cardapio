//
//  CLSubItemPedidoView.h
//  cardapio
//
//  Created by Julio Rocha on 04/06/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CLSubItemPedidoView : UIView

@property(nonatomic,strong) NSMutableArray *subItens;
@property(nonatomic,strong) NSNumber *valorParcial;
- (void)configure;

@end
