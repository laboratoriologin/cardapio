//
//  CLClienteBS.m
//  cardapioBeachStop
//
//  Created by Login Informatica on 20/02/15.
//  Copyright (c) 2015 Login. All rights reserved.
//

#import "CLClienteBS.h"

@interface CLClienteBS() {
    NSMutableArray *resultAux;
}

@end


@implementation CLClienteBS

- (void)insert:(NSMutableArray *) resultParam {
    
    resultAux = resultParam;
    
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:self.parameters] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:10];
    
    (void) [[NSURLConnection alloc] initWithRequest:request delegate:self];
    
    
}


- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
    
    NSDictionary *resultado = [NSJSONSerialization JSONObjectWithData:self.mutableData options:NSJSONReadingMutableContainers error:nil];
    
    NSNumber *retorno = [[resultado objectForKey:@"cliente"]objectForKey:@"id"];
    
    if(retorno) {
        
        [resultAux addObject:retorno];
        
    }
    
    if(self.completionHandler) {
        self.completionHandler();
    }
    
}

@end
