//
//  CAAppDelegate.h
//  cardapio
//
//  Created by Julio Rocha on 11/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CLAppDelegate : UIResponder <UIApplicationDelegate>

@property(strong, nonatomic) UIWindow *window;

@property(strong, nonatomic) NSMutableArray *categoriasCardapio;

@property(strong, nonatomic) UILabel *configAppLabel;

@property(strong, nonatomic) UIActivityIndicatorView *indicador;

@property(strong, nonatomic) UIImageView *splashImage;
@property(strong, nonatomic) UIView *backgroundView;

@property(strong, nonatomic) NSString *databaseName;
@property(strong, nonatomic) NSString *databasePath;

@property(strong, nonatomic)  UITabBarController *tabBarController;

@end
