//
//  CLContaBS.m
//  cardapio
//
//  Created by Julio Rocha on 19/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLContaBS.h"

@interface CLContaBS() {
    NSMutableArray *resultAux;
    BOOL checkUltimaConta;
}

@end

@implementation CLContaBS

- (void)open:(NSMutableArray *) resultParam {
    
    resultAux = resultParam;
    
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:self.parameters] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:10];
    
    (void) [[NSURLConnection alloc] initWithRequest:request delegate:self];
    
    
}

- (void)checkUltimaConta:(NSMutableArray *) resultParam {

    resultAux = resultParam;
    
    NSNumber *contaAtual = [[NSUserDefaults standardUserDefaults]objectForKey:@"conta"];
    
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:[CLAppBaseUrl stringByAppendingFormat:@"contas/%d/flagAberto",contaAtual.intValue]] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:10];
    
    checkUltimaConta = YES;
    
    (void) [[NSURLConnection alloc] initWithRequest:request delegate:self];
    
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
    
    NSDictionary *resultado = [NSJSONSerialization JSONObjectWithData:self.mutableData options:NSJSONReadingMutableContainers error:nil];
    
    NSNumber *retorno = [[resultado objectForKey:@"conta"]objectForKey:@"flagAberto"];
    
    if(retorno) {
        
        [resultAux addObject:retorno];
        
    }
    
    if(self.completionHandler) {
        self.completionHandler();
    }
    
}

@end
