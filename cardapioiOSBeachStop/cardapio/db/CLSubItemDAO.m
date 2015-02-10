//
//  CLSubItem.m
//  cardapio
//
//  Created by Julio Rocha on 02/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLSubItemDAO.h"
#import "FMDatabase.h"
#import "CLDBUtil.h"

@implementation CLSubItemDAO

- (BOOL) inserir:(CLSubItem *)subItem {
    
    FMDatabase *conexao = [CLDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno = [conexao executeUpdate:@"INSERT INTO SUB_ITEM(CODIGO,NOME,ITEM_ID,QUANTIDADE,DESCRICAO,VALOR,TIPO_DESCRICAO) values(?, ?, ?, ?, ?, ?, ?)",
                    subItem.codigo,subItem.nome,subItem.item.codigo,subItem.quantidade,subItem.descricao,[NSNumber numberWithDouble:[subItem.valor doubleValue]],subItem.tipoDescricao,nil];
        
    } @catch (NSException *exception) {
        
        return NO;
        
    } @finally {
        
        [CLDBUtil closeConnection:conexao];
        
    }
    
    return retorno;
    
}

- (NSMutableArray *) getAllFromItem:(CLItem *)item {
    
    FMDatabase *conexao = [CLDBUtil getConnection];
    
    @try {
        
        FMResultSet *results = [conexao executeQuery:@"SELECT * FROM SUB_ITEM WHERE ITEM_ID = ?" withArgumentsInArray:@[item.codigo]];
        
        NSMutableArray *subItens = [[NSMutableArray alloc]init];
        
        CLSubItem *subItem = nil;
        
        while([results next]) {
            subItem               = [[CLSubItem alloc]init];
            subItem.nome          = [results stringForColumn:@"NOME"];
            subItem.codigo        = [NSNumber numberWithInt:[results intForColumn:@"CODIGO"]];
            subItem.descricao     = [results stringForColumn:@"DESCRICAO"];
            subItem.tipoDescricao = [results stringForColumn:@"TIPO_DESCRICAO"];
            subItem.valor         = [NSNumber numberWithDouble:[results doubleForColumn:@"VALOR"]];;
            subItem.quantidade    = [NSNumber numberWithInt:[results intForColumn:@"QUANTIDADE"]];
            [subItens addObject:subItem];
        }
        
        return subItens;
        
    }
    @catch (NSException *exception) {
        return nil;
    }
    @finally {
        [CLDBUtil closeConnection:conexao];
    }
    
}

#pragma mark - Conta Methods

- (NSMutableArray *)getAllPendente {
    
    FMDatabase *conexao = [CLDBUtil getConnection];
    
    @try {
        
        FMResultSet *results = [conexao executeQuery:@"SELECT I.CODIGO AS CODIGO_ITEM, S.CODIGO, I.NOME AS NOME_ITEM, S.DESCRICAO AS DESCRICAO_SUB_ITEM, PS.QUANTIDADE AS QUANTIDADE, S.VALOR, S.NOME FROM ITEM I, SUB_ITEM S, PEDIDO_SUB_ITEM PS WHERE PS.SUB_ITEM_ID = S.CODIGO AND S.ITEM_ID = I.CODIGO AND PS.FLAG_SOLICITADO = 0" withArgumentsInArray:nil];
        
        NSMutableArray *subitens = [[NSMutableArray alloc]init];
        
        CLSubItem *subItem = nil;
        
        while([results next]) {
            subItem                       = [[CLSubItem alloc]init];
            subItem.item                  = [[CLItem alloc]init];
            subItem.item.codigo           = [NSNumber numberWithInt:[results intForColumnIndex:0]];
            subItem.codigo                = [NSNumber numberWithInt:[results intForColumnIndex:1]];
            subItem.item.nome             = [results stringForColumnIndex:2];
            subItem.descricao             = [results stringForColumnIndex:3];
            subItem.quantidade            = [NSNumber numberWithInt:[results intForColumnIndex:4]];
            subItem.quantidadeSelecionada = [subItem.quantidade intValue];
            subItem.valor                 = [NSNumber numberWithDouble:[results doubleForColumnIndex:5]];
            subItem.nome                  = [results stringForColumnIndex:6];
            [subitens addObject:subItem];
        }
        
        return subitens;
        
    }
    @catch (NSException *exception) {
        return nil;
    }
    @finally {
        [CLDBUtil closeConnection:conexao];
    }
    
}

- (CLSubItem *)get:(CLSubItem *) subitem {
    
    FMDatabase *conexao = [CLDBUtil getConnection];
    
    @try {
        
        FMResultSet *results = [conexao executeQuery:@"SELECT PS.SUB_ITEM_ID FROM PEDIDO_SUB_ITEM PS WHERE PS.SUB_ITEM_ID = ? AND FLAG_SOLICITADO = 0" withArgumentsInArray:@[subitem.codigo]];
     
        CLSubItem *subItem = nil;
        
        if([results next]) {
            subItem                = [[CLSubItem alloc]init];
            subItem.codigo         = [NSNumber numberWithInt:[results intForColumn:@"SUB_ITEM_ID"]];
        }
        
        return subItem;
        
    }
    @catch (NSException *exception) {
        return nil;
    }
    @finally {
        [CLDBUtil closeConnection:conexao];
    }
    
}


- (BOOL)alterarPedido:(CLSubItem *)subItem {
    
    FMDatabase *conexao = [CLDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno =  [conexao executeUpdate:@"UPDATE PEDIDO_SUB_ITEM SET QUANTIDADE = ? WHERE SUB_ITEM_ID = ? AND FLAG_SOLICITADO = 0", [NSNumber numberWithInt:subItem.quantidadeSelecionada],subItem.codigo,nil];
        
    } @catch (NSException *exception) {
        
        return NO;
        
    } @finally {
        
        [CLDBUtil closeConnection:conexao];
        
    }
    
    return retorno;
    
}

- (BOOL)incrementarQuantidade:(CLSubItem *)subItem {
    
    FMDatabase *conexao = [CLDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno =  [conexao executeUpdate:@"UPDATE PEDIDO_SUB_ITEM SET QUANTIDADE = QUANTIDADE + ? WHERE SUB_ITEM_ID = ? AND FLAG_SOLICITADO = 0", [NSNumber numberWithInt:subItem.quantidadeSelecionada],subItem.codigo,nil];
        
    } @catch (NSException *exception) {
        
        return NO;
        
    } @finally {
        
        [CLDBUtil closeConnection:conexao];
        
    }
    
    return retorno;
    
}

- (BOOL)inserirPedido:(CLSubItem *)subItem {
    
    FMDatabase *conexao = [CLDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno = [conexao executeUpdate:@"INSERT INTO PEDIDO_SUB_ITEM(SUB_ITEM_ID, QUANTIDADE, FLAG_SOLICITADO) VALUES(?, ?, 0)",subItem.codigo, [NSNumber numberWithInt:subItem.quantidadeSelecionada],nil];
        
    } @catch (NSException *exception) {
        
        return NO;
        
    } @finally {
        
        [CLDBUtil closeConnection:conexao];
        
    }
    
    return retorno;
    
}

- (BOOL)marcarSolicitado:(CLSubItem *)subItem {
    
    FMDatabase *conexao = [CLDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno = [conexao executeUpdate:@"UPDATE PEDIDO_SUB_ITEM SET FLAG_SOLICITADO = 1 WHERE SUB_ITEM_ID = ?",subItem.codigo,nil];
        
    } @catch (NSException *exception) {
        
        return NO;
        
    } @finally {
        
        [CLDBUtil closeConnection:conexao];
        
    }
    
    return retorno;
    
}


- (BOOL)excluirPedido:(CLSubItem *)subItem {
    
    FMDatabase *conexao = [CLDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno = [conexao executeUpdate:@"DELETE FROM PEDIDO_SUB_ITEM WHERE SUB_ITEM_ID = ? AND FLAG_SOLICITADO = 0",subItem.codigo,nil];
        
    } @catch (NSException *exception) {
        
        return NO;
        
    } @finally {
        
        [CLDBUtil closeConnection:conexao];
        
    }
    
    return retorno;
    
}

- (BOOL)clean {
    
    FMDatabase *conexao = [CLDBUtil getConnection];
    
    BOOL retorno = NO;
    
    @try {
        
        retorno =  [conexao executeUpdate:@"DELETE FROM PEDIDO_SUB_ITEM",nil];
        
    } @catch (NSException *exception) {
        
        return NO;
        
    } @finally {
        
        [CLDBUtil closeConnection:conexao];
        
    }
    
    return retorno;
    
}


@end
