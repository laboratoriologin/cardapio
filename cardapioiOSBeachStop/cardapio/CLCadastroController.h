//
//  CLCadastroController.h
//  cardapioBeachStop
//
//  Created by Login Informatica on 19/02/15.
//  Copyright (c) 2015 Login. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <FacebookSDK/FacebookSDK.h>

@interface CLCadastroController : UIViewController <FBLoginViewDelegate>
@property (weak, nonatomic) IBOutlet UILabel *welcomeTextLabel;
@property (weak, nonatomic) IBOutlet UIView *fbLoginView;
@property (weak, nonatomic) IBOutlet UITextField *nomeTextField;
@property (weak, nonatomic) IBOutlet UITextField *emailTextField;
@property (weak, nonatomic) IBOutlet UITextField *celularTextField;
@property (weak, nonatomic) IBOutlet UITextField *aniversarioTextField;
- (IBAction)enviar:(id)sender;
@property (strong, nonatomic) IBOutletCollection(UITextField) NSArray *txtFields;

@end
