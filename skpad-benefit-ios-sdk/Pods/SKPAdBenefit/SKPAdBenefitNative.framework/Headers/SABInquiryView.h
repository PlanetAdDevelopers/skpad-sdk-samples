//
//  SABInquiryView.h
//  SABNative
//
//  Created by 이주연(Juyeon Lee)/Context-Aware기술개발팀/SKP on 2023/06/08.
//  Copyright © 2023 SKP. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface SABInquiryView : UIView
@property (nonatomic, readonly) UIButton *inquiryButton;
- (void)setInquiryViewImage:(UIImage *)img;
@end

NS_ASSUME_NONNULL_END
