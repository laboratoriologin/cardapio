//
//  CLItem.h
//  cardapio
//
//  Created by Julio Rocha on 02/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CLItem.h"
#import "CLMenu.h"
@interface CLItemDAO : NSObject

- (BOOL)inserir:(CLItem *) item;
- (NSMutableArray *)getAllFromMenu:(CLMenu *) menu;

@end
