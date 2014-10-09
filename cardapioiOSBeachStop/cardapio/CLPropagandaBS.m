//
//  CLPropagandaBS.m
//  cardapio
//
//  Created by Julio Rocha on 21/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLPropagandaBS.h"
#import "CLPublicidade.h"
@interface CLPropagandaBS() {
    NSMutableArray *resultado;
}

@end


@implementation CLPropagandaBS

- (void)getPublicidades:(NSMutableArray *) reference {
    
    resultado = reference;
    
    NSString *url = [CLAppBaseUrl stringByAppendingFormat:@"publicidades/keymobile/%@", CLKeyEmpresa];
    
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:url] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:10];
    
    (void) [[NSURLConnection alloc] initWithRequest:request delegate:self];
    
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
    
    NSDictionary *jsonResultado = [NSJSONSerialization JSONObjectWithData:self.mutableData options:NSJSONReadingMutableContainers error:nil];
    
    if (resultado && [jsonResultado count] > 0 ) {
        
        for(NSDictionary *publicidade in jsonResultado) {
            
            [resultado addObject:[[CLPublicidade alloc]initWithDictionary:publicidade]];
            
        }
        
    }
    
    if(self.completionHandler) {
        self.completionHandler();
    }
    
}

@end
