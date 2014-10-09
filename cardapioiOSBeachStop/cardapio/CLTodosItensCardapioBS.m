//
//  CLTodosItensCardapioBS.m
//  cardapio
//
//  Created by Julio Rocha on 05/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLTodosItensCardapioBS.h"
#import "CLItemDAO.h"
#import "CLMenuDAO.h"
#import "CLSubItemDAO.h"
@implementation CLTodosItensCardapioBS

- (void)getMenus:(NSMutableArray *) reference {
    
    self.listMenuResultado = reference;
    
    NSMutableArray *menus = [[[CLMenuDAO alloc]init]getAll];
    
    NSMutableArray *pendentes = [NSMutableArray arrayWithCapacity:0];
    
    for(CLMenu *menu in menus) {

        if(menu.itens.count>0) {
        
            [self.listMenuResultado addObject:menu];
        
        } else {
        
            [pendentes addObject:menu];
        
        }
        
    }
    
    if([pendentes count]==0) {
        
        
        if(self.completionHandler) {
            self.completionHandler();
        }
        
    } else {
        
        NSString *url = [CLAppBaseUrl stringByAppendingFormat:@"itens/keymobile/%@/categorias/", CLKeyEmpresa];
        
        for(CLMenu *pendente in pendentes) {
            
            url = [url stringByAppendingFormat:@"%@,",[pendente.codigo stringValue]];
            
        }
        
        NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:url] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:10];
        
        (void) [[NSURLConnection alloc] initWithRequest:request delegate:self];
        
    }
    
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
    
    NSDictionary *resultados = [NSJSONSerialization JSONObjectWithData:self.mutableData options:NSJSONReadingMutableContainers error:nil];
    
    NSMutableArray *itens = [[[CLMenu alloc]initWithDictionary:resultados] itens];
    
    CLItemDAO *itemDAO = [[CLItemDAO alloc]init];
    
    CLSubItemDAO *subItemDAO = [[CLSubItemDAO alloc]init];
    
    for(CLItem *item in itens) {
        
        [itemDAO inserir:item];
        
        for(CLSubItem *subItem in item.subItens) {
            
            subItem.item = item;
            
            [subItemDAO inserir:subItem];
            
        }
        
    }
    
    [self.listMenuResultado removeAllObjects];
    
    for(CLMenu *menu in [[[CLMenuDAO alloc]init]getAll]) {
        
        [self.listMenuResultado addObject:menu];
        
    }
    
    if(self.completionHandler) {
        self.completionHandler();
    }
    
}



@end
