//
//  CLClienteBS.h
//  cardapioBeachStop
//
//  Created by Login Informatica on 20/02/15.
//  Copyright (c) 2015 Login. All rights reserved.
//

#import "CLHttpRequestBS.h"

@interface CLClienteBS : CLHttpRequestBS

@property(nonatomic,copy) NSString *parameters;

- (void)insert:(NSMutableArray *) resultParam;

@end