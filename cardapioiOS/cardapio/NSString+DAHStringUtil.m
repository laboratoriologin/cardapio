//  Created by Marcelo Magalhães on 09/05/14.
//  Copyright (c) 2014 DaHora. All rights reserved.

#import "NSString+DAHStringUtil.h"
#import <CommonCrypto/CommonDigest.h>

@implementation NSString(Util)

- (NSString*)MD5 {
    // Create pointer to the string as UTF8
    const char *ptr = [self UTF8String];
    
    // Create byte array of unsigned chars
    unsigned char md5Buffer[CC_MD5_DIGEST_LENGTH];
    
    // Create 16 byte MD5 hash value, store in buffer
    CC_MD5(ptr, strlen(ptr), md5Buffer);
    
    // Convert MD5 value in the buffer to NSString of hex values
    NSMutableString *output = [NSMutableString stringWithCapacity:CC_MD5_DIGEST_LENGTH * 2];
    for(int i = 0; i < CC_MD5_DIGEST_LENGTH; i++)
        [output appendFormat:@"%02x",md5Buffer[i]];
    
    return output;
}

- (NSString *)shortURLFromString:(NSString *) url{
    
    NSString *urlstr = [url stringByReplacingOccurrencesOfString:@" " withString:@""];
    
    NSString *apiEndpoint = [NSString stringWithFormat:@"http://tinyurl.com/api-create.php?url=%@",urlstr];
    
    NSString *shortURL = [NSString stringWithContentsOfURL:[NSURL URLWithString:apiEndpoint]
                                                  encoding:NSASCIIStringEncoding
                                                     error:nil];
    
    return shortURL;
    
}

- (BOOL)isEmailValid {
    
    NSString *regEx = @".+@.+\\.[a-z]+";
    NSRange range = [self rangeOfString:regEx options:NSRegularExpressionSearch];
    return range.location != NSNotFound;
    
}

@end
