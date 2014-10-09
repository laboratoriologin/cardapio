//
//  CLItensCardapioBS.h
//  cardapio
//
//  Created by Julio Rocha on 25/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CLMenu.h"
#import "CLHttpRequestBS.h"
@interface CLItensCardapioBS : CLHttpRequestBS {
    CLMenu *referencia;
}

- (void)fillMenuWithItens:(CLMenu *)menu;

@end
