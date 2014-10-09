//
//  CLPublicidade.m
//  cardapio
//
//  Created by Julio Rocha on 21/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLPublicidade.h"

@implementation CLPublicidade

- (instancetype)initWithDictionary:(NSDictionary *)dictionary {
    
    self = [super init];
    
    NSDictionary *publicidade = [dictionary objectForKey:@"publicidade"];
    
    if(self) {
        
        self.codigo      = [publicidade objectForKey:@"id"];
        self.nome        = [publicidade objectForKey:@"nome"];
        self.descricao   = [publicidade objectForKey:@"descricao"];
        self.imagem      = [publicidade objectForKey:@"imagem"];
        self.link        = [publicidade objectForKey:@"link"];
        self.preco       = [publicidade objectForKey:@"preco"];
        
    }
    
    return self;
    
}

@end
