//
//  CLEmpresaBS.h
//  cardapio
//
//  Created by Julio Rocha on 15/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CLHttpRequestBS.h"
@interface CLEmpresaBS :  CLHttpRequestBS

- (void)getLastKey:(NSMutableArray *) resultParam;

@end
