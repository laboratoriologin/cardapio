//
//  CLCollectionViewSingleCell.h
//  cardapio
//
//  Created by Julio Rocha on 29/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CLSubItemView.h"
@interface CLCollectionViewSingleCell : UICollectionViewCell
@property (weak, nonatomic) IBOutlet UIImageView *imageView;
@property (weak, nonatomic) IBOutlet UILabel *nomeLabel;
@property (weak, nonatomic) IBOutlet UILabel *ingredienteLabel;
@property (weak, nonatomic) IBOutlet CLSubItemView *subItensView;
@property (nonatomic, assign) CGPoint imageOffset;
@property (weak, nonatomic) IBOutlet UIButton *detalhesButton;

@end
