//
//  CLItem.h
//  cardapio
//
//  Created by Julio Rocha on 25/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CLMenu.h"
@interface CLItem : NSObject


@property(nonatomic,strong) NSNumber *codigo;
@property(nonatomic,strong) NSNumber *tempoMedioPreparo;
@property(nonatomic,strong) NSString *nome;
@property(nonatomic,strong) NSString *descricao;
@property(nonatomic,strong) NSString *imagem;
@property(nonatomic,strong) NSString *ingredientes;
@property(nonatomic,strong) NSString *guarnicoes;
@property(nonatomic,strong) NSMutableArray *subItens;
@property(nonatomic,strong) UIImage *icone;
@property(nonatomic,strong) CLMenu *menu;
@end
