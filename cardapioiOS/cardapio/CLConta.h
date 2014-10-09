//
//  CLConta.h
//  cardapio
//
//  Created by Julio Rocha on 04/06/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface CLConta : NSObject

@property(nonatomic,strong) NSNumber *codigo;
@property(nonatomic,strong) NSNumber *mesa;
@property(nonatomic,strong) NSString *horarioChegada;
@property(nonatomic,strong) NSNumber *valor;
@property(nonatomic,strong) NSNumber *valorPago;
@property(nonatomic,strong) NSMutableArray * subItens;

- (instancetype)initWithDictionary:(NSDictionary *)dictionary;

@end
