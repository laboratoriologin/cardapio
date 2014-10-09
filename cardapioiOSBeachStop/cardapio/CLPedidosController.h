//
//  CAPedidosController.h
//  cardapio
//
//  Created by Julio Rocha on 14/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CLPedidosController : UIViewController <UIAlertViewDelegate,UIScrollViewDelegate,UITableViewDataSource,UITableViewDelegate>
@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;
@property (weak, nonatomic) IBOutlet UITextField *parcialTextField;
- (IBAction)enviarPedido:(id)sender;
@property (weak, nonatomic) IBOutlet UIActivityIndicatorView *indicatorView;
@property (weak, nonatomic) IBOutlet UIButton *enviarPedidoButton;
@property (weak, nonatomic) IBOutlet UIScrollView *groupScrollView;
@property (weak, nonatomic) IBOutlet UIButton *naoEnviadoButton;
@property (weak, nonatomic) IBOutlet UIButton *enviadoButton;
@property (weak, nonatomic) IBOutlet UISwitch *switcher;
@property (weak, nonatomic) IBOutlet UITextField *valorContaTextField;
@property (weak, nonatomic) IBOutlet UILabel *nenhumPedidoEnviadoLabel;
@property (weak, nonatomic) IBOutlet UILabel *switcherLabel;
@property (weak, nonatomic) IBOutlet UILabel *totalLabel;
@property (weak, nonatomic) IBOutlet UILabel *parcialLabel;
@property (weak, nonatomic) IBOutlet UILabel *semPedidosLabel;
@property (weak, nonatomic) IBOutlet UITableView *pedidosContaTableView;
- (IBAction)showNaoEnviado:(id)sender;
- (IBAction)showEnviado:(id)sender;
- (IBAction)switched:(id)sender;
@property (weak, nonatomic) IBOutlet UIActivityIndicatorView *contaIndicatorView;
@property (weak, nonatomic) IBOutlet UIButton *refreshPedidosEnviadosButton;
- (IBAction)refreshPedidosEnviados:(id)sender;
@property (weak, nonatomic) IBOutlet UIButton *pedirContaButton;

@end
