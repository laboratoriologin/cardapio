//
//  CLSubItemView.h
//  cardapio
//
//  Created by Julio Rocha on 29/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CLSubItem.h"
@interface CLSubItemView : UIView

@property(nonatomic,strong) CLItem *item;

- (void) configure;

@end
