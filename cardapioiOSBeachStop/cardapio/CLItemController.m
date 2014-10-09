//
//  CLItemController.m
//  cardapio
//
//  Created by Julio Rocha on 12/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLItemController.h"
#import "UIColor+DAHColorUtil.h"
#import "UIFont+DAHFontUtil.h"
#import <QuartzCore/QuartzCore.h>
#import "CLTitleView.h"
#import "UIView+DAHMessages.h"
#import "CLConstants.h"
const NSInteger CLSectionImagem = 0;
const NSInteger CLSectionDescricao = 1;
const NSInteger CLSectionSubItem = 4;
const NSInteger CLSectionIngrediente = 2;
const NSInteger CLSectionAcompanhamento = 3;

@interface CLItemController ()
@property UIImageView *imageViewReferenceBounce;
@property UILabel *descricaoLabel;
@property UILabel *acompanhamentoLabel;
@property UILabel *ingredientesLabel;
@property CLSubItemDetalheView *subItemView;
@property UIActivityIndicatorView *indicatorImageView;

@end

@implementation CLItemController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
    }
    return self;
}

- (void)viewDidLoad {
    
    [super viewDidLoad];
    
    [self initCellComponents];
    
    [self initImage];
    
    self.tableView.delegate = self;
    
    self.tableView.dataSource = self;
    
    CLTitleView *titleView = [[[NSBundle mainBundle] loadNibNamed:@"CLTitleView" owner:nil options:nil] lastObject];
    
    titleView.titulo = self.item.nome;
    
    titleView.imagem = self.item.menu.imagemTopo;
    
    [titleView configure];
    
    self.navigationItem.titleView = titleView;

    self.navigationController.navigationBar.topItem.title = @"";
    
    [self initShareButton];
    
}

- (void)initShareButton {
    
    UIButton *compartilharButton = [UIButton buttonWithType:UIButtonTypeCustom];
    
    compartilharButton.frame = CGRectMake(0, 0, 25, 25);
    
    [compartilharButton setBackgroundColor:[UIColor clearColor]];
    
    [compartilharButton setImage:[UIImage imageNamed:@"icone_compartilhar"] forState:UIControlStateNormal];
    
    [compartilharButton addTarget:self action:@selector(compartilhar) forControlEvents:UIControlEventTouchUpInside];
    
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc]initWithCustomView:compartilharButton];
    
}

#pragma mark - UITableViewDelegate

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    
    if (section != CLSectionDescricao && section != CLSectionIngrediente && section != CLSectionAcompanhamento) {
        return 0;
    }

    if (section == CLSectionAcompanhamento && [self.item.guarnicoes length]==0) {
        return 0;
    }
    
    if (section == CLSectionIngrediente && [self.item.ingredientes length]==0) {
        return 0;
    }
    
    return 40;
    
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    CGFloat altura = 0;
    
    switch (indexPath.section) {
       
        case CLSectionImagem: {
            
            altura = 200;
        
            break;
       
        }
            
        case CLSectionAcompanhamento: {
            
            if ([self.item.guarnicoes length] == 0) {
                
                altura = 0;
                
            } else {
            
                altura = _acompanhamentoLabel.frame.size.height + 20;
                
            }
            
            break;
        }
        
        case CLSectionIngrediente: {
            
            altura = _ingredientesLabel.frame.size.height + 10;
        
            break;
       
        }
        
        case CLSectionDescricao: {
            
            altura = _descricaoLabel.frame.size.height + 25;
            
            break;
        
        }
            
        case CLSectionSubItem: {
        
            altura = [self.item.subItens count] * 38;
            
            break;
        
        }
            
    }
    
    return altura;
    
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {

    
    if (section != CLSectionIngrediente && section != CLSectionAcompanhamento && section != CLSectionDescricao) {
        return nil;
    }

    
    UIView *headerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, tableView.bounds.size.width, 40)];
    
    headerView.backgroundColor = [UIColor whiteColor];
    
    UILabel *texto = [[UILabel alloc]initWithFrame:CGRectMake(20,3,200,35)];
    
    texto.textColor = [UIColor appHeaderTableViewColor];
    
    texto.backgroundColor = [UIColor whiteColor];
    
    if (section == CLSectionIngrediente) {
        
        texto.text = @"Ingredientes:";
 
    } else if (section ==  CLSectionAcompanhamento) {
   
        texto.text = @"Acompanhamentos:";
    
    } else {
    
        texto.text = @"Sobre o prato:";
  
    }
    
    [texto setFont:[UIFont appFontWithSize:16]];
    
    [headerView addSubview:texto];
    
    if (section == CLSectionIngrediente) {
        
//        UIButton *pedirButton = [UIButton buttonWithType:UIButtonTypeCustom];
//        
//        pedirButton.frame = CGRectMake(135, 3, 80, 26);
//        
//        [pedirButton setTitle:@" Pedir" forState:UIControlStateNormal];
//        
//        pedirButton.titleLabel.font = [UIFont appFontWithSize:12];
//        
//        [pedirButton setBackgroundColor:[UIColor appButtonColor]];
//        
//        [pedirButton setTitleColor:[UIColor appButtonTextColor] forState:UIControlStateNormal];
//        
//        [pedirButton setImage:[UIImage imageNamed:@"icone_mais"] forState:UIControlStateNormal];
//        
//        [pedirButton addTarget:self action:@selector(pedir:) forControlEvents:UIControlEventTouchUpInside];
//        
//        [headerView addSubview:pedirButton];
//        
//        UIButton *incluirButton = [UIButton buttonWithType:UIButtonTypeCustom];
//        
//        incluirButton.frame = CGRectMake(225, 3, 80, 25);
//        
//        [incluirButton setTitle:@" Incluir" forState:UIControlStateNormal];
//        
//        incluirButton.titleLabel.font = [UIFont appFontWithSize:12];
//        
//        [incluirButton setBackgroundColor:[UIColor appButtonColor]];
//        
//        [incluirButton setTitleColor:[UIColor appButtonTextColor] forState:UIControlStateNormal];
//        
//        [incluirButton setImage:[UIImage imageNamed:@"icone_mais"] forState:UIControlStateNormal];
//        
//        [incluirButton addTarget:self action:@selector(incluir:) forControlEvents:UIControlEventTouchUpInside];
//        
//        [headerView addSubview:incluirButton];
        
    }
    
    return headerView;
}

- (void)compartilhar {
    
    NSMutableArray *sharingItems = [NSMutableArray new];
    
    NSString *text = [[CLNomeEmpresa stringByAppendingString:@": "]stringByAppendingString:self.item.nome];
    
    text = [[text stringByAppendingString:@"\n"]stringByAppendingString:self.item.descricao];
    
    [sharingItems addObject:text];
    
    if (self.item.icone) {
    
        [sharingItems addObject:self.item.icone];
        
    }
    
    UIActivityViewController *activityController = [[UIActivityViewController alloc] initWithActivityItems:sharingItems applicationActivities:nil];
    
    [self presentViewController:activityController animated:YES completion:nil];
    
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    
    return 5;

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
        
        case CLSectionAcompanhamento: {
        
            [cell addSubview:_acompanhamentoLabel];
            
            break;
        
        }
        
        case CLSectionIngrediente: {
            
            [cell addSubview:_ingredientesLabel];
            
            break;
        }
            
        case CLSectionDescricao: {
            
            [cell addSubview:_descricaoLabel];
            
            break;
        
        }
            
        case CLSectionSubItem: {
            
            [cell addSubview:_subItemView];
            
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
    
    _descricaoLabel.text = self.item.descricao;
    
    _descricaoLabel.textColor = [UIColor appButtonColor];
    
    _descricaoLabel.numberOfLines = 0;
    
    _descricaoLabel.font = [UIFont appFontWithSize:14];
    
    _descricaoLabel.backgroundColor = [UIColor whiteColor];
    
    [_descricaoLabel sizeToFit];
    
    // inicializando a imagem que fica no topo do tableView
    
    _imageViewReferenceBounce = [[UIImageView alloc]initWithFrame:CGRectMake(0, 0, 320, 200)];
    
    _imageViewReferenceBounce.image = [UIImage imageNamed:@"placeholder"];
    
    _imageViewReferenceBounce.clipsToBounds = YES;
    
    _imageViewReferenceBounce.contentMode = UIViewContentModeScaleAspectFill;
    
    _imageViewReferenceBounce.backgroundColor = [UIColor whiteColor];
    
    _indicatorImageView = [[UIActivityIndicatorView alloc]initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
    
    _indicatorImageView.center = _imageViewReferenceBounce.center;
    
    [_indicatorImageView startAnimating];
    
    _indicatorImageView.hidesWhenStopped = YES;
    
    NSNumber *mesaAtual = [[NSUserDefaults standardUserDefaults]objectForKey:CLParamMesa];

    BOOL configureWithPrice = mesaAtual > 0;
    
    //iniciando a view com os subitens
    _subItemView = [[CLSubItemDetalheView alloc]initWithFrame:CGRectMake(0, 2, 320, [self.item.subItens count] * 36)];
    
    _subItemView.item = self.item;
    
    [_subItemView configure:configureWithPrice];
    
    // iniciando ingredientes label
    
    _ingredientesLabel = [[UILabel alloc]initWithFrame:CGRectMake(10, 5, 290, 0)];
    
    _ingredientesLabel.text = self.item.ingredientes;
    
    _ingredientesLabel.numberOfLines = 0;
    
    _ingredientesLabel.font = [UIFont appFontWithSize:14];
    
    _ingredientesLabel.textColor = [UIColor darkGrayColor];
    
    [_ingredientesLabel sizeToFit];
    
    // iniciando acompanhamentos label
    
    _acompanhamentoLabel = [[UILabel alloc]initWithFrame:CGRectMake(10, 5, 290, 0)];
    
    _acompanhamentoLabel.text = self.item.guarnicoes;
    
    _acompanhamentoLabel.numberOfLines = 0;
    
    _acompanhamentoLabel.font = [UIFont appFontWithSize:14];
    
    _acompanhamentoLabel.textColor = [UIColor darkGrayColor];
    
    [_acompanhamentoLabel sizeToFit];
    
}

- (void)initImage {
    
    if (self.item.icone) {
        
        _imageViewReferenceBounce.image = self.item.icone;
        
        [_indicatorImageView stopAnimating];

    } else if (self.item.imagem.length>0) {
        
        _imageViewReferenceBounce.image = [UIImage imageNamed:@"placeholder.png"];
    
        dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0ul);
        dispatch_async(queue, ^{
            NSURL *url =[NSURL URLWithString:[NSString stringWithFormat:CLUrlImagem, self.item.imagem]];
            UIImage *img = [UIImage imageWithData: [NSData dataWithContentsOfURL:url]];
            
            dispatch_sync(dispatch_get_main_queue(), ^{
                
                [_indicatorImageView stopAnimating];
                
                if(img) {
                    self.item.icone = img;
                    _imageViewReferenceBounce.image = self.item.icone;
                    [_imageViewReferenceBounce setNeedsLayout];
                }
                
            });
        });
        
    } else {
        
         [_indicatorImageView stopAnimating];
        
    }
    
}

#pragma mark - IBActions

- (void)incluir:(UIButton *)sender {
 
    [self incluirPedido];
    
    [self.view addInfoMessage:NSLocalizedString(@"PEDIDOS_INCLUIDOS", nil) stickTime:2];
    
}

- (void)pedir:(UIButton *)sender {
    
    if (![self validatePedido]) {
        return;
    }
    
    [self incluirPedido];
    
    double delayInSeconds = 1.0;
    
    [self.view addInfoMessage:NSLocalizedString(@"DIRECIONANDO_PEDIDOS", nil) stickTime:2];
    
    dispatch_time_t popTime = dispatch_time(DISPATCH_TIME_NOW, delayInSeconds * NSEC_PER_SEC);
    
    dispatch_after(popTime, dispatch_get_main_queue(), ^(void){
        
        self.navigationController.tabBarController.selectedIndex = 2;
        
    });
    
}


- (void)incluirPedido {
    
    if (![self validatePedido]) {
        return;
    }
    
    CLSubItemDAO *subItemDAO = [[CLSubItemDAO alloc]init];
    
    for(CLSubItem *subItem in self.item.subItens) {
        
        if (subItem.quantidadeSelecionada > 0) {
            
            if ([subItemDAO get:subItem] !=nil) {
                
                [subItemDAO incrementarQuantidade:subItem];
                
            } else {
                
                [subItemDAO inserirPedido:subItem];
                
            }
            
        }
        
    }
    
    _subItemView = [[CLSubItemDetalheView alloc]initWithFrame:CGRectMake(0, 2, 320, [self.item.subItens count] * 36)];
    
    _subItemView.item = self.item;
    
    for (CLSubItem *subItem in self.item.subItens) {
        
        subItem.quantidadeSelecionada=0;
        
    }
    
    [_subItemView configure:YES];
    
    [self.tableView reloadData];
    
}

- (BOOL)validatePedido {
    
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"quantidadeSelecionada > 0"];
    
    BOOL retorno = [[self.item.subItens filteredArrayUsingPredicate:predicate]count] > 0;
    
    if(!retorno) {
        [self.view addInfoMessage:NSLocalizedString(@"ADICIONE_ITEM", nil) stickTime:2];
    }
    
    return retorno;
    
}

- (void)didReceiveMemoryWarning {
    
    [super didReceiveMemoryWarning];
    
}


@end
