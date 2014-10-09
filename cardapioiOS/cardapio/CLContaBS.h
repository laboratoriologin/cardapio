//
//  CLContaBS.h
//  cardapio
//
//  Created by Julio Rocha on 19/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CLHttpRequestBS.h"
@interface CLContaBS : CLHttpRequestBS

- (void)open:(NSMutableArray *) resultParam;
- (void)checkUltimaConta:(NSMutableArray *) resultParam;
@property(nonatomic,copy) NSString *parameters;

@end
