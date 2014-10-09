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
    
    NSString *urlChamarGarcom = [CLAppBaseUrl stringByAppendingString:@"alertas"];
    
    NSString *parameters = [NSString stringWithFormat:@"flagresolvido=false&tipoalerta=%@&conta=%@",CLTipoAlertaChamarGarcom,[[NSUserDefaults standardUserDefaults]objectForKey:CLParamConta]];
    
    [self enviarChamado:urlChamarGarcom parameters:parameters method:@"POST"];
    
}

- (IBAction)pedirConta:(id)sender {
    
    NSNumber *solicitado = [[NSUserDefaults standardUserDefaults]objectForKey:CLParamContaSolicitada];
    
    if (solicitado) {
        
        [self.view addInfoMessage:NSLocalizedString(@"CONTA_JA_SOLICITADA", nil) stickTime:2];
        
        return;
        
        
    }
    
    NSNumber *conta = [[NSUserDefaults standardUserDefaults]objectForKey:CLParamConta];
    
    NSString *urlChamarGarcom = [[CLAppBaseUrl stringByAppendingString:@"contas/fechar/"]stringByAppendingFormat:@"%ld",[conta longValue]];
    
    [self enviarChamado:urlChamarGarcom parameters:@"" method:@"PUT"];
    
}

- (void)enviarChamado:(NSString *)url parameters:(NSString *)parameters method:(NSString *)method  {
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:url]];
    
    [request setHTTPMethod:method];
    
    [request setHTTPBody:[parameters dataUsingEncoding:NSUTF8StringEncoding]];
    
    [request setValue:CLURLAppEncode forHTTPHeaderField:@"Content-Type"];
    
    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    
    operation.responseSerializer = [AFJSONResponseSerializer serializer];
    
    [operation setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
       
        [self.view addInfoMessage:NSLocalizedString(@"CHAMADO_ENVIADO", nil) stickTime:2];
        
        [[NSUserDefaults standardUserDefaults]setObject:[NSNumber numberWithBool:YES] forKey:CLParamContaSolicitada];
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        
        [self.view addInfoMessage:NSLocalizedString(@"ACONTECEU_ERRO", nil) stickTime:3];
        
    }];
    
    [operation start];
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    
}

@end
