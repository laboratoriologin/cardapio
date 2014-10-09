//
//  CAMenu.h
//  cardapio
//
//  Created by Julio Rocha on 15/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>

const int CAMBebida            = 1;
const int CAMEntradas          = 2;
const int CAMSaladas           = 3;
const int CAMSugestaoChefe     = 4;
const int CAMMassas            = 5;
const int CAMGrelhados         = 6;
const int CAMComidaOriental    = 7;
const int CAMSobremesa         = 8;
const int CAMTodosPratos       = 0;

@interface CLMenu : NSObject

@property(nonatomic,strong) NSNumber * codigo;
@property(nonatomic,strong) NSString * descricao;
@property(nonatomic,strong) NSString * imagem;
@property(nonatomic,strong) NSString * imagemTopo;
@property(nonatomic,strong) NSMutableArray * itens;
@property(nonatomic,assign) BOOL showItem;

- (instancetype) initWithID:(NSNumber *) code;
- (instancetype) initWithDictionary:(NSDictionary *) dictionary;
@end
