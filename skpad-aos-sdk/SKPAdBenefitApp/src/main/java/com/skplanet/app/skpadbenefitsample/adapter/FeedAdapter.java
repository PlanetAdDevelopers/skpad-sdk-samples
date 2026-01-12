package com.skplanet.app.skpadbenefitsample.adapter;

import android.content.Context;

import com.skplanet.app.skpadbenefitsample.Constants;
import com.skplanet.app.skpadbenefitsample.CustomFeedAdsAdapter;
import com.skplanet.app.skpadbenefitsample.CustomFeedFeedbackHandler;
import com.skplanet.app.skpadbenefitsample.CustomFeedHeaderViewAdapter;
import com.skplanet.app.skpadbenefitsample.CustomFeedToolbarHolder;
import com.skplanet.skpad.benefit.presentation.feed.FeedConfig;
import com.skplanet.skpad.benefit.presentation.feed.FeedHandler;

public class FeedAdapter {

    private Context context;
    private FeedHandler feedHandler;

    public FeedAdapter(Context context) {
        this.context = context;
        createFeedHandler(false);
    }

    public void createFeedHandler(boolean useCustomisedUI) {

        if (useCustomisedUI) {
            feedHandler = new FeedHandler(createCustomisedFeedConfig());
        }
        else {
            feedHandler = new FeedHandler(createDefaultFeedConfig());

        }
    }

    public FeedConfig createDefaultFeedConfig() {
        FeedConfig.Builder builder = new FeedConfig.Builder(context, Constants.FEED_UNIT_ID)
                .showInquiryButton(Constants.OLDER_14YEAR);  // // 만 14세 이상인 경우에만 VOC(문의하기) 기능을 노출해야합니다.
        return builder.build();
    }

    public FeedConfig createCustomisedFeedConfig() {
        FeedConfig.Builder builder = new FeedConfig.Builder(context, Constants.FEED_UNIT_ID)
                .feedHeaderViewAdapterClass(CustomFeedHeaderViewAdapter.class)
                .adsAdapterClass(CustomFeedAdsAdapter.class)
                .feedToolbarHolderClass(CustomFeedToolbarHolder.class)
                .showInquiryButton(Constants.OLDER_14YEAR)  // // 만 14세 이상인 경우에만 VOC(문의하기) 기능을 노출해야합니다.
                .feedFeedbackHandlerClass(CustomFeedFeedbackHandler.class);

        return builder.build();
    }


    public void show() {
        feedHandler.startFeedActivity(context);
    }

    public void preload(FeedHandler.FeedPreloadListener feedPreloadListener) {
        feedHandler.preload(feedPreloadListener);
    }

    public int getTotalReward() {
        return feedHandler.getTotalReward();
    }
}
