//
//  CLSubItem.h
//  cardapio
//
//  Created by Julio Rocha on 25/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CLItem.h"
@interface CLSubItem : NSObject


@property(nonatomic,strong) NSNumber *codigo;
@property(nonatomic,strong) NSNumber *quantidade;
@property(nonatomic,strong) NSString *nome;
@property(nonatomic,strong) NSString *tipoDescricao;
@property(nonatomic,strong) NSNumber *valor;
@property(nonatomic,strong) NSNumber *valorTotalPedido;
@property(nonatomic,strong) NSString *descricao;
@property(nonatomic,strong) CLItem *item;
@property(nonatomic,assign) int quantidadeSelecionada;

@end
