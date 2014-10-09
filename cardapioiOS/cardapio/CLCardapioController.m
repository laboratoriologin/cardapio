//
//  CAViewController.m
//  cardapio
//
//  Created by Julio Rocha on 11/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLCardapioController.h"
#import "CLTitleView.h"
#import "CLCategoriaController.h"
#import "CLMenuDAO.h"
#import "CLPropagandaBS.h"
#import "CLPublicidade.h"
#import "CLConstants.h"
const int CALarguraMenuCardapio  = 70;
const int CAAlturaMenuCardapio   = 70;
const int CAMargemMenuCardapio   = 5;
const CGFloat xMargem = 100;
const CGFloat yMargem = 72;
const CGFloat xSize = 96;
const CGFloat ySize = 79;
const CGFloat CAalturaImagemSlide = 149;
const CGFloat CAlarguraImagemSlide = 320;
@interface CLCardapioController () {
    NSMutableArray *publicidades;
}

@end

@implementation CLCardapioController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [self initNavigationBar];
    
    publicidades = [NSMutableArray array];
    
    self.pageControl.frame=CGRectMake(0,131, _pageControl.frame.size.width, 14);
    
}

- (void)initNavigationBar {
    
    CLTitleView *titleView = [[[NSBundle mainBundle] loadNibNamed:@"CLTitleView" owner:nil options:nil] lastObject];
    
    titleView.titulo = @"Meu Cardápio";
    
    titleView.imagem = @"bt_menu_cardapio.png";
    
    [titleView configure];
    
    self.navigationItem.titleView = titleView;
    
}

-(void)initSlide {
    
    CLPublicidade *publicidade;
    
    for (int i = 0; i < [publicidades count]; i++) {
        
        UIImageView *imageView = [[UIImageView alloc]initWithFrame:
                                  CGRectMake(self.scrollView.frame.size.width * i, 0, 320, 135)];
        
        imageView.image = [UIImage imageNamed:@"placeholder.png"];
        
        publicidade = [publicidades objectAtIndex:i];
        
        dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0ul);
        dispatch_async(queue, ^{
            NSURL *url =[NSURL URLWithString:[NSString stringWithFormat:CLUrlImagem,publicidade.imagem]];
            UIImage *img = [UIImage imageWithData: [NSData dataWithContentsOfURL:url]];
            
            dispatch_sync(dispatch_get_main_queue(), ^{
                
                if(img) {
                    imageView.image = img;
                    [imageView setNeedsLayout];
                }
                
            });
        });
        
        imageView.contentMode = UIViewContentModeScaleAspectFill;
        
        [self.scrollView addSubview:imageView];
        
        
    }
    
    self.scrollView.contentSize = CGSizeMake(self.scrollView.frame.size.width * [publicidades count],82);
    
    self.scrollView.delegate=self;
    
    _pageControl.numberOfPages = [publicidades count];
    
}

//método executado pelo app delegate após ser baixado o cardapio para o celular
- (void)initCardapioWithObjects:(NSMutableArray *) objects {
    
    CGFloat xAxis = 10;
    
    CGFloat yAxis = self.view.center.y - 104;
    
    CGRect posicao = CGRectMake(xAxis, yAxis, xSize, ySize);
    
    int contadorPosicaoTela = 0;
    
    for(CLMenu *menu in objects) {
        
        contadorPosicaoTela++;
        
        UIButton *button = [[UIButton alloc]initWithFrame:posicao];
        
        button.tag = [[menu codigo] integerValue];
        
        [button setBackgroundImage:[UIImage imageNamed:menu.imagem] forState:UIControlStateNormal];
        
        [button addTarget:self action:@selector(goCategoria:) forControlEvents:UIControlEventTouchUpInside];
        
        [self.view addSubview:button];
        
        if(contadorPosicaoTela % 3==0) {
            
            xAxis = 10;
            
            yAxis = yAxis + yMargem;
            
        } else {
            
            xAxis = xAxis + xMargem;
            
        }
        
        posicao = CGRectMake(xAxis, yAxis, xSize, ySize);
        
    }
    
    CLPropagandaBS *propagandaBS = [[CLPropagandaBS alloc]init];
    
    [propagandaBS setCompletionHandler:^ {
        [self initSlide];
    }];
    
    [propagandaBS getPublicidades:publicidades];
    
}

- (void) goCategoria:(UIButton *) sender {
    
    CLCategoriaController *categoriaController = [[CLCategoriaController alloc]init];
    
    CLMenu *menu = [[CLMenu alloc]initWithID:[NSNumber numberWithInteger:sender.tag]];
    
    categoriaController.menu = menu;
    
    if(menu.codigo.intValue != CAMTodosPratos) {
    
        categoriaController.menu = [[[CLMenuDAO alloc]init]get:menu];
        
    }
    
    [self.navigationController pushViewController:categoriaController animated:YES];
    
    
}

#pragma mark UIScrollView Delegate

- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView {
    
    self.pageControl.currentPage = lround(self.scrollView.contentOffset.x /
                                      (self.scrollView.contentSize.width / self.pageControl.numberOfPages));
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

@end
