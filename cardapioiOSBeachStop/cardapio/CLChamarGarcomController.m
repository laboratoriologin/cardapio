//
//  CLChamarGarcomController.m
//  cardapio
//
//  Created by Julio Rocha on 18/06/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLChamarGarcomController.h"
#import "CLTitleView.h"
#import "UIView+DAHMessages.h"
#import <AFNetworking/AFNetworking.h>
#import "CLConstants.h"
@interface CLChamarGarcomController ()

@end

@implementation CLChamarGarcomController

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self initNavigationBar];
}

- (void)initNavigationBar {
    
    CLTitleView *titleView = [[[NSBundle mainBundle] loadNibNamed:@"CLTitleView" owner:nil options:nil] lastObject];
    
    titleView.titulo = @"Chamar garçom";
    
    titleView.imagem = @"icone_chamados.png";
    
    [titleView configure];
    
    self.navigationItem.titleView = titleView;
    
}

- (IBAction)chamarGarcom:(id)sender {
    
    NSNumber *conta = [[NSUserDefaults standardUserDefaults]objectForKey:CLParamConta];
    
    if (!conta) {
     
        [self.view addInfoMessage:@"Você ainda não fez check-in" stickTime:2];
        
        return;
        
    }
    
    NSString *urlChamarGarcom = [CLAppBaseUrl stringByAppendingString:@"acoes_contas/chamargarcom"];
    
    NSString *parameters = [NSString stringWithFormat:@"acao=%@&conta=%@",CLTipoAlertaChamarGarcom,[[NSUserDefaults standardUserDefaults]objectForKey:CLParamConta]];
    
    [self enviarChamado:urlChamarGarcom parameters:parameters method:@"POST" fecharConta:NO];
    
}

- (IBAction)pedirConta:(id)sender {
    
    NSNumber *conta = [[NSUserDefaults standardUserDefaults]objectForKey:CLParamConta];
    
    if (!conta) {
        
        [self.view addInfoMessage:@"Você ainda não fez check-in" stickTime:2];
        
        return;
        
    }
    
    NSNumber *solicitado = [[NSUserDefaults standardUserDefaults]objectForKey:CLParamContaSolicitada];
    
    if (solicitado) {
        
        [self.view addInfoMessage:NSLocalizedString(@"CONTA_JA_SOLICITADA", nil) stickTime:2];
        
        return;
        
        
    }
    
    NSString *urlChamarGarcom = [CLAppBaseUrl stringByAppendingString:@"acoes_contas/fecharconta"];
    
    NSString *parameters = [NSString stringWithFormat:@"acao=%@&conta=%@",CLTipoAlertaPedirConta,[[NSUserDefaults standardUserDefaults]objectForKey:CLParamConta]];
    
    [self enviarChamado:urlChamarGarcom parameters:parameters method:@"POST" fecharConta:YES];
    
}

- (void)enviarChamado:(NSString *)url parameters:(NSString *)parameters method:(NSString *)method fecharConta:(BOOL) fecharConta  {
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:url]];
    
    [request setHTTPMethod:method];
    
    [request setHTTPBody:[parameters dataUsingEncoding:NSUTF8StringEncoding]];
    
    [request setValue:CLURLAppEncode forHTTPHeaderField:@"Content-Type"];
    
    self.indicatorView.alpha = 1;
    
    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    
    operation.responseSerializer = [AFJSONResponseSerializer serializer];
    
    [operation setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        self.indicatorView.alpha = 0;
       
        [self.view addInfoMessage:NSLocalizedString(@"CHAMADO_ENVIADO", nil) stickTime:2];
        
        if (fecharConta) {
        
            [[NSUserDefaults standardUserDefaults]setObject:[NSNumber numberWithBool:YES] forKey:CLParamContaSolicitada];
            
        }
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        
        self.indicatorView.alpha = 0;        
        
        [self.view addInfoMessage:NSLocalizedString(@"ACONTECEU_ERRO", nil) stickTime:3];
        
    }];
    
    [operation start];
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    
}

@end
