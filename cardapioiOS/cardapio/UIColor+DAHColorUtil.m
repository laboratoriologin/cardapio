//  Created by Marcelo Magalhães on 09/05/14.
//  Copyright (c) 2014 DaHora. All rights reserved.

#import "UIColor+DAHColorUtil.h"

@implementation UIColor(Util)

+ (UIColor *)colorFromHexString:(NSString *) cor {
    
    unsigned result=0;
    NSScanner *scanner = [NSScanner scannerWithString:[cor stringByReplacingOccurrencesOfString:@"#" withString:@""]];
    
    [scanner scanHexInt:&result];
    
    return [UIColor colorWithRed:((float)((result & 0xFF0000) >> 16))/255.0 green:((float)((result & 0xFF00) >> 8))/255.0 blue:((float)(result & 0xFF))/255.0 alpha:1.0];
    
}

+ (UIColor *)colorFromHexString:(NSString *) cor alpha:(float) alpha {
    
    unsigned result=0;
    NSScanner *scanner = [NSScanner scannerWithString:[cor stringByReplacingOccurrencesOfString:@"#" withString:@""]];
    
    [scanner scanHexInt:&result];
    
    return [UIColor colorWithRed:((float)((result & 0xFF0000) >> 16))/255.0 green:((float)((result & 0xFF00) >> 8))/255.0 blue:((float)(result & 0xFF))/255.0 alpha:alpha];
    
}

+ (UIColor *)appBrownColor {
    
    return [UIColor colorFromHexString:@"#4F3832"];
    
}

+ (UIColor *)appMediumBrownColor {
    
    return [UIColor colorFromHexString:@"#634C47"];
    
}

+ (UIColor *)appLightBrownColor {
    
    return [UIColor colorFromHexString:@"#C19E97"];
    
}


+ (UIColor *)appBeigeColor {
    
    return [UIColor colorFromHexString:@"#F2DBB3"];
    
}

+ (UIColor *)appGreenColor {
    
    return [UIColor colorFromHexString:@"#83C900"];
    
}

+ (UIColor *)appWineColor {
    
    return [UIColor colorFromHexString:@"#A50000"];
    
}

@end
