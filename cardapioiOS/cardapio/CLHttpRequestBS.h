//
//  CLHttpRequestBS.h
//  cardapio
//
//  Created by Julio Rocha on 19/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CLConstants.h"
@interface CLHttpRequestBS : NSObject <NSURLConnectionDelegate>

@property (nonatomic, copy) void (^completionHandler)(void);
@property(nonatomic,strong) NSMutableData *mutableData;

@end
