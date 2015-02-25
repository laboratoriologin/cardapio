//
//  CACardapioBS.m
//  cardapio
//
//  Created by Julio Rocha on 15/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLCardapioBS.h"
#import "CLMenuDAO.h"

@implementation CLCardapioBS


- (void)findCategorias:(NSMutableArray *) result {

    NSMutableArray *menus = [[[CLMenuDAO alloc]init]getAll];
    
    resultado = result;
    
    if( [menus count] > 0 ) {
        
        for(CLMenu *menu in menus) {
          
            [resultado addObject:menu];
            
        }
        
        [resultado addObject:[[CLMenu alloc]initWithID:[NSNumber numberWithInt:CAMTodosPratos]]];
        
        if(self.completionHandler) {
        
            self.completionHandler();
       
        }
        
    } else {
    
        NSString *url = [CLAppBaseUrl stringByAppendingString:@"categorias/ativo"];
        
        NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:url] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:10];
        
        (void) [[NSURLConnection alloc] initWithRequest:request delegate:self];
        
    }
    
    
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
    
    NSDictionary *resultados = [NSJSONSerialization JSONObjectWithData:self.mutableData
                                                               options:NSJSONReadingMutableContainers error:nil];
    
    CLMenu *menu = nil;
    
    for(NSDictionary *categoriaCardapio in resultados) {

        menu = [[CLMenu alloc]init];
        
        menu.codigo = [[categoriaCardapio objectForKey:@"categoria"] objectForKey:@"id"];
        
        menu.descricao = [[categoriaCardapio objectForKey:@"categoria"] objectForKey:@"descricao"];
        
        menu.imagem = [[categoriaCardapio objectForKey:@"categoria"] objectForKey:@"imagem"];
        
        menu.ordem = [[categoriaCardapio objectForKey:@"categoria"] objectForKey:@"ordem"];
        
        menu.imagemTopo = [@"topo_" stringByAppendingString:menu.imagem];
        
        [resultado addObject:menu];
        
    }
    
     [resultado addObject:[[CLMenu alloc] initWithID:[NSNumber numberWithInt:CAMTodosPratos]]];
    
    CLMenuDAO *menuDAO = [[CLMenuDAO alloc]init];
    
    for(CLMenu *menu in resultado) {
        
        if(menu.codigo.intValue!=CAMTodosPratos) {
    
            [menuDAO inserir:menu];
            
        }
    
    }
    
    if(self.completionHandler) {
        self.completionHandler();
    }
    
}

@end
