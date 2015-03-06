//
//  CLPublicidadeController.m
//  cardapioBeachStop
//
//  Created by Login Informatica on 26/02/15.
//  Copyright (c) 2015 Login. All rights reserved.
//


#import "CLPublicidadeController.h"
#import "UIFont+DAHFontUtil.h"
#import "UIColor+DAHColorUtil.h"
#import "CLConstants.h"
const NSInteger CLSectionImagem = 0;
const NSInteger CLSectionNome = 1;
const NSInteger CLSectionDescricao = 2;

@interface CLPublicidadeController ()

@property UIImageView *imageViewReferenceBounce;
@property UILabel *nomeLabel;
@property UILabel *descricaoLabel;
@property UIActivityIndicatorView *indicatorImageView;

@end

@implementation CLPublicidadeController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self initCellComponents];
    
    [self initImage];
    
    self.tableView.delegate = self;
    
    self.tableView.dataSource = self;
    
}

#pragma mark - UITableViewDelegate


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    CGFloat altura = 0;
    
    switch (indexPath.section) {
            
        case CLSectionImagem: {
            
            altura = 200;
            
            break;
            
        }
            
        case CLSectionNome: {
            
            if ([self.publicidade.nome length] == 0) {
                
                altura = 0;
                
            } else {
                
                altura = _nomeLabel.frame.size.height + 20;
                
            }
            
            break;
        }
            
        case CLSectionDescricao: {
            
            altura = _descricaoLabel.frame.size.height + 10;
            
            break;
            
        }
            
    }
    
    return altura;
    
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    
    return 3;
    
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    return 1;
    
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    UITableViewCell *cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:nil];
    
    cell.backgroundView.backgroundColor = [UIColor whiteColor];
    
    switch (indexPath.section) {
            
        case CLSectionImagem: {
            
            cell.backgroundColor = [UIColor blackColor];
            
            [cell addSubview:_imageViewReferenceBounce];
            
            [cell addSubview:_indicatorImageView];
            
            break;
            
        }
            
        case CLSectionDescricao: {
            
            [cell addSubview:_descricaoLabel];
            
            break;
            
        }
            
        case CLSectionNome: {
            
            [cell addSubview:_nomeLabel];
            
            break;
        }
            
    }
    
    return cell;
    
}

#pragma mark - UISCrollViewDelegate

- (void) scrollViewDidScroll:(UIScrollView *)scrollView {
    
    CGFloat yPos = -scrollView.contentOffset.y;
    
    if (yPos > 0) {
        
        CGRect imgRect = _imageViewReferenceBounce.frame;
        
        imgRect.origin.y = scrollView.contentOffset.y;
        
        imgRect.size.height = 200+yPos;
        
        imgRect.size.width =  320+yPos;
        
        imgRect.origin.x = 0 - yPos / 2;
        
        _imageViewReferenceBounce.frame = imgRect;
        
    }
    
}

#pragma mark Load Components

- (void)initCellComponents {
    
    //inicializando a descricao do item, segunda posicao da tableview
    
    CGRect labelFrame = CGRectMake(15, 0, 270, 0);
    
    _descricaoLabel = [[UILabel alloc]initWithFrame:labelFrame];
    
    _descricaoLabel.text = [self.publicidade.descricao stringByAppendingFormat:@"\n\n %@",self.publicidade.texto];
    
    _descricaoLabel.textColor = [UIColor appButtonColor];
    
    _descricaoLabel.numberOfLines = 0;
    
    _descricaoLabel.font = [UIFont appFontWithSize:14];
    
    _descricaoLabel.backgroundColor = [UIColor whiteColor];
    
    [_descricaoLabel sizeToFit];
    
    // inicializando a imagem que fica no topo do tableView
    
    _imageViewReferenceBounce = [[UIImageView alloc]initWithFrame:CGRectMake(0, 0, 320, 200)];
    
    _imageViewReferenceBounce.image = [UIImage imageNamed:@"placeholder"];
    
    _imageViewReferenceBounce.clipsToBounds = YES;
    
    _imageViewReferenceBounce.contentMode = UIViewContentModeScaleAspectFit;
    
    _imageViewReferenceBounce.backgroundColor = [UIColor whiteColor];
    
    _indicatorImageView = [[UIActivityIndicatorView alloc]initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
    
    _indicatorImageView.center = _imageViewReferenceBounce.center;
    
    [_indicatorImageView startAnimating];
    
    _indicatorImageView.hidesWhenStopped = YES;
    
    // iniciando ingredientes label
    _nomeLabel = [[UILabel alloc]initWithFrame:CGRectMake(10, 5, 290, 0)];
    
    _nomeLabel.text = self.publicidade.nome;
    
    _nomeLabel.numberOfLines = 0;
    
    _nomeLabel.font = [UIFont appFontWithSize:14];
    
    _nomeLabel.textColor = [UIColor darkGrayColor];
    
    [_nomeLabel sizeToFit];
    
}

- (void)initImage {
    
    if (self.publicidade.imagem.length > 0) {
        
        _imageViewReferenceBounce.image = [UIImage imageNamed:@"placeholder.png"];
        
        dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0ul);
        
        dispatch_async(queue, ^{
            
            NSURL *url =[NSURL URLWithString:[NSString stringWithFormat:CLUrlImagem,self.publicidade.imagem]];
            
            UIImage *img = [UIImage imageWithData: [NSData dataWithContentsOfURL:url]];
            
            dispatch_sync(dispatch_get_main_queue(), ^{
                
                [_indicatorImageView stopAnimating];
                
                if(img) {
                    
                    _imageViewReferenceBounce.image = img;
                    
                    [_imageViewReferenceBounce setNeedsLayout];
                    
                }
                
            });
        });
        
    } else {
        
        [_indicatorImageView stopAnimating];
        
    }
    
}

- (void)didReceiveMemoryWarning {
    
    [super didReceiveMemoryWarning];
    
}


@end
