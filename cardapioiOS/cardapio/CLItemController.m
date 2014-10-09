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
const NSInteger CLSectionSubItem = 2;
const NSInteger CLSectionIngrediente = 3;
const NSInteger CLSectionAcompanhamento = 4;

@interface CLItemController () {
    UIImageView *imageViewReferenceBounce;
    UIView *descricaoView;
    UILabel *acompanhamentoLabel;
    UILabel *ingredientesLabel;
    CLSubItemDetalheView *subItemView;
    UIActivityIndicatorView *indicatorImageView;
}

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
    
    self.tableView.delegate = self;
    
    self.tableView.dataSource = self;
    
    CLTitleView *titleView = [[[NSBundle mainBundle] loadNibNamed:@"CLTitleView" owner:nil options:nil] lastObject];
    
    titleView.titulo = self.item.nome;
    
    titleView.imagem = self.item.menu.imagemTopo;
    
    [titleView configure];
    
    self.navigationItem.titleView = titleView;

    self.navigationController.navigationBar.topItem.title = @"";
    
    [self initCellComponents];
    
    [self initImage];
    
}

#pragma mark - UITableViewDelegate

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    
    if (section != CLSectionIngrediente && section != CLSectionAcompanhamento) {
        return 0;
    }

    if (section == CLSectionAcompanhamento && [self.item.guarnicoes length]==0) {
        return 0;
    }
    
    return 50;
    
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
            
                altura = acompanhamentoLabel.frame.size.height + 20;
                
            }
            
            break;
        }
        
        case CLSectionIngrediente: {
            altura = ingredientesLabel.frame.size.height + 10;
            break;
        }
        
        case CLSectionDescricao: {
            altura = descricaoView.frame.size.height + 25;
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

    
    if (section != CLSectionIngrediente && section != CLSectionAcompanhamento) {
        return nil;
    }

    
    UIView *headerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, tableView.bounds.size.width, 40)];
    
    [headerView setBackgroundColor:[UIColor whiteColor]];
    
    UILabel *texto = [[UILabel alloc]initWithFrame:CGRectMake(20,3,200,35)];
    
    [texto setTextColor:[UIColor colorFromHexString:@"#56852F"]];
    
    texto.text = section == CLSectionIngrediente?@"Ingredientes:":@"Acompanhamentos:";
    
    [texto setFont:[UIFont appFontWithSize:16]];
    
    [headerView addSubview:texto];
    
    if (section == CLSectionIngrediente) {
        
        UIButton *pedirButton = [UIButton buttonWithType:UIButtonTypeCustom];
        
        pedirButton.frame = CGRectMake(135, 3, 80, 26);
        
        [pedirButton setTitle:@" Pedir" forState:UIControlStateNormal];
        
        pedirButton.titleLabel.font = [UIFont appFontWithSize:12];
        
        [pedirButton setBackgroundColor:[UIColor colorFromHexString:@"#83C900"]];
        
        [pedirButton setTitleColor:[UIColor colorFromHexString:@"#385600"] forState:UIControlStateNormal];
        
        [pedirButton setImage:[UIImage imageNamed:@"icone_mais"] forState:UIControlStateNormal];
        
        [pedirButton addTarget:self action:@selector(pedir:) forControlEvents:UIControlEventTouchUpInside];
        
        [headerView addSubview:pedirButton];
        
        UIButton *incluirButton = [UIButton buttonWithType:UIButtonTypeCustom];
        
        incluirButton.frame = CGRectMake(225, 3, 80, 25);
        
        [incluirButton setTitle:@" Incluir" forState:UIControlStateNormal];
        
        incluirButton.titleLabel.font = [UIFont appFontWithSize:12];
        
        [incluirButton setBackgroundColor:[UIColor colorFromHexString:@"#83C900"]];
        
        [incluirButton setTitleColor:[UIColor colorFromHexString:@"#385600"] forState:UIControlStateNormal];
        
        [incluirButton setImage:[UIImage imageNamed:@"icone_mais"] forState:UIControlStateNormal];
        
        [incluirButton addTarget:self action:@selector(incluir:) forControlEvents:UIControlEventTouchUpInside];
        
        [headerView addSubview:incluirButton];
        
    }
    
    return headerView;
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    
    return 5;

}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    return 1;
    
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    UITableViewCell *cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:nil];
    
    switch (indexPath.section) {
       
        case CLSectionImagem : {
            
            cell.backgroundColor = [UIColor blackColor];
            
            [cell addSubview:imageViewReferenceBounce];
            
            [cell addSubview:indicatorImageView];
         
            break;
            
        }
        
        case CLSectionAcompanhamento: {
        
            [cell addSubview:acompanhamentoLabel];
            
            break;
        
        }
        
        case CLSectionIngrediente: {
            
            [cell addSubview:ingredientesLabel];
            
            break;
        }
            
        case CLSectionDescricao: {
            
            [cell addSubview:descricaoView];
            
            break;
        
        }
            
        case CLSectionSubItem: {
            
            [cell addSubview:subItemView];
            
            break;
            
        }

    }
    
    cell.textLabel.numberOfLines = 0;
    
    cell.textLabel.font = [UIFont appFontWithSize:14];
    
    [cell.textLabel sizeToFit];
    
    return cell;
    
}

#pragma mark - UISCrollViewDelegate

- (void) scrollViewDidScroll:(UIScrollView *)scrollView {

    CGFloat yPos = -scrollView.contentOffset.y;
    
    if (yPos > 0) {
    
        CGRect imgRect = imageViewReferenceBounce.frame;
        
        imgRect.origin.y = scrollView.contentOffset.y;
        
        imgRect.size.height = 200+yPos;
        
        imageViewReferenceBounce.frame = imgRect;
        
    }
    
}

#pragma mark Load Components

- (void)initCellComponents {
    
    //inicializando a descricao do item, segunda posicao da tableview
    
    CGRect labelFrame = CGRectMake(15, 10, 265, 0);
    
    UILabel *descricaoLabel = [[UILabel alloc]initWithFrame:labelFrame];
    
    descricaoLabel.text = self.item.descricao;
    
    descricaoLabel.textColor = [UIColor colorFromHexString:@"#83C900"];
    
    descricaoLabel.numberOfLines = 0;
    
    descricaoLabel.font = [UIFont appFontWithSize:14];
    
    [descricaoLabel sizeToFit];
    
    labelFrame = CGRectMake(15, 5, 290, descricaoLabel.frame.size.height + 15);
    
    descricaoView = [[UIView alloc]initWithFrame:labelFrame];
    
    descricaoView.layer.cornerRadius = 4;
    
    descricaoView.layer.masksToBounds = YES;
    
    descricaoView.backgroundColor = [UIColor colorFromHexString:@"F2DBB3"];
    
    [descricaoView addSubview:descricaoLabel];
    
    // inicializando a imagem que fica no topo do tableView
    
    imageViewReferenceBounce = [[UIImageView alloc]initWithFrame:CGRectMake(0, 0, 320, 200)];
    
    imageViewReferenceBounce.image = [UIImage imageNamed:@"placeholder.png"];
    
    imageViewReferenceBounce.clipsToBounds = NO;
    
    imageViewReferenceBounce.contentMode = UIViewContentModeScaleAspectFit;
    
    imageViewReferenceBounce.backgroundColor = [UIColor whiteColor];
    
    indicatorImageView = [[UIActivityIndicatorView alloc]initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
    
    indicatorImageView.center = imageViewReferenceBounce.center;
    
    [indicatorImageView startAnimating];
    
    indicatorImageView.hidesWhenStopped = YES;
    
    //iniciando a view com os subitens
    subItemView = [[CLSubItemDetalheView alloc]initWithFrame:CGRectMake(0, 2, 320, [self.item.subItens count] * 36)];
    
    subItemView.item = self.item;
    
    [subItemView configure];
    
    // iniciando ingredientes label
    
    ingredientesLabel = [[UILabel alloc]initWithFrame:CGRectMake(10, 5, 290, 0)];
    
    ingredientesLabel.text = self.item.ingredientes;
    
    ingredientesLabel.numberOfLines = 0;
    
    ingredientesLabel.font = [UIFont appFontWithSize:14];
    
    [ingredientesLabel sizeToFit];

    // iniciando ingredientes label
    
    acompanhamentoLabel = [[UILabel alloc]initWithFrame:CGRectMake(10, 5, 290, 0)];
    
    acompanhamentoLabel.text = self.item.guarnicoes;
    
    acompanhamentoLabel.numberOfLines = 0;
    
    acompanhamentoLabel.font = [UIFont appFontWithSize:14];
    
    [acompanhamentoLabel sizeToFit];
    
}

- (void)initImage {
    
    if (self.item.icone) {
        
        imageViewReferenceBounce.image = self.item.icone;
        
        [indicatorImageView stopAnimating];

    } else if (self.item.imagem.length>0) {
        
        imageViewReferenceBounce.image = [UIImage imageNamed:@"placeholder.png"];
    
        dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0ul);
        dispatch_async(queue, ^{
            NSURL *url =[NSURL URLWithString:[NSString stringWithFormat:CLUrlImagem, self.item.imagem]];
            UIImage *img = [UIImage imageWithData: [NSData dataWithContentsOfURL:url]];
            
            dispatch_sync(dispatch_get_main_queue(), ^{
                
                [indicatorImageView stopAnimating];
                
                if(img) {
                    self.item.icone = img;
                    imageViewReferenceBounce.image = self.item.icone;
                    [imageViewReferenceBounce setNeedsLayout];
                }
                
            });
        });
        
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
    
    subItemView = [[CLSubItemDetalheView alloc]initWithFrame:CGRectMake(0, 2, 320, [self.item.subItens count] * 36)];
    
    subItemView.item = self.item;
    
    for (CLSubItem *subItem in self.item.subItens) {
        
        subItem.quantidadeSelecionada=0;
        
    }
    
    [subItemView configure];
    
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
