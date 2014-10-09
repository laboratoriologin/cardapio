//
//  CLTodosItensCardapioBS.h
//  cardapio
//
//  Created by Julio Rocha on 05/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CLItem.h"
#import "CLHttpRequestBS.h"
@interface CLTodosItensCardapioBS : CLHttpRequestBS

@property (nonatomic, strong)NSMutableArray * listMenuResultado;

- (void)getMenus:(NSMutableArray *) reference;
@end
