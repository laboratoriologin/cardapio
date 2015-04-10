//
//  CLCadastroController.m
//  cardapioBeachStop
//
//  Created by Login Informatica on 19/02/15.
//  Copyright (c) 2015 Login. All rights reserved.
//

#import "CLCadastroController.h"
#import "UIFont+DAHFontUtil.h"
#import "CLCliente.h"
#import "CLConstants.h"
#import "MBProgressHUD.h"
#import "UIView+DAHMessages.h"
#import <AFNetworking/AFNetworking.h>
#import <MobileCoreServices/MobileCoreServices.h>
@interface CLCadastroController ()

@end

@implementation CLCadastroController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.welcomeTextLabel.font = [UIFont appFontWithSize:14];
    
    FBLoginView *loginview = [[FBLoginView alloc] initWithReadPermissions:
                                                   @[@"public_profile", @"email"]];
   
    #ifdef __IPHONE_7_0
    #ifdef __IPHONE_OS_VERSION_MAX_ALLOWED
    #if __IPHONE_OS_VERSION_MAX_ALLOWED >= __IPHONE_7_0
    if ([self respondsToSelector:@selector(setEdgesForExtendedLayout:)]) {
            loginview.frame = CGRectOffset(loginview.frame, 5, 25);
    }
    #endif
    #endif
    #endif
    
    loginview.delegate = self;
    
    [self.fbLoginView addSubview:loginview];
    
}

#pragma mark - FBLoginView Delegate
- (void)loginViewFetchedUserInfo:(FBLoginView *)loginView
                            user:(id<FBGraphUser>)user {

    self.nomeTextField.text = [user.first_name stringByAppendingFormat:@" %@",user.last_name];
    
    self.emailTextField.text = [user objectForKey:@"email"];
    
    self.aniversarioTextField.text = user.birthday;
    
}

#pragma mark - IBActions
- (IBAction)enviar:(id)sender {
    
    CLCliente *cliente = [CLCliente new];
    
    cliente.nome = self.nomeTextField.text;
    
    cliente.email = self.emailTextField.text;
    
    cliente.dataNascimento = self.aniversarioTextField.text;
    
    cliente.celular = self.celularTextField.text;
    
    if (![self validarCampos:cliente]) {
        return;
    }
    
    [self salvar:cliente];
    
}

- (BOOL)validarCampos:(CLCliente *)cliente {
    
    if ([cliente.nome length]==0) {
        
        [self.view addInfoMessage:@"O campo nome é obrigatório" stickTime:2];
        
        return NO;
        
    }
    
    if ([cliente.email length]==0) {
        
        [self.view addInfoMessage:@"O campo e-mail é obrigatório" stickTime:2];
        
        return NO;
        
    }

    if ([cliente.dataNascimento length]==0) {
        
        [self.view addInfoMessage:@"O campo data de nascimento é obrigatório" stickTime:2];
        
        return NO;
        
    }
    
    NSDateFormatter *formatter = [NSDateFormatter new];
    
    formatter.dateFormat = @"dd/MM/yyyy";
    
    NSDate *date = [formatter dateFromString:cliente.dataNascimento];
    
    if (!date) {

        [self.view addInfoMessage:@"Data de nascimento inválida" stickTime:2];
        
        return NO;
        
    }
    
    return YES;
    
}

- (void)salvar:(CLCliente *)cliente {
    
    NSString *parameters = [NSString stringWithFormat:@"nome=%@&datanascimento=%@&celular=%@&email=%@",cliente.nome,cliente.dataNascimento,cliente.celular,cliente.email];
    
    NSString *url = [CLAppBaseUrl stringByAppendingString:@"clientes"];
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:url]];
    
    [request setHTTPMethod:@"POST"];
    
    [request setHTTPBody:[parameters dataUsingEncoding:NSUTF8StringEncoding]];
    
    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    
    operation.responseSerializer = [AFJSONResponseSerializer serializer];
    
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    hud.mode = MBProgressHUDModeIndeterminate;

    hud.labelText = @"Enviando seus dados...";
    
    [operation setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        NSDictionary *response = responseObject;
        
        cliente.codigo = [[response objectForKey:@"cliente"]objectForKey:@"id"];
        
        [[NSUserDefaults standardUserDefaults]setObject:cliente.codigo forKey:CLParamIDCliente];
        
        [hud hide:YES];
        
        [self dismissViewControllerAnimated:YES completion:nil];
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        
        [self.view addInfoMessage:@"Ocorreu um erro, tente novamente mais tarde" stickTime:2];
        
        [hud hide:YES];
        
    }];
    
    [operation start];

    
}

-(void) touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    
    for(UITextField *field in self.txtFields) {
        
        if([field isFirstResponder] ) {
            
            [field resignFirstResponder];
            
            break;
            
        }
        
    }
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
