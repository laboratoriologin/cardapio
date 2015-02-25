//
//  CLItensCardapioBS.m
//  cardapio
//
//  Created by Julio Rocha on 25/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLItensCardapioBS.h"
#import "CLItemDAO.h"
#import "CLSubitemDAO.h"
#import "CLSubItem.h"
@implementation CLItensCardapioBS

- (void)fillMenuWithItens:(CLMenu *)menu {
    
    referencia = menu;
    
    NSMutableArray *itens = [[[CLItemDAO alloc]init]getAllFromMenu:menu];
    
    if([itens count]>0) {
        
        menu.itens = itens;
        
        if(self.completionHandler) {
            self.completionHandler();
        }
        
    } else {
        
        NSString *url = [CLAppBaseUrl stringByAppendingFormat:@"itens/categorias/%d", [menu.codigo intValue]];
        
        NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:url] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:10];
        
        (void) [[NSURLConnection alloc] initWithRequest:request delegate:self];
        
    }
    
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
    
    NSDictionary *resultados = [NSJSONSerialization JSONObjectWithData:self.mutableData options:NSJSONReadingMutableContainers error:nil];
    
    referencia.itens = [[CLMenu alloc]initWithDictionary:resultados].itens;
    
    CLItemDAO *itemDAO = [[CLItemDAO alloc]init];
    
    CLSubItemDAO *subItemDAO = [[CLSubItemDAO alloc]init];
    
    for(CLItem *item in referencia.itens) {
        
        item.menu = referencia;

        [itemDAO inserir:item];
        
        for(CLSubItem *subItem in item.subItens) {
            
            subItem.item = item;
            
            [subItemDAO inserir:subItem];
            
        }
 
    }
    
    if(self.completionHandler) {
        self.completionHandler();
    }
    
}


@end
