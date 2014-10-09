//
//  CLSubItem.m
//  cardapio
//
//  Created by Julio Rocha on 25/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLSubItem.h"

@implementation CLSubItem

- (BOOL)isEqual:(id)object {
    
    return self.codigo == ((CLSubItem *)object).codigo;
    
}

@end
