//  Created by Marcelo Magalhães on 09/05/14.
//  Copyright (c) 2014 DaHora. All rights reserved.

#import <Foundation/Foundation.h>

@interface NSString(Util)

- (NSString *)MD5;
- (NSString *)shortURLFromString:(NSString *) url;
- (BOOL)isEmailValid;

@end
