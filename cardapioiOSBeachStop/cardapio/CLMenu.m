//
//  CAMenu.m
//  cardapio
//
//  Created by Julio Rocha on 15/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLMenu.h"
#import "CLItem.h"
#import "CLSubItemDAO.h"


@implementation CLMenu

- (instancetype) initWithDictionary:(NSDictionary *) dictionary {
    
    self = [self init];
    
    if(self) {
        
        self.itens = [NSMutableArray array];
        
        CLItem *item = nil;
        
        CLSubItem *subItem = nil;
        
        NSDictionary *dicItem;
        
        for(NSDictionary * dicItens in dictionary) {
            
            dicItem = [dicItens objectForKey:@"item"];
            
            item                   = [[CLItem alloc]init];
            
            item.guarnicoes        = [dicItem objectForKey:@"guarnicoes"];
            item.nome              = [dicItem objectForKey:@"nome"];
            item.codigo            = [dicItem objectForKey:@"id"];
            item.imagem            = [dicItem objectForKey:@"imagem"];
            item.descricao         = [dicItem objectForKey:@"descricao"];
            item.ingredientes      = [dicItem objectForKey:@"ingredientes"];
            item.tempoMedioPreparo = [dicItem objectForKey:@"tempoMedioPreparo"];
            item.menu              = [[CLMenu alloc]init];
            item.menu.codigo       = [[[dicItem objectForKey:@"empresaCategoriaCardapio"]objectForKey:@"categoriaCardapio"]objectForKey:@"id"];
            item.subItens          = [NSMutableArray arrayWithCapacity:0];
            
            if ( [[dicItem objectForKey:@"subItens"]respondsToSelector:@selector(objectAtIndex:)] ) {
            
                for(NSDictionary * dicSubItem in [dicItem objectForKey:@"subItens"]) {
                    
                    subItem = [[CLSubItem alloc]init];
                    
                    subItem.valor         = [dicSubItem objectForKey:@"valor"];
                    subItem.codigo        = [dicSubItem objectForKey:@"id"];
                    subItem.tipoDescricao = [[dicSubItem objectForKey:@"tipoQuantidade"]objectForKey:@"descricao"];
                    subItem.nome          = [dicSubItem objectForKey:@"nome"];
                    subItem.descricao     = [dicSubItem objectForKey:@"descricao"];
                    subItem.quantidade    = [dicSubItem objectForKey:@"quantidade"];
                    
                    [item.subItens addObject:subItem];
                    
                }
                
            } else {
                
                NSDictionary * dicSubItem = [dicItem objectForKey:@"subItens"];
                
                if (dicSubItem) {
                    
                    subItem = [[CLSubItem alloc]init];
                    
                    subItem.valor         = [dicSubItem objectForKey:@"valor"];
                    subItem.codigo        = [dicSubItem objectForKey:@"id"];
                    subItem.tipoDescricao = [[dicSubItem objectForKey:@"tipoQuantidade"]objectForKey:@"descricao"];
                    subItem.descricao     = [dicSubItem objectForKey:@"descricao"];
                    subItem.quantidade    = [dicSubItem objectForKey:@"quantidade"];
                    
                    [item.subItens addObject:subItem];
                    
                }
                
            }
            
            [self.itens addObject:item];
            
        }
        
    }
    
    return self;
    
}


- (instancetype) initWithID:(NSNumber *) code {
    
    self = [super init];

    self.codigo = code;
    
    switch ([code intValue]) {
        
        case CAMBebida:
            self.descricao  = @"Bebidas";
            self.imagem     = @"bt_home_bebidas";
            self.imagemTopo = @"icone_topo_bebidas";
            break;
        
        case CAMTiraGosto:
            self.descricao  = @"Petiscos";
            self.imagem     = @"bt_home_entradas";
            self.imagemTopo = @"icone_topo_entradas";
            break;
        
        case CAMSaladas:
            self.descricao  = @"Saladas";
            self.imagem     = @"bt_home_saladas";
            self.imagemTopo = @"icone_topo_saladas";
            break;
        
        case CAMAcompanhamentos:
            self.descricao  = @"Acompanhamentos";
            self.imagem     = @"bt_home_acompanhamento";
            self.imagemTopo = @"icone_topo_acompanhamentos";
            break;
        
        case CAMPizzas:
            self.descricao  = @"Pizzas";
            self.imagem     = @"bt_home_pizza";
            self.imagemTopo = @"icone_topo_pizzas";
            break;
        
        case CAMPratosMar:
            self.descricao  = @"Pratos do mar";
            self.imagem     = @"bt_home_pratos_mar";
            self.imagemTopo = @"icone_topo_pratos_do_mar";
            break;
        
        case CAMPratosTerra:
            self.descricao  = @"Pratos da terra";
            self.imagem     = @"bt_home_pratos_terra";
            self.imagemTopo = @"icone_topo_pratos_terra";
            break;
        
        case CAMSobremesa:
            self.descricao = @"Sobremesas";
            self.imagem    = @"bt_home_sobremesas";
            self.imagemTopo = @"icone_topo_sobremesas";
            break;
            
        case CAMPasteis:
            self.descricao = @"Pastéis";
            self.imagem    = @"bt_home_pasteis";
            self.imagemTopo = @"icone_topo_pasteis";
            break;
       
        case CAMTodosPratos:
            self.descricao  = @"Todos os pratos";
            self.imagem     = @"bt_home_todos_pratos";
            self.imagemTopo = @"icone_topo_todos_pratos";
            break;
            
        default:
            break;
    }
    
    return self;
    
}

@end
