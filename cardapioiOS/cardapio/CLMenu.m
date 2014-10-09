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
        
        self.itens = [NSMutableArray arrayWithCapacity:0];
        
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
            
            for(NSDictionary * dicSubItem in [dicItem objectForKey:@"subItens"]) {
                
                subItem = [[CLSubItem alloc]init];
                
                subItem.valor         = [dicSubItem objectForKey:@"valor"];
                subItem.codigo        = [dicSubItem objectForKey:@"id"];
                subItem.tipoDescricao = [[dicSubItem objectForKey:@"tipoQuantidade"]objectForKey:@"descricao"];
                subItem.descricao     = [dicSubItem objectForKey:@"descricao"];
                subItem.quantidade    = [dicSubItem objectForKey:@"quantidade"];

                [item.subItens addObject:subItem];
                
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
            self.imagem     = @"bt_home_bebidas.png";
            self.imagemTopo = @"icone_topo_bebidas";
            break;
        
        case CAMEntradas:
            self.descricao  = @"Entradas";
            self.imagem     = @"bt_home_entradas.png";
            self.imagemTopo = @"icone_topo_entradas";
            break;
        
        case CAMSaladas:
            self.descricao  = @"Saladas";
            self.imagem     = @"bt_home_saladas.png";
            self.imagemTopo = @"icone_topo_saladas";
            break;
        
        case CAMSugestaoChefe:
            self.descricao  = @"Sugestão do Chefe";
            self.imagem     = @"bt_home_sugestao_chef.png";
            self.imagemTopo = @"icone_topo_sugestao_chef";
            break;
        
        case CAMMassas:
            self.descricao  = @"Massas";
            self.imagem     = @"bt_home_massas.png";
            self.imagemTopo = @"icone_topo_massas";
            break;
        
        case CAMGrelhados:
            self.descricao  = @"Grelhados";
            self.imagem     = @"bt_home_grelhados.png";
            self.imagemTopo = @"icone_topo_grelhados";
            break;
        
        case CAMComidaOriental:
            self.descricao  = @"Comida Oriental";
            self.imagem     = @"bt_home_comida_oriental.png";
            self.imagemTopo = @"icone_topo_comida_oriental";
            break;
        
        case CAMSobremesa:
            self.descricao = @"Sobremesas";
            self.imagem    = @"bt_home_sobremesas.png";
            self.imagemTopo = @"icone_topo_sobremesas";
            break;
       
        case CAMTodosPratos:
            self.descricao  = @"Todos os pratos";
            self.imagem     = @"bt_home_todos_pratos.png";
            self.imagemTopo = @"icone_topo_todos_pratos";
            break;
            
        default:
            break;
    }
    
    return self;
    
}

@end
