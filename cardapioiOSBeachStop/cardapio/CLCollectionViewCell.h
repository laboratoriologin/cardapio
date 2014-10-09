//
//  CLCollectionViewCell.h
//  cardapio
//
//  Created by Julio Rocha on 29/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CLCollectionViewCell : UICollectionViewCell
@property (weak, nonatomic) IBOutlet UIImageView *imageView;
@property (weak, nonatomic) IBOutlet UILabel *tituloLabel;

@property (weak, nonatomic) IBOutlet UILabel *descricaoLabel;

@property (nonatomic, assign) CGPoint imageOffset;

@end
