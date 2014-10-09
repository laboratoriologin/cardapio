//
//  URLParser.h
//  cardapio
//
//  Created by Julio Rocha on 20/05/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface URLParser : NSObject
@property (nonatomic, strong) NSArray *variables;

- (id)initWithURLString:(NSString *)url;
- (NSString *)valueForVariable:(NSString *)varName;

@end
