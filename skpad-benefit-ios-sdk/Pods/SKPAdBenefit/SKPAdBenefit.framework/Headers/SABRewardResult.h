//
//  SABRewardResult.h
//  SABAd
//
//  Created by Jaehee Ko on 02/05/2019.
//  Copyright © 2019 Buzzvil. All rights reserved.
//

typedef enum {
    SABRewardResultSuccess = 0,
    SABRewardResultAlreadyParticipated,
    SABRewardResultBrowserNotLaunched,
    SABRewardResultTooShortToParticipate,
    SABRewardResultNetworkError,
    SABRewardResultClientError,
    SABRewardResultServerError,
    SABRewardResultTimeout,
    SABRewardResultMissingReward,
    SABRewardResultNoPbUrl,
    SABRewardResultUnknownError,
} SABRewardResult;

