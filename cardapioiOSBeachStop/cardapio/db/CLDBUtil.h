//
//  CLDBUtil.h
//  cardapio
//
//  Created by Julio Rocha on 02/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "FMDatabase.h"
@interface CLDBUtil : NSObject


+ (FMDatabase *)getConnection;

+ (void)closeConnection:(FMDatabase *)connection;


@end
