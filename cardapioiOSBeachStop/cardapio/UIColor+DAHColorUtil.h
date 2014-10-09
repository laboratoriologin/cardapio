//  Created by Marcelo Magalhães on 09/05/14.
//  Copyright (c) 2014 DaHora. All rights reserved.

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface UIColor(Util)

+ (UIColor *)colorFromHexString:(NSString *) cor;

+ (UIColor *)colorFromHexString:(NSString *) cor alpha:(float) alpha;

+ (UIColor *)appBrownColor;

+ (UIColor *)appMediumBrownColor;

+ (UIColor *)appLightBrownColor;

+ (UIColor *)appBeigeColor;

+ (UIColor *)appButtonColor;

+ (UIColor *)appButtonTextColor;

+ (UIColor *)appBackgroundColor;

+ (UIColor *)appHeaderTableViewColor;

@end
