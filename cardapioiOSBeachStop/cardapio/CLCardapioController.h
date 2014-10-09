//
//  CAViewController.h
//  cardapio
//
//  Created by Julio Rocha on 11/04/14.
//  Copyright (c) 2014 Login. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CLCardapioController : UIViewController <UIScrollViewDelegate>

- (void) initCardapioWithObjects:(NSMutableArray *) objects;
@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;

@property (weak, nonatomic) IBOutlet UIPageControl *pageControl;

@end
