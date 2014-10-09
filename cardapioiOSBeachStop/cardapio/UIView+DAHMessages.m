//
//  UIView+DAHMessages.m
//  cardapio
//
//  Created by Julio Rocha on 19/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "UIView+DAHMessages.h"
#import "MBProgressHUD.h"
@implementation UIView(Util)

- (void)addInfoMessage:(NSString *)message stickTime:(NSInteger) stickTime {

    MBProgressHUD *progress = [MBProgressHUD showHUDAddedTo:self animated:YES];
    
    progress.mode = MBProgressHUDModeText;
    
    progress.labelText=message;
    
    [progress show:YES];
    
    [progress hide:YES afterDelay:stickTime];
    
}

@end
