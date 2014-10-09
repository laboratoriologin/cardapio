//
//  CLSubItem.h
//  cardapio
//
//  Created by Julio Rocha on 02/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CLSubItemDAO.h"
#import "CLSubItem.h"
#import "CLConta.h"
@interface CLSubItemDAO : NSObject

- (BOOL)inserir:(CLSubItem *) subItem;
- (NSMutableArray *)getAllFromItem:(CLItem *) item;
- (NSMutableArray *)getAllPendente;
- (CLSubItem *)get:(CLSubItem *) subitem;
- (BOOL)alterarPedido:(CLSubItem *)subItem;
- (BOOL)incrementarQuantidade:(CLSubItem *)subItem;
- (BOOL)excluirPedido:(CLSubItem *)subItem;
- (BOOL)inserirPedido:(CLSubItem *)subItem;
- (BOOL)marcarSolicitado:(CLSubItem *)subItem;
- (BOOL)clean;
@end
