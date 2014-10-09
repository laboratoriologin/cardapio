//
//  CLMenuDAO.h
//  cardapio
//
//  Created by Julio Rocha on 02/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CLMenu.h"
@interface CLMenuDAO : NSObject

- (BOOL)inserir:(CLMenu *) menu;
- (CLMenu *) get:(CLMenu *) menu;
- (NSMutableArray *) getAll;
- (void)cleanDataBase;
@end
