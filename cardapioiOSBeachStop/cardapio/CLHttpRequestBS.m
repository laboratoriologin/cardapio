//
//  CLHttpRequestBS.m
//  cardapio
//
//  Created by Julio Rocha on 19/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import "CLHttpRequestBS.h"

@implementation CLHttpRequestBS


- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data {
    
    if(!self.mutableData) {
        self.mutableData = [[NSMutableData alloc]init];
    }
    
    [self.mutableData appendData:data];
    
}

- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error {
    
    if(self.completionHandler) {
        self.completionHandler();
    }
    
}

@end
