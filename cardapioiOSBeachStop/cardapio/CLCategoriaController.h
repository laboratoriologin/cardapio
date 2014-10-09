//
//  CACategoriaController.h
//  cardapio
//
//  Created by Julio Rocha on 16/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CLItem.h"
@interface CLCategoriaController : UIViewController <UICollectionViewDataSource,UICollectionViewDelegate>

@property(nonatomic,strong) CLMenu *menu;
@property(weak, nonatomic) IBOutlet UICollectionView *collectionView;
@property(nonatomic) NSMutableDictionary *imageDownloadsInProgress;
@end
