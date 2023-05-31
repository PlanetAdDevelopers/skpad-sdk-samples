//
//  SKPImpressionTrackableView.h
//
//  Created by Jaehee Ko on 22/11/2018.
//  Copyright © 2018 Jaehee Ko. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol SKPImpressionTrackableView <NSObject>
- (BOOL)shouldTrackImpression;
- (void)viewDidImpressed;
@optional
- (void)viewBecomeVisible;
- (void)viewBecomeInvisible;
@end
