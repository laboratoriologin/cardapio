//
//  CACardapioBS.h
//  cardapio
//
//  Created by Julio Rocha on 15/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CLHttpRequestBS.h"
@interface CLCardapioBS : CLHttpRequestBS {
    NSMutableArray *resultado;
}

- (void)findCategorias:(NSMutableArray *) result;

@end
