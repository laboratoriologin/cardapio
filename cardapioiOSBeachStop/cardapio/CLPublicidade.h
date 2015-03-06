//
//  CLPublicidade.h
//  cardapio
//
//  Created by Julio Rocha on 21/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface CLPublicidade : NSObject

@property(nonatomic,strong) NSNumber *codigo;
@property(nonatomic,strong) NSString *nome;
@property(nonatomic,strong) NSString *descricao;
@property(nonatomic,strong) NSString *imagem;
@property(nonatomic,strong) NSString *link;
@property(nonatomic,strong) NSString *texto;
@property(nonatomic,strong) NSNumber *preco;


- (instancetype)initWithDictionary:(NSDictionary *)dictionary;


@end
