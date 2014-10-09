//
//  CAPedidosController.m
//  cardapio
//
//  Created by Julio Rocha on 14/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLPedidosController.h"
#import "CLTitleView.h"
#import "CLSubItemPedidoView.h"
#import "CLSubItemDAO.h"
#import "UIView+DAHMessages.h"
#import "UIColor+DAHColorUtil.h"
#import "UIFont+DAHFontUtil.h"
#import <AFNetworking/AFNetworking.h>
#import <AudioToolbox/AudioToolbox.h>
#import "CLConstants.h"
@interface CLPedidosController ()
@property CLSubItemPedidoView *subItemView;
@property    NSString *observacao;
@property   CLConta *conta;
@property   NSNumberFormatter *currencyFormatter;


@end

@implementation CLPedidosController


#pragma mark - ViewController methods

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    _currencyFormatter = [[NSNumberFormatter alloc]init];
    
    _currencyFormatter.locale = [NSLocale localeWithLocaleIdentifier:@"pt_BR"];
    
    _currencyFormatter.numberStyle = NSNumberFormatterCurrencyStyle;
    
    [self initNavigationBar];
    
    self.semPedidosLabel.font = [UIFont appFontWithSize:16];
    
    self.nenhumPedidoEnviadoLabel.font = [UIFont appFontWithSize:16];
    
    self.parcialLabel.font = [UIFont appFontWithSize:14];
    
    self.totalLabel.font = [UIFont appFontWithSize:14];
    
    self.switcherLabel.font = [UIFont appFontWithSize:14];
    
    self.groupScrollView.contentSize = CGSizeMake(640, self.groupScrollView.bounds.size.height - 120);
    
    self.groupScrollView.delegate=self;
    
    self.pedidosContaTableView.delegate=self;
    
    self.pedidosContaTableView.dataSource=self;
    
    self.naoEnviadoButton.titleLabel.font = [UIFont appFontWithSize:14];
    
    self.enviadoButton.titleLabel.font = [UIFont appFontWithSize:14];
    
    self.enviarPedidoButton.titleLabel.font = [UIFont appFontWithSize:14];
    
    self.pedirContaButton.titleLabel.font = [UIFont appFontWithSize:14];
    
}

- (void)viewWillAppear:(BOOL)animated {
    
    [super viewWillAppear:animated];
    
    [self initAllPedidos];
    
}

- (void)initAllPedidos {
    
    [self initPedidos];
    
    [self initPedidosConta];
    
}

- (void)viewWillDisappear:(BOOL)animated {
    
    [super viewWillDisappear:animated];

    [_subItemView removeObserver:self forKeyPath:@"valorParcial"];
    
    [[NSNotificationCenter defaultCenter] removeObserver:self name:@"arrayUpdated" object:nil];
    
    _subItemView = nil;
    
}

#pragma mark - init view components

- (void)initNavigationBar {
    
    CLTitleView *titleView = [[[NSBundle mainBundle] loadNibNamed:@"CLTitleView" owner:nil options:nil] lastObject];
    
    titleView.titulo = @"Pedidos";
    
    titleView.imagem = @"bt_menu_pedidos.png";
    
    [titleView configure];
    
    self.navigationItem.titleView = titleView;
    
}

- (void)initPedidos {
    
    if (_subItemView) {
        
        [_subItemView removeObserver:self forKeyPath:@"valorParcial"];
        
        [[NSNotificationCenter defaultCenter] removeObserver:self name:@"arrayUpdated" object:nil];
    
    }
    
    _subItemView = [[CLSubItemPedidoView alloc]initWithFrame:CGRectMake(0, 2, 320, [[[[CLSubItemDAO alloc]init]getAllPendente] count] * 36)];
    
    for(UIView *view in self.scrollView.subviews) {
        [view removeFromSuperview];
    }
    
    [self.scrollView addSubview:_subItemView];
    
    self.scrollView.contentSize = CGSizeMake(320,_subItemView.frame.size.height);
    
    [_subItemView addObserver:self forKeyPath:@"valorParcial" options:NSKeyValueObservingOptionNew context:nil];
    
    [_subItemView configure];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(initPedidos) name:@"arrayUpdated" object:nil];
    
    [self tratarSemPedidoLabel];
    
}

- (void)initPedidosConta {
    
    NSNumber *contaAtual = [[NSUserDefaults standardUserDefaults]objectForKey:CLParamConta];
    
    if (contaAtual > 0) {
        
        self.refreshPedidosEnviadosButton.alpha = 0;
        
        self.contaIndicatorView.alpha=1;
        
        [self.contaIndicatorView startAnimating];
        
        self.nenhumPedidoEnviadoLabel.text=@"Buscando pedidos enviados...";
        
        NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:[CLAppBaseUrl stringByAppendingFormat:@"contas/%d/horarioChegada/mesa/pedidoSubItem/valor/valorPago",contaAtual.intValue]]];
        
        AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
        
        operation.responseSerializer = [AFJSONResponseSerializer serializer];
        
        [operation setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
            
            NSDictionary *response = responseObject;
            
            _conta = [[CLConta alloc]initWithDictionary:response];
            
            [self initGridConta];
            
            self.contaIndicatorView.alpha=0;
            
            [self.contaIndicatorView stopAnimating];
            
        } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
            
            _conta = [[CLConta alloc]init];
            
            [self initGridConta];
            
            self.contaIndicatorView.alpha=0;
            
            [self.contaIndicatorView stopAnimating];
            
            self.nenhumPedidoEnviadoLabel.text = @"Ocorreu um erro ao buscar pedidos enviados";
            
            self.refreshPedidosEnviadosButton.alpha = 1;
            
            self.nenhumPedidoEnviadoLabel.alpha = 1;
            
        }];
        
        [operation start];
        
    } else {
        
        _conta = [[CLConta alloc]init];
        
        [self initGridConta];
        
    }

    
}

- (void)initGridConta {
    
    self.nenhumPedidoEnviadoLabel.alpha=1;
    
    self.nenhumPedidoEnviadoLabel.text = @"Nenhum pedido enviado ao garçom!";    
    
    if ([_conta.subItens count] > 0) {
    
        self.nenhumPedidoEnviadoLabel.alpha=0;
        
    }
    
    [self.pedidosContaTableView reloadData];
    
    [self calcularTotalConta];
    
}

- (void)calcularTotalConta {
    NSNumber *valorTotal = @0;
    
    for(CLSubItem *subItem in _conta.subItens) {
        
        valorTotal = [NSNumber numberWithDouble:[valorTotal doubleValue] +  ([subItem.valor doubleValue] * [subItem.quantidade intValue])];
        
    }
    
    if (self.switcher.on) {
        //calculando os dez por cento
        valorTotal = [NSNumber numberWithDouble:[valorTotal doubleValue] + ([valorTotal doubleValue] * 0.1f)];
        
    }
    
    self.valorContaTextField.text = [_currencyFormatter stringFromNumber:valorTotal];
}

- (void)tratarSemPedidoLabel {
    
    if ([_subItemView.subItens count]==0) {
    
        self.semPedidosLabel.alpha=1;
    
    } else {
        
        self.semPedidosLabel.alpha=0;
        
    }
    
}

#pragma mark - Observer

- (void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary *)change context:(void *)context {
    
    if ([keyPath isEqual:@"valorParcial"]) {
        
        NSNumber *novoValor = [change objectForKey:@"new"];
        
        self.parcialTextField.text = [_currencyFormatter stringFromNumber:novoValor];
        
        [self tratarSemPedidoLabel];
        
    } else {
        
        [self initPedidos];
        
    }
    
}


#pragma mark - UIAlertViewDelegate

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    
    switch (buttonIndex) {
        case 1:
            
            _observacao = [alertView textFieldAtIndex:0].text;
            
            [self continuarEnvioPedido];
            
            break;
            
        default:
            
            _observacao = @"";
            
            break;
    }
    
}

#pragma mark - UITableViewDelegate

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    return [_conta.subItens count];
    
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    UITableViewCell *cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"Cell"];
    
    CLSubItem *subItem = [_conta.subItens objectAtIndex:indexPath.row];
    
    UIView *itemView = [[UIView alloc]initWithFrame:CGRectMake(0, 0, 320, 30)];
    
    UILabel *labelItem = [[UILabel alloc]initWithFrame:CGRectMake(5, 0, 150, 30)];
    
    labelItem.numberOfLines = 2;
    
    labelItem.text = [NSString stringWithFormat:@"%@-%@",subItem.item.nome,subItem.descricao];
    
    labelItem.font = [UIFont appFontWithSize:10];
    
    [itemView addSubview:labelItem];
    
    UILabel *valor = [[UILabel alloc]initWithFrame:CGRectMake(160, 0, 50, 30)];
    
    valor.text = [_currencyFormatter stringFromNumber:subItem.valor];
    
    valor.font = [UIFont appFontWithSize:10];
    
    [itemView addSubview:valor];
    
    UILabel *quantidade = [[UILabel alloc]initWithFrame:CGRectMake(215, 0, 20, 30)];
    
    quantidade.text = [subItem.quantidade stringValue];
    
    quantidade.font = [UIFont appFontWithSize:10];
    
    [itemView addSubview:quantidade];
    
    UILabel *valorTotal = [[UILabel alloc]initWithFrame:CGRectMake(240, 0, 80, 30)];
    
    valorTotal.text = [_currencyFormatter stringFromNumber:[NSNumber numberWithDouble:[subItem.valor doubleValue] * [subItem.quantidade intValue]]];
    
    valorTotal.font = [UIFont appFontWithSize:10];
    
    [itemView addSubview:valorTotal];
    
    [cell addSubview:itemView];
    
    if (indexPath.row%2) {
     
        cell.backgroundColor = [UIColor whiteColor];

    } else {
    
        cell.backgroundColor = [UIColor clearColor];

    }
    
    return cell;
    
}



#pragma mark - Actions

- (IBAction)enviarPedido:(id)sender {
    
    NSNumber *solicitado = [[NSUserDefaults standardUserDefaults]objectForKey:CLParamContaSolicitada];
    
    if (solicitado) {
        
        [self.view addInfoMessage:NSLocalizedString(@"JA_PEDIU_CONTA" , nil) stickTime:2];
        
        return;
        
        
    }

    
    NSPredicate *filter = [NSPredicate predicateWithFormat:@"quantidadeSelecionada > 0",nil];
    
    NSArray *selecionados = [_subItemView.subItens filteredArrayUsingPredicate:filter];
    
    if ([selecionados count] == 0) {
        
        [self.view addInfoMessage:NSLocalizedString(@"ADICIONE_ITEM",nil) stickTime:2];
        
        return;
        
    }
    
    UIAlertView *alertView = [[UIAlertView alloc]initWithTitle:NSLocalizedString(@"ALGUMA_OBSERVACAO",nil) message:nil delegate:self cancelButtonTitle:@"Cancelar" otherButtonTitles:@"Pedir", nil];
    
    alertView.alertViewStyle = UIAlertViewStylePlainTextInput;
    
    [alertView textFieldAtIndex:0].placeholder = NSLocalizedString(@"NENHUMA_OBSERVACAO",nil);
    
    [alertView show];
    
}

- (void)continuarEnvioPedido {
    
    NSPredicate *filter = [NSPredicate predicateWithFormat:@"quantidadeSelecionada > 0",nil];
    
    NSArray *selecionados = [_subItemView.subItens filteredArrayUsingPredicate:filter];
    
    if ([selecionados count] == 0) {
        
        [self.view addInfoMessage:NSLocalizedString(@"ADICIONE_ITEM",nil) stickTime:2];
        
        return;
        
    }
    
    NSNumber *contaAtual = [[NSUserDefaults standardUserDefaults]objectForKey:CLParamConta];
    
    if (contaAtual > 0) {

        NSString *endereco = [CLAppBaseUrl stringByAppendingString:@"pedidos"];
        
        NSString *parameters = [NSString stringWithFormat:@"conta.id=%ld&observacao=%@",contaAtual.longValue,_observacao];
        
        CLSubItem *subItem = nil;
        
        for (int index = 0; index < [selecionados count]; index++) {
            
            subItem = [selecionados objectAtIndex:index];
            
            parameters = [parameters stringByAppendingFormat:@"&listPedidoSubItem[%d].subitem=%ld", index, [subItem.codigo longValue]];
            
            parameters = [parameters stringByAppendingFormat:@"&listPedidoSubItem[%d].quantidade=%d", index, subItem.quantidadeSelecionada];
            
        }
        
        [self enviarServidor:endereco parameters:parameters selecionados:selecionados];
        
    } else {
        
        [self.view addInfoMessage:NSLocalizedString(@"FACA_CHECKIN",nil) stickTime:2];
        
    }

    
}

- (void)enviarServidor:(NSString *)url parameters:(NSString *)parameters selecionados:(NSArray *) selecionados {
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:url]];
    
    [request setHTTPMethod:@"POST"];

    [request setHTTPBody:[parameters dataUsingEncoding:NSUTF8StringEncoding]];
    
    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    
    operation.responseSerializer = [AFJSONResponseSerializer serializer];
    
    [operation setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        [self.indicatorView stopAnimating];
        
        [self.view addInfoMessage:NSLocalizedString(@"OBRIGADO",nil) stickTime:2];
        
        [self.enviarPedidoButton setImage:[UIImage imageNamed:@"icone_chamados_55x42_verde.png"] forState:UIControlStateNormal];
        
        CLSubItemDAO *subItemDAO = [[CLSubItemDAO alloc]init];
        
        for(CLSubItem *subItem in selecionados) {
            
            [subItemDAO marcarSolicitado:subItem];
            
        }
        
        [self initAllPedidos];
        
        self.enviarPedidoButton.enabled=YES;
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        
        [self.view addInfoMessage:NSLocalizedString(@"ACONTECEU_ERRO",nil) stickTime:3];
            
        [self.indicatorView stopAnimating];
        
        [self.enviarPedidoButton setImage:[UIImage imageNamed:@"icone_chamados_55x42_verde.png"] forState:UIControlStateNormal];
        
        self.enviarPedidoButton.enabled=YES;
        
    }];
    
    [self.enviarPedidoButton setImage:[[UIImage alloc]init] forState:UIControlStateNormal];
    
    self.enviarPedidoButton.enabled=NO;
    
    self.indicatorView.alpha=1;
    
    [self.indicatorView startAnimating];
    
    [operation start];
    
}

- (IBAction)showNaoEnviado:(id)sender {
    
    self.naoEnviadoButton.backgroundColor = [UIColor appButtonColor];
    
    self.enviadoButton.backgroundColor = [UIColor appBrownColor];
    
    [self.groupScrollView scrollRectToVisible:CGRectMake(0, 0,self.groupScrollView.frame.size.width, self.groupScrollView.frame.size.height) animated:YES];

}

- (IBAction)showEnviado:(id)sender {
    
    self.enviadoButton.backgroundColor = [UIColor appButtonColor];
    
    self.naoEnviadoButton.backgroundColor = [UIColor appBrownColor];
    
    [self.groupScrollView scrollRectToVisible:CGRectMake(320, 0,self.groupScrollView.frame.size.width, self.groupScrollView.frame.size.height) animated:YES];
    
}

- (IBAction)switched:(id)sender {
    
    [self calcularTotalConta];
}


- (IBAction)refreshPedidosEnviados:(id)sender {
    
    [self initPedidosConta];
    
}

#pragma mark - UIScrollViewDelegate

- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView {
    
    int currentPage = (int) lround(self.groupScrollView.contentOffset.x /
                                      (self.groupScrollView.contentSize.width / 2));
    
    if (currentPage==0) {
        
        self.naoEnviadoButton.backgroundColor = [UIColor appButtonColor];
        
        self.enviadoButton.backgroundColor = [UIColor appBrownColor];
        
    } else {
        
        self.enviadoButton.backgroundColor = [UIColor appButtonColor];
        
        self.naoEnviadoButton.backgroundColor = [UIColor appBrownColor];
        
    }
    
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    
}

@end
