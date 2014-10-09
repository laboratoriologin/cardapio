//  Created by Marcelo Magalhães on 09/05/14.
//  Copyright (c) 2014 DaHora. All rights reserved.

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>


#define IS_IPHONE_5 ( fabs( ( double )[ [ UIScreen mainScreen ] bounds ].size.height - ( double )568 ) < DBL_EPSILON )

@interface DAHUtil : NSObject

+ (void)addHorizontalTilt:(CGFloat)x verticalTilt:(CGFloat)y ToView:(UIView *)view;

@end



