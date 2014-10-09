//
//  CLItem.m
//  cardapio
//
//  Created by Julio Rocha on 02/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLItemDAO.h"
#import "FMDatabase.h"
#import "CLDBUtil.h"
#import "CLSubItemDAO.h"

@implementation CLItemDAO

- (BOOL)inserir:(CLItem *)item {
    
    FMDatabase *conexao = [CLDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno =  [conexao executeUpdate:@"INSERT INTO ITEM(CODIGO,MENU_ID,NOME,DESCRICAO,IMAGEM,INGREDIENTES,GUARNICOES) values(?, ?, ?, ?, ?, ?, ?)",
                    item.codigo,item.menu.codigo,item.nome,item.descricao,item.imagem,item.ingredientes,item.guarnicoes,nil];
        
    } @catch (NSException *exception) {
        
        return NO;
        
    } @finally {
        
        [CLDBUtil closeConnection:conexao];
        
    }
    
    return retorno;
    
}

- (NSMutableArray *)getAllFromMenu:(CLMenu *) menu {
    
    FMDatabase *conexao = [CLDBUtil getConnection];
    
    @try {
        
        FMResultSet *results = [conexao executeQuery:@"SELECT * FROM ITEM WHERE MENU_ID = ?" withArgumentsInArray:@[menu.codigo]];
        
        NSMutableArray *menus = [[NSMutableArray alloc]init];
        
        CLItem *item = nil;
        
        CLSubItemDAO *subItemDAO = [[CLSubItemDAO alloc]init];
        
        while([results next]) {
            item              = [[CLItem alloc]init];
            item.codigo       = [NSNumber numberWithInt:[results intForColumn:@"CODIGO"]];
            item.nome         = [results stringForColumn:@"NOME"];
            item.descricao    = [results stringForColumn:@"DESCRICAO"];
            item.imagem       = [results stringForColumn:@"IMAGEM"];
            item.guarnicoes   = [results stringForColumn:@"GUARNICOES"];
            item.ingredientes = [results stringForColumn:@"INGREDIENTES"];
            item.menu         = menu;
            item.subItens     = [subItemDAO getAllFromItem:item];
            [menus addObject:item];
        }
        
        return menus;
        
    }
    @catch (NSException *exception) {
        return nil;
    }
    @finally {
        [CLDBUtil closeConnection:conexao];
    }
    
}



@end
