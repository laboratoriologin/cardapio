//
//  CLMenuDAO.m
//  cardapio
//
//  Created by Julio Rocha on 02/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLMenuDAO.h"
#import "CLDBUtil.h"
#import "FMDatabase.h"
#import "CLItemDAO.h"
#import "CLSubItemDAO.h"

@implementation CLMenuDAO

- (BOOL) inserir:(CLMenu *) menu {
    
    FMDatabase *conexao = [CLDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno =  [conexao executeUpdate:@"INSERT INTO MENU(CODIGO,DESCRICAO,IMAGEM,IMAGEM_TOPO) values(?, ?, ?, ?)",menu.codigo,menu.descricao,menu.imagem,menu.imagemTopo,nil];
    
    } @catch (NSException *exception) {
        
        return NO;
        
    } @finally {
        
        [CLDBUtil closeConnection:conexao];
        
    }
    
    return retorno;

}

- (CLMenu *) get:(CLMenu *) menu {
    
    FMDatabase *conexao = [CLDBUtil getConnection];
    
    @try {
        
        FMResultSet *results = [conexao executeQuery:@"SELECT * FROM MENU WHERE CODIGO = ?" withArgumentsInArray:@[menu.codigo]];
        
        CLMenu *menu = nil;
        
        if([results next]) {
            
            menu             = [[CLMenu alloc]init];
            menu.codigo      = [NSNumber numberWithInt:[results intForColumn:@"CODIGO"]];
            menu.descricao   = [results stringForColumn:@"DESCRICAO"];
            menu.imagem      = [results stringForColumn:@"IMAGEM"];
            menu.imagemTopo  = [results stringForColumn:@"IMAGEM_TOPO"];
            menu.itens       = [[[CLItemDAO alloc]init]getAllFromMenu:menu];
            
        }
        
        return menu;
        
    }
    @catch (NSException *exception) {
        return nil;
    }
    @finally {
        [CLDBUtil closeConnection:conexao];
    }
    
    
}

- (NSMutableArray *) getAll {
    
    FMDatabase *conexao = [CLDBUtil getConnection];
    
    @try {
        
        FMResultSet *results = [conexao executeQuery:@"SELECT * FROM MENU ORDER BY CODIGO = 0,CODIGO" withArgumentsInArray:nil];
        
        NSMutableArray *menus = [[NSMutableArray alloc]init];
        
        CLMenu *menu = nil;
        
        while([results next]) {
            menu             = [[CLMenu alloc]init];
            menu.codigo      = [NSNumber numberWithInt:[results intForColumn:@"CODIGO"]];
            menu.descricao   = [results stringForColumn:@"DESCRICAO"];
            menu.imagem      = [results stringForColumn:@"IMAGEM"];
            menu.imagemTopo  = [results stringForColumn:@"IMAGEM_TOPO"];
            menu.itens       = [[[CLItemDAO alloc]init]getAllFromMenu:menu];
            [menus addObject:menu];
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

- (void)cleanDataBase {

    FMDatabase *connection = [CLDBUtil getConnection];
    
    @try {
        [connection executeUpdate:@"DELETE FROM SUB_ITEM"];
        [connection executeUpdate:@"DELETE FROM ITEM"];
        [connection executeUpdate:@"DELETE FROM MENU"];
    }
    @catch (NSException *exception) {
    }
    @finally {
        [CLDBUtil closeConnection:connection];
    }
    
    
}


@end
