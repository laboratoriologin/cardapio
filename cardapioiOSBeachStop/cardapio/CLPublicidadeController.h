//
//  CLPublicidadeController.h
//  cardapioBeachStop
//
//  Created by Login Informatica on 26/02/15.
//  Copyright (c) 2015 Login. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CLPublicidade.h"
@interface CLPublicidadeController : UIViewController <UITableViewDataSource,UITableViewDelegate>
@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property CLPublicidade *publicidade;

@end
