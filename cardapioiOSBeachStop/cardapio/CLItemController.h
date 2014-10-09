//
//  CLItemController.h
//  cardapio
//
//  Created by Julio Rocha on 12/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CLSubItemDetalheView.h"
@interface CLItemController : UIViewController <UITableViewDelegate,UITableViewDataSource>
@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (strong, nonatomic) CLItem *item;
@end
