//
//  CATitleView.h
//  cardapio
//
//  Created by Julio Rocha on 14/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CLTitleView : UIView
@property (weak, nonatomic) IBOutlet UILabel *title;
@property (weak, nonatomic) IBOutlet UIImageView *image;


@property(copy,nonatomic) NSString *titulo;
@property(copy,nonatomic) NSString *imagem;

- (void) configure;

@end
