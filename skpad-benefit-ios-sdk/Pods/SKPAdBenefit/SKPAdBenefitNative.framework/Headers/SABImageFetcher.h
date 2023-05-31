//
//  SABImageFetcher.h
//  SKPAdNative
//
//  Created by Jaehee Ko on 11/06/2019.
//  Copyright © 2019 Buzzvil. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface SABImageFetcher : NSObject

+ (void)renderImageFromUrl:(NSString *)url onImageView:(UIImageView *)imageView;

@end

NS_ASSUME_NONNULL_END
