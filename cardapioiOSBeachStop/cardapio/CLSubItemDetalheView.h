//
//  CLSubItemDetalheView.h
//  cardapio
//
//  Created by Julio Rocha on 08/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "CLSubItemDAO.h"
@interface CLSubItemDetalheView : UIView

@property(nonatomic,strong) CLItem *item;

- (void)configure:(BOOL)price;

@end
