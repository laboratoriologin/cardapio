//
//  CLConta.m
//  cardapio
//
//  Created by Julio Rocha on 04/06/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLConta.h"
#import "CLSubItem.h"
@implementation CLConta

- (instancetype)initWithDictionary:(NSDictionary *)dictionary {
    
    self = [super init];
    
    if (self) {
        
        NSDictionary *conta = [dictionary objectForKey:@"conta"];
        
        self.codigo         = [conta objectForKey:@"id"];
        self.horarioChegada = [conta objectForKey:@"horarioChegada"];
        self.valor          = [conta objectForKey:@"valor"];
        self.valorPago      = [conta objectForKey:@"valorPago"];
        self.subItens       = [NSMutableArray array];
        
        CLSubItem *subItem = nil;
        
        if (![[conta objectForKey:@"pedidoSubItem"] respondsToSelector:@selector(objectAtIndex:)]) {
         
            NSDictionary *pedido = [conta objectForKey:@"pedidoSubItem"];

            if (pedido) {
                subItem                  = [[CLSubItem alloc]init];
                subItem.quantidade       = [pedido objectForKey:@"quantidade"];
                subItem.descricao        = [[pedido objectForKey:@"subitem"] objectForKey:@"descricao"];
                subItem.valor            = [[pedido objectForKey:@"subitem"] objectForKey:@"valor"];
                subItem.item             = [[CLItem alloc]init];
                subItem.item.nome        = [[[pedido objectForKey:@"subitem"] objectForKey:@"item"]objectForKey:@"nome"];
                subItem.valorTotalPedido = [pedido objectForKey:@"valor"];
                [self.subItens addObject:subItem];
            }

       
        } else {
            
            for(NSDictionary *pedido in [conta objectForKey:@"pedidoSubItem"]) {
                
                subItem                  = [[CLSubItem alloc]init];
                subItem.quantidade       = [pedido objectForKey:@"quantidade"];
                subItem.descricao        = [[pedido objectForKey:@"subitem"] objectForKey:@"descricao"];
                subItem.valor            = [[pedido objectForKey:@"subitem"] objectForKey:@"valor"];
                subItem.item             = [[CLItem alloc]init];
                subItem.item.nome        = [[[pedido objectForKey:@"subitem"] objectForKey:@"item"]objectForKey:@"nome"];
                subItem.valorTotalPedido = [pedido objectForKey:@"valor"];
                [self.subItens addObject:subItem];
            }
        }
        
    }
    
    return self;
    
}

@end
