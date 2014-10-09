//
//  CASobreRestauranteController.m
//  cardapio
//
//  Created by Julio Rocha on 14/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLSobreRestauranteController.h"
#import "CLTitleView.h"
#import "CLConstants.h"
#import <AFNetworking/AFNetworking.h>
@interface CLSobreRestauranteController ()

@end

@implementation CLSobreRestauranteController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [self initNavigationBar];
    
    self.webView.scalesPageToFit = YES;
    
    self.webView.scrollView.bounces = NO;
    
    NSURL *url =[NSURL URLWithString:[NSString stringWithFormat:CLUrlHtml,CLKeyEmpresa]];
    
    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc]initWithRequest:[NSURLRequest requestWithURL:url]];
    
    operation.responseSerializer = [AFJSONResponseSerializer serializer];
    
    [operation setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        NSDictionary *response = responseObject;
        
        NSString *html = [NSString stringWithFormat:@"<html><head/><body>%@<body></html>",[[response objectForKey:@"empresa"]objectForKey:@"dadosEmpresa"]];
        
        [self.webView loadHTMLString:html baseURL:nil];
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        
        
        
    }];
    
    [operation start];

    
}


- (void) initNavigationBar {
    
    CLTitleView *titleView = [[[NSBundle mainBundle] loadNibNamed:@"CLTitleView" owner:nil options:nil] lastObject];
    
    titleView.titulo = @"Sobre o Restaurante";
    
    titleView.imagem = @"bt_menu_sobre_restaurante.png";
    
    [titleView configure];
    
    self.navigationItem.titleView = titleView;
    
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    
}

@end
