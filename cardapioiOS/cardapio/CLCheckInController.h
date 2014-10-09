//
//  CACheckInController.h
//  cardapio
//
//  Created by Julio Rocha on 14/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ZBarSDK.h"
@interface CLCheckInController : UIViewController <ZBarReaderDelegate>
- (IBAction)checkIn:(id)sender;
@property (weak, nonatomic) IBOutlet UIButton *checkinButton;
@property (weak, nonatomic) IBOutlet UILabel *welcomeLabel;
@property (weak, nonatomic) IBOutlet UILabel *infoMesaLabel;
@property (weak, nonatomic) IBOutlet UILabel *numeroMesaLabel;
@property (weak, nonatomic) IBOutlet UIActivityIndicatorView *indicatorView;

@end
