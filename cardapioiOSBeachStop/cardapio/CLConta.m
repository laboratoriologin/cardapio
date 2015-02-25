//
//  CLConta.m
//  cardapio
//
//  Created by Julio Rocha on 04/06/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLConta.h"
#import "CLSubItem.h"
#import "CLSubItemDAO.h"
@implementation CLConta

- (instancetype)initWithDictionary:(NSDictionary *)dictionary {
    
    self = [super init];
    
    if (self) {
        
        CLSubItemDAO *subItemDAO = [CLSubItemDAO new];
        
        NSDictionary *conta = [dictionary objectForKey:@"conta"];
        
        self.codigo         = [conta objectForKey:@"id"];
        self.horarioChegada = [conta objectForKey:@"horarioChegada"];
        self.valor          = [conta objectForKey:@"valor"];
        self.valorPago      = [conta objectForKey:@"valorPago"];
        self.subItens       = [NSMutableArray array];
        
        CLSubItem *subItem = nil;
        
        if (![[conta objectForKey:@"pedidoSubItens"] respondsToSelector:@selector(objectAtIndex:)]) {
         
            NSDictionary *pedido = [conta objectForKey:@"pedidoSubItens"];

            if (pedido) {
                subItem                  = [[CLSubItem alloc]init];
                subItem.quantidade       = [pedido objectForKey:@"quantidade"];
                subItem.descricao        = [[pedido objectForKey:@"subItem"] objectForKey:@"descricao"];
                subItem.nome             = [[pedido objectForKey:@"subItem"] objectForKey:@"nome"];
                subItem.valor            = [[pedido objectForKey:@"subItem"] objectForKey:@"valorUnitario"];
                subItem.item             = [[CLItem alloc]init];
                subItem.item.nome        = [[[pedido objectForKey:@"subItem"] objectForKey:@"item"]objectForKey:@"nome"];
                subItem.valorTotalPedido = [pedido objectForKey:@"valorCalculado"];
                [self.subItens addObject:subItem];
            }

       
        } else {
            
            for(NSDictionary *pedido in [conta objectForKey:@"pedidoSubItens"]) {
                subItem                  = [subItemDAO getByID:[[pedido objectForKey:@"subItem"] objectForKey:@"id"]];
                subItem.quantidade       = [pedido objectForKey:@"quantidade"];
                subItem.valor            = [pedido objectForKey:@"valorUnitario"];
                subItem.valorTotalPedido = [pedido objectForKey:@"valorCalculado"];
                [self.subItens addObject:subItem];
            }
        }
        
    }
    
    return self;
    
}

@end
