//
//  CAMenu.h
//  cardapio
//
//  Created by Julio Rocha on 15/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>

const int CAMBebida            = 1;
const int CAMTiraGosto         = 2;
const int CAMSaladas           = 3;
const int CAMAcompanhamentos   = 4;
const int CAMPizzas            = 5;
const int CAMPratosMar         = 6;
const int CAMPratosTerra       = 7;
const int CAMSobremesa         = 8;
const int CAMPasteis          = 14;
const int CAMTodosPratos       = 0;

@interface CLMenu : NSObject

@property(nonatomic,strong) NSNumber * codigo;
@property(nonatomic,strong) NSString * descricao;
@property(nonatomic,strong) NSString * imagem;
@property(nonatomic,strong) NSString * imagemTopo;
@property(nonatomic,strong) NSMutableArray * itens;
@property(nonatomic,assign) BOOL showItem;
@property(nonatomic,strong) NSNumber * ordem;

- (instancetype) initWithID:(NSNumber *) code;
- (instancetype) initWithDictionary:(NSDictionary *) dictionary;
@end
