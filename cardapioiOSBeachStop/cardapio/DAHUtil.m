//  Created by Marcelo Magalhães on 09/05/14.
//  Copyright (c) 2014 DaHora. All rights reserved.

#import "DAHUtil.h"
#import <CommonCrypto/CommonDigest.h>

@implementation DAHUtil

+ (void)addHorizontalTilt:(CGFloat)x verticalTilt:(CGFloat)y ToView:(UIView *)view {
    
    UIInterpolatingMotionEffect *xAxis = nil;
    UIInterpolatingMotionEffect *yAxis = nil;
    
    if (x != 0.0)
    {
        xAxis = [[UIInterpolatingMotionEffect alloc]
                 initWithKeyPath:@"center.x"
                 type:UIInterpolatingMotionEffectTypeTiltAlongHorizontalAxis];
        xAxis.minimumRelativeValue = [NSNumber numberWithFloat:-x];
        xAxis.maximumRelativeValue = [NSNumber numberWithFloat:x];
    }
    
    if (y != 0.0)
    {
        yAxis = [[UIInterpolatingMotionEffect alloc]
                 initWithKeyPath:@"center.y"
                 type:UIInterpolatingMotionEffectTypeTiltAlongVerticalAxis];
        yAxis.minimumRelativeValue = [NSNumber numberWithFloat:-y];
        yAxis.maximumRelativeValue = [NSNumber numberWithFloat:y];
    }
    
    if (xAxis || yAxis)
    {
        UIMotionEffectGroup *group = [[UIMotionEffectGroup alloc] init];
        NSMutableArray *effects = [[NSMutableArray alloc] init];
        if (xAxis)
        {
            [effects addObject:xAxis];
        }
        
        if (yAxis)
        {
            [effects addObject:yAxis];
        }
        group.motionEffects = effects;
        [view addMotionEffect:group];
    }
}

@end



