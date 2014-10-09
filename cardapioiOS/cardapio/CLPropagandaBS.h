//
//  CLPropagandaBS.h
//  cardapio
//
//  Created by Julio Rocha on 21/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CLHttpRequestBS.h"
@interface CLPropagandaBS : CLHttpRequestBS 

- (void)getPublicidades:(NSMutableArray *) reference;

@end
