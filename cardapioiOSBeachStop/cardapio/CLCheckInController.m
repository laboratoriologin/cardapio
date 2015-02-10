//
//  CACheckInController.m
//  cardapio
//
//  Created by Julio Rocha on 14/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLCheckInController.h"
#import "CLTitleView.h"
#import <AVFoundation/AVFoundation.h>
#import "CLContaBS.h"
#import "UIView+DAHMessages.h"
#import "URLParser.h"
#import "UIFont+DAHFontUtil.h"
#import "UIColor+DAHColorUtil.h"
#import <AFNetworking/AFNetworking.h>
NSString *const  CLMesaAtual = @"mesa";
NSString *const  CLContaAtual = @"conta";
NSInteger const CLBadRequest = 412;
#import <Social/Social.h>
@interface CLCheckInController ()

@end

@implementation CLCheckInController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [self initNavigationBar];
    
}

- (void)viewWillAppear:(BOOL)animated {
    
    [super viewWillAppear:animated];
    
    [self checkContaAberta];
    
}

- (void)checkContaAberta {
    
    NSNumber *contaAberta = [[NSUserDefaults standardUserDefaults]objectForKey:CLContaAtual];
    
    if (contaAberta > 0) {
        
        [self.checkinButton setTitle:NSLocalizedString(@"MUDAR_MESA", nil) forState:UIControlStateNormal];
        
    } else {
        
        [self.checkinButton setTitle:NSLocalizedString(@"CHECK_IN", nil) forState:UIControlStateNormal];
        
    }
    
    self.checkinButton.titleLabel.font = [UIFont appFontWithSize:17];
    
    [self initViewTexts];
    
}

#pragma mark - View Configuration

- (void)initNavigationBar {
    
    CLTitleView *titleView = [[[NSBundle mainBundle] loadNibNamed:@"CLTitleView" owner:nil options:nil] lastObject];
    
    titleView.titulo = @"Check In";
    
    titleView.imagem = @"bt_menu_checkin.png";
    
    [titleView configure];
    
    self.navigationItem.titleView = titleView;
    
}

- (void)initViewTexts {

    self.welcomeLabel.font = [UIFont appFontWithSize:17];
    
    self.welcomeLabel.text = [NSString stringWithFormat:NSLocalizedString(@"BEM_VINDO", nil),@"Beach Stop"];
    
    self.infoMesaLabel.font = [UIFont appFontWithSize:18];
    
    self.numeroMesaLabel.font = [UIFont appFontWithSize:65];
    
    self.infoMesaLabel.alpha = 1;
    
    NSNumber *mesaAtual = [[NSUserDefaults standardUserDefaults]objectForKey:CLMesaAtual];
    
    if (mesaAtual > 0) {
        
        self.numeroMesaLabel.text = [mesaAtual stringValue];
        
        self.numeroMesaLabel.alpha = 1;
        
        self.infoMesaLabel.alpha = 1;
        
    } else {
        
        self.infoMesaLabel.alpha = 0;
        
        self.numeroMesaLabel.alpha = 0;
        
    }
    
}

- (IBAction)checkIn:(id)sender {
    
    ZBarReaderViewController *reader = [ZBarReaderViewController new];
    
    reader.readerDelegate = self;
    
    [reader.scanner setSymbology: 0
                          config: ZBAR_CFG_ENABLE
                              to: 0];
    [reader.scanner setSymbology: ZBAR_QRCODE
                          config: ZBAR_CFG_ENABLE
                              to: 1];
    
    reader.readerView.session.sessionPreset = AVCaptureSessionPreset1280x720;
    
    
    [self presentViewController:reader animated: YES completion:nil];
    
}

#pragma  mark imagePickerDelegate

- (void) imagePickerController: (UIImagePickerController*) reader didFinishPickingMediaWithInfo: (NSDictionary*) info {
    id<NSFastEnumeration> results = [info objectForKey: ZBarReaderControllerResults];
    
    ZBarSymbol *symbol = nil;
    for(symbol in results) break;
    
    NSString *parameters = symbol.data;
    
    [reader dismissViewControllerAnimated:YES completion:nil];
    
    NSNumber *mesa = [[NSUserDefaults standardUserDefaults]objectForKey:CLMesaAtual];
    
    NSString *url = [CLAppBaseUrl stringByAppendingFormat:@"contas?%@",parameters];
    
    URLParser *urlParser = [[URLParser alloc]initWithURLString: url];

    NSNumber *novaMesa = [NSNumber numberWithInt:[[urlParser valueForVariable:CLMesaAtual]intValue]];
    
    if (mesa && [mesa isEqualToNumber:novaMesa]) {
        
        [self.view addInfoMessage:NSLocalizedString(@"JA_ESTA_NA_MESA", nil) stickTime:3];
        
        return;

    }
    
    [self realizarCheckIn:novaMesa];
    
}

- (void)realizarCheckIn:(NSNumber *)mesa {
    
    BOOL changeMesa=NO;

    NSString *endereco = [CLAppBaseUrl stringByAppendingString:@"contas"];
    
    NSString *httpMethod = @"POST";
    
    NSNumber *codigoConta = [[NSUserDefaults standardUserDefaults]objectForKey:CLContaAtual];
    
    if(codigoConta > 0) {
        
        httpMethod = @"PUT";
        
        changeMesa=YES;
        
        endereco = [endereco stringByAppendingFormat:@"/%d",codigoConta.intValue];
        
    }
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:endereco]];

    [request setHTTPMethod:httpMethod];
    
    NSString *postString = [NSString stringWithFormat:@"numero=%d&tipoconta=1", mesa.intValue];

    if(changeMesa) {
    
        [request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
        
    }

    [request setHTTPBody:[postString dataUsingEncoding:NSUTF8StringEncoding]];
    
    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    
    operation.responseSerializer = [AFJSONResponseSerializer serializer];
    
    [operation setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        [self.indicatorView stopAnimating];

        NSDictionary *response = responseObject;
        
        if(changeMesa && [[response objectForKey:@"conta"]objectForKey:@"flagAberto"]) {
            
            [self.view addInfoMessage:NSLocalizedString(@"MESA_OCUPADA", nil) stickTime:3];
            
        } else {
            
            NSNumber *codigoConta = [[response objectForKey:@"conta"]objectForKey:@"id"];
            
            [[NSUserDefaults standardUserDefaults]setObject:codigoConta forKey:CLContaAtual];
        
            [[NSUserDefaults standardUserDefaults]setObject:mesa forKey:CLMesaAtual];
            
            self.numeroMesaLabel.text = [mesa stringValue];
            
            self.infoMesaLabel.alpha = 1;
            
            self.numeroMesaLabel.alpha = 1;
            
            [self.checkinButton setTitle:NSLocalizedString(@"MUDAR_MESA", nil) forState:UIControlStateNormal];
            
            if (!changeMesa) {
            
                SLComposeViewController *composer = [SLComposeViewController  composeViewControllerForServiceType:SLServiceTypeFacebook];
                
                [composer setInitialText:@"No Beach Stop de Ipitanga."];
                
                [self presentViewController:composer animated:YES completion:nil];
                
            }
            
        }
        
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        
        NSHTTPURLResponse *response = operation.response;

        if(response.statusCode == CLBadRequest) {
            
             [self.view addInfoMessage:NSLocalizedString(@"MESA_OCUPADA", nil) stickTime:3];
            
        } else {

            [self.view addInfoMessage:NSLocalizedString(@"ACONTECEU_ERRO", nil) stickTime:3];
            
        }
        
        [self.indicatorView stopAnimating];
        
    }];
    
    self.indicatorView.alpha=1;
    
    [self.indicatorView startAnimating];
    
    [operation start];
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    
}

@end
