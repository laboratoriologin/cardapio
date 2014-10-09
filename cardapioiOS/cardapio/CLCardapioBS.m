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
    
    if([menus count]>0) {
        
        for(CLMenu *menu in menus) {
          
            [resultado addObject:menu];
            
        }
        
        [resultado addObject:[[CLMenu alloc]initWithID:[NSNumber numberWithInt:CAMTodosPratos]]];
        
        if(self.completionHandler) {
            self.completionHandler();
        }
        
    } else {
    
        NSString *url = [CLAppBaseUrl stringByAppendingFormat:@"categorias_cardapio/empresa/%@/id", CLKeyEmpresa];
        
        NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:url] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:10];
        
        (void) [[NSURLConnection alloc] initWithRequest:request delegate:self];
        
    }
    
    
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
    
    NSDictionary *resultados = [NSJSONSerialization JSONObjectWithData:self.mutableData
                                                               options:NSJSONReadingMutableContainers error:nil];
    
    
    for(NSDictionary *categoriaCardapio in resultados) {
        
        [resultado addObject:[[CLMenu alloc]initWithID:[[categoriaCardapio objectForKey:@"categoriacardapio"]objectForKey:@"id"]]];
        
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
