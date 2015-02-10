//
//  CLEmpresaBS.m
//  cardapio
//
//  Created by Julio Rocha on 15/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLEmpresaBS.h"

@interface CLEmpresaBS() {
    NSMutableArray *resultAux;
}

@end

@implementation CLEmpresaBS

- (void)getLastKey:(NSMutableArray *) resultParam {
    
    resultAux = resultParam;
    
    NSString *url = [CLAppBaseUrl stringByAppendingString:@"empresas/1/keyCardapio"];
    
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:url] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:10];
    
    (void) [[NSURLConnection alloc] initWithRequest:request delegate:self];
    
    
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
    
    if (self.mutableData) {
    
        NSDictionary *resultado = [NSJSONSerialization JSONObjectWithData:self.mutableData
                                                                  options:NSJSONReadingMutableContainers error:nil];
        
        if (resultado) {
            
            [resultAux addObject:[[[resultado objectForKey:@"empresa"]objectForKey:@"keyCardapio"]stringValue]];
            
        }
        
    }
    
    
    if(self.completionHandler) {
        self.completionHandler();
    }
    
}

@end
