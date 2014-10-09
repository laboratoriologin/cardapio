//
//  CLDBUtil.m
//  cardapio
//
//  Created by Julio Rocha on 02/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLDBUtil.h"
#import "CLAppDelegate.h"
@implementation CLDBUtil

+ (FMDatabase *)getConnection {
    
    NSString *databasePath = [(CLAppDelegate *)[[UIApplication sharedApplication] delegate] databasePath];
    FMDatabase *db = [FMDatabase databaseWithPath:databasePath];
    [db open];
    return db;
    
    
}

+ (void)closeConnection:(FMDatabase *)conexao {
    
    if(conexao!=nil) {
        [conexao close];
    }
}

@end
