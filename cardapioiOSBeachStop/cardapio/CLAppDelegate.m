//
//  CAAppDelegate.m
//  cardapio
//
//  Created by Julio Rocha on 11/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLAppDelegate.h"
#import <QuartzCore/QuartzCore.h>
#import "UIColor+DAHColorUtil.h"
#import "DAHUtil.h"
#import "CLCardapioBS.h"
#import "CLCardapioController.h"
#import "UIFont+DAHFontUtil.h"
#import "CLEmpresaBS.h"
#import "CLMenuDAO.h"
#import "CLContaBS.h"
#import <AFNetworking/AFNetworking.h>
#import "CLSubItemDAO.h"
#import "CLConstants.h"
#import "UIImageEffects.h"
#import "CLCadastroController.h"
#import <FacebookSDK/FacebookSDK.h>
@implementation CLAppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    
    [[UITabBar appearance]setTintColor:[UIColor whiteColor]];
    
    [[UINavigationBar appearance]setBackgroundImage:[UIImage imageNamed:@"barra_topo.png"] forBarMetrics:UIBarMetricsDefault];
    
    [[UITabBar appearance]setBackgroundImage:[UIImage imageNamed:@"barra_menu.png"]];
    
    [[UINavigationBar appearance]setTintColor:[UIColor whiteColor]];
    
    [[UINavigationBar appearance]setBarTintColor:[UIColor whiteColor]];
   
    [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleLightContent];
    
    [self initDataBase];
    
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    
    self.tabBarController =[storyboard instantiateInitialViewController];
    
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    
    self.window.rootViewController =  self.tabBarController;
    
    [self.window makeKeyAndVisible];
    
    [self checkCadastro];
    
    [self configureSplash];
    
    [self checkLastKey:nil];
    
    return YES;
}

- (void)configureSplash {
    
    self.splashImage = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, self.window.bounds.size.width, self.window.bounds.size.height)];

    if(!IS_IPHONE_5) {
    
        self.splashImage.image = [UIImage imageNamed:@"Default"];

    } else {
        
        self.splashImage.image = [UIImage imageNamed:@"Default-568h"];
    
    }
    
    [self.window addSubview:self.splashImage];
   
    [self.window bringSubviewToFront:self.splashImage];
    
    self.splashImage.layer.anchorPoint = CGPointMake(0, 0.5);
    
    self.splashImage.frame = CGRectMake(0, 0, self.window.bounds.size.width, self.window.bounds.size.height);
    
    self.backgroundView = [[UIView alloc]initWithFrame:self.window.frame];
    
    self.backgroundView.backgroundColor = [UIColor blackColor];
    
    self.backgroundView.alpha = 0.4;
    
    [self.window addSubview:self.backgroundView];
    
    self.configAppLabel = [[UILabel alloc]initWithFrame:CGRectMake(5, self.window.center.y + 150, 305, 50)];
    
    [self.configAppLabel setTextColor:[UIColor whiteColor]];
    
    [self.configAppLabel setTextAlignment:NSTextAlignmentCenter];
    
    [self.configAppLabel setFont:[UIFont appFontWithSize:17]];
    
    self.configAppLabel.numberOfLines = 0;
    
    [DAHUtil addHorizontalTilt:10 verticalTilt:10 ToView:self.configAppLabel];
    
    self.indicador = [[UIActivityIndicatorView alloc]initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhite];
    
    [self.indicador startAnimating];
    
    self.indicador.center = CGPointMake(self.window.center.x,self.window.center.y + 130);

    self.configAppLabel.alpha=0;
    
    [self.window addSubview:self.configAppLabel];
    
    [UIView animateWithDuration:0.5 animations:^{
        self.configAppLabel.alpha=1;
    }];
    
}

- (void)checkCadastro {
    
    NSString *idCliente = [[NSUserDefaults standardUserDefaults]stringForKey:CLParamIDCliente];
    
    if (!idCliente) {
        
        CLCadastroController *controller = [[CLCadastroController alloc]initWithNibName:@"CLCadastroController" bundle:nil];
        
        [self.tabBarController presentViewController:controller animated:YES completion:nil];
        
    }
    
}

- (void)checkLastKey:(UIButton *) sender {
    
    if(sender) {
        [sender removeFromSuperview];
    }
    
    static NSString *chave = @"chave";
    
    [self.window addSubview:self.indicador];
    
    self.configAppLabel.text = @"Buscando atualizações!";
    
    CLEmpresaBS *empresaBS = [[CLEmpresaBS alloc]init];
    
    NSMutableArray *resultArray = [NSMutableArray array];
    
    [empresaBS setCompletionHandler:^{
        
        NSString *lastKey = [resultArray count] > 0 ? [resultArray objectAtIndex:0] : nil;
        
        NSString *lastKeySaved = [[NSUserDefaults standardUserDefaults]stringForKey:chave];
        
        if (!lastKey) {
            
            [self handleConfigurationErrorWithMessage:@"Não foi possível buscar atualizações, verifique sua conexão" checkingKey:YES];
            
            self.backgroundView.alpha=0.4;
        
        } else {
            
            if (![lastKey isEqualToString:lastKeySaved]) {
                
                [[NSUserDefaults standardUserDefaults]setObject:lastKey forKey:chave];
                
                [[[CLMenuDAO alloc]init]cleanDataBase];
                
            }
            
            [self checkContaAtual:YES];
            
        }
        
    }];
    
    [empresaBS getLastKey:resultArray];
    
}

- (void)checkContaAtual:(BOOL) findCategorias {
    
    NSNumber *contaAtual = [[NSUserDefaults standardUserDefaults]objectForKey:CLParamConta];
    
    if (contaAtual > 0) {
        
        NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:[CLAppBaseUrl stringByAppendingFormat:@"contas/aberto/%d",contaAtual.intValue]]];
        
        AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
        
        operation.responseSerializer = [AFJSONResponseSerializer serializer];
        
        [operation setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
            
            NSDictionary *response = responseObject;
            
            NSNumber *flagAberto = [[response objectForKey:@"conta"]objectForKey:@"id"];
            
            if(![flagAberto boolValue]) {
                
                [self limparPedidos];
                
            }
            
            if (findCategorias) {
                [self findCategorias:nil];
            }
            
        } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
            
            if(findCategorias) {
            
                [self handleConfigurationErrorWithMessage:@"Não foi possível buscar atualizações, verifique sua conexão com a internet!" checkingKey:YES];
                
            }
            
        }];
        
        [operation start];
        
    } else {
        
        [self limparPedidos];
        
        if (findCategorias) {
            
            [self findCategorias:nil];
            
        }
        
    }
    
}

- (void)findCategorias:(UIButton *) sender {
    
    if(sender) {
    
        [sender removeFromSuperview];
        
    }
    
    [self.window addSubview:self.indicador];

    self.configAppLabel.text = @"Preparando pratos para você!";
    
    self.categoriasCardapio = [[NSMutableArray alloc]init];
    
    CLCardapioController *cardapioController = [[[[self.window.rootViewController childViewControllers]objectAtIndex:0]childViewControllers]objectAtIndex:0];
    
    CLCardapioBS *cardapioBS = [[CLCardapioBS alloc]init];
    
    [cardapioBS setCompletionHandler:^ {
        
        if([self.categoriasCardapio count]==0) {
            
            [self handleConfigurationErrorWithMessage:@"Não foi possível buscar seus pratos, verifique sua conexão com a internet!" checkingKey:NO];
            
        } else {
            
            [cardapioController initCardapioWithObjects:self.categoriasCardapio];
            
            //animate the open
            [UIView animateWithDuration:1.0 delay:0.0 options:(UIViewAnimationOptionCurveEaseInOut) animations:^{
                
                [self.indicador removeFromSuperview];
                
                [self.configAppLabel removeFromSuperview];
                
                self.splashImage.layer.transform = CATransform3DRotate(CATransform3DIdentity, -M_PI_2, 0, 1, 0);
                
                [self.backgroundView removeFromSuperview];
                
            } completion:^(BOOL finished){

                [self.splashImage removeFromSuperview];
            }];
            
            
        }
        
    }];
    
    [cardapioBS findCategorias:self.categoriasCardapio];
    
}

- (void)handleConfigurationErrorWithMessage:(NSString *)message checkingKey:(BOOL) checkingKey {
    
    [self.indicador removeFromSuperview];
    
    self.configAppLabel.text = message;
    
    UIButton *buttonTentarNovamente = [[UIButton alloc]initWithFrame:self.indicador.frame];
    
    [buttonTentarNovamente setImage:[UIImage imageNamed:@"bt_refresh"] forState:UIControlStateNormal];
    
    [self.window addSubview:buttonTentarNovamente];
    
    if (checkingKey) {
        
        [buttonTentarNovamente addTarget:self action:@selector(checkLastKey:) forControlEvents:UIControlEventTouchUpInside];
    
    } else {
    
        [buttonTentarNovamente addTarget:self action:@selector(findCategorias:) forControlEvents:UIControlEventTouchUpInside];
        
    }
    
    [DAHUtil addHorizontalTilt:10 verticalTilt:10 ToView:buttonTentarNovamente];
    
}

- (void)limparPedidos {
    
    [[NSUserDefaults standardUserDefaults]removeObjectForKey:CLParamConta];
    
    [[NSUserDefaults standardUserDefaults]removeObjectForKey:CLParamMesa];
    
    [[NSUserDefaults standardUserDefaults]removeObjectForKey:CLParamContaSolicitada];

    [[[CLSubItemDAO alloc]init]clean];

}

- (void)initDataBase {
    
    self.databaseName = @"cardapio.sqlite";
    
    NSArray *documentPaths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory,NSUserDomainMask, YES);
    
    NSString *documentDir = [documentPaths objectAtIndex:0];
    
    self.databasePath = [documentDir stringByAppendingPathComponent:self.databaseName];
    
    BOOL exist = [[NSFileManager defaultManager] fileExistsAtPath:self.databasePath];
    
    if(!exist) {
    
        NSString *path = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:self.databaseName];
    
        [[NSFileManager defaultManager] copyItemAtPath:path toPath:self.databasePath error:nil];
        
    }
    
}
							
- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    [self checkContaAtual:NO];
    self.tabBarController.selectedIndex=0;

}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    [self checkContaAtual:NO];
}

- (BOOL)application:(UIApplication *)application
            openURL:(NSURL *)url
  sourceApplication:(NSString *)sourceApplication
         annotation:(id)annotation {
    
    // Call FBAppCall's handleOpenURL:sourceApplication to handle Facebook app responses
    BOOL wasHandled = [FBAppCall handleOpenURL:url sourceApplication:sourceApplication];
    
    // You can add your app-specific url handling code here if needed
    
    return wasHandled;
}

@end
