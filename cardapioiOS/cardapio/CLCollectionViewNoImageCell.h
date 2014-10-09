//
//  CLCollectionViewNoImageCell.h
//  cardapio
//
//  Created by Julio Rocha on 07/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CLSubItemView.h"
@interface CLCollectionViewNoImageCell : UICollectionViewCell
@property (weak, nonatomic) IBOutlet UILabel *titulo;
@property (weak, nonatomic) IBOutlet CLSubItemView *subItensView;

@end
