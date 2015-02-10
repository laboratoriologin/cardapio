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
#import "CLNoTextTitleView.h"
#import <Social/Social.h>
#import <SDWebImage/SDWebImageManager.h>
#define IS_IPHONE_5 ( fabs( ( double )[ [ UIScreen mainScreen ] bounds ].size.height - ( double )568 ) < DBL_EPSILON )
const int CALarguraMenuCardapio  = 70;
const int CAAlturaMenuCardapio   = 70;
const int CAMargemMenuCardapio   = 5;
const CGFloat xMargem = 100;

const CGFloat xSize = 96;
const CGFloat ySize = 79;
const CGFloat CAalturaImagemSlide = 149;
const CGFloat CAlarguraImagemSlide = 320;
@interface CLCardapioController ()

@property NSMutableArray *publicidades;


@end

@implementation CLCardapioController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [self initNavigationBar];
    
    _publicidades = [NSMutableArray array];
    
    int posicao = 115;
    
    if (IS_IPHONE_5) {
        
        posicao = posicao + 30;
        
    }
    
    self.pageControl.frame=CGRectMake(0, posicao, CGRectGetWidth(self.pageControl.frame), 16);
    
    self.pageControl.backgroundColor = [UIColor clearColor];
    
}

- (void)initNavigationBar {
    
    CLNoTextTitleView *titleView = [[[NSBundle mainBundle] loadNibNamed:@"CLNoTextTitleView" owner:nil options:nil] lastObject];
    
    self.navigationItem.titleView = titleView;
    
}

- (void)initSlide {
    
    self.pageControl.alpha = 0;
    
    CLPublicidade *publicidade;
    
    int altura = 135;
    
    if (IS_IPHONE_5) {
        
        altura = altura + 30;
        
    }
    
    for (int i = 0; i < [_publicidades count]; i++) {
        
        UIImageView *imageView = [[UIImageView alloc]initWithFrame:CGRectMake(self.view.frame.size.width * i, 0, 320, altura)];
        
        imageView.image = [UIImage imageNamed:@"placeholder"];
        
        publicidade = [_publicidades objectAtIndex:i];
        
        dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0ul);
      
        dispatch_async(queue, ^{
          
            NSURL *url =[NSURL URLWithString:[NSString stringWithFormat:CLUrlImagem,publicidade.imagem]];
            
            UIImage *img = [UIImage imageWithData: [NSData dataWithContentsOfURL:url]];
            
            dispatch_sync(dispatch_get_main_queue(), ^{
                
                if(img) {
                    _pageControl.alpha = 1;
                    imageView.image = img;
                    imageView.contentMode = UIViewContentModeScaleAspectFit;
                    [imageView setNeedsLayout];
                }
                
            });
        });
        
        [self.scrollView addSubview:imageView];
        
        
    }
    
    self.scrollView.contentSize = CGSizeMake(self.scrollView.frame.size.width * [_publicidades count], 82);
    
    self.scrollView.delegate=self;
    
    _pageControl.numberOfPages = [_publicidades count];
    
}

//método executado pelo app delegate após ser baixado o cardapio para o celular
- (void)initCardapioWithObjects:(NSMutableArray *) objects {
    
    CGFloat xAxis = 10;
    
    CGFloat yAxis = self.view.center.y - 104;
    
    CGRect posicao = CGRectMake(xAxis, yAxis, xSize, ySize);
    
    int contadorPosicaoTela = 0;
    
    CGFloat yMargem = 73;
    
    if (IS_IPHONE_5) {
        yMargem = 80;
    }
    
    for(CLMenu *menu in objects) {
        
        contadorPosicaoTela++;
        
        UIButton *button = [[UIButton alloc]initWithFrame:posicao];
        
        button.tag = [[menu codigo] integerValue];
        
       [[button imageView] setContentMode: UIViewContentModeScaleAspectFit];
        
        dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0ul);
        
        dispatch_async(queue, ^{
            
            NSURL *url =[NSURL URLWithString:[NSString stringWithFormat:CLUrlImagem,menu.imagem]];
            
            UIImage *img = [UIImage imageWithData: [NSData dataWithContentsOfURL:url]];
            
            dispatch_sync(dispatch_get_main_queue(), ^{
                
                if(img) {
                    [button setImage:img forState:UIControlStateNormal];
                    [button setImage:img forState:UIControlStateHighlighted];
                }
                
            });
        });
        
        
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
    
    [propagandaBS getPublicidades: _publicidades];
    
}

- (void)goCategoria:(UIButton *) sender {
    
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
