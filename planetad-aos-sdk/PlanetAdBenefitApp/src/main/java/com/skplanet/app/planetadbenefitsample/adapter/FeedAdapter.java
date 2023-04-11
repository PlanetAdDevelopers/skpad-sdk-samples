package com.skplanet.app.planetadbenefitsample.adapter;

import android.content.Context;

import com.skplanet.app.planetadbenefitsample.Constants;
import com.skplanet.app.planetadbenefitsample.CustomFeedAdsAdapter;
import com.skplanet.app.planetadbenefitsample.CustomFeedFeedbackHandler;
import com.skplanet.skpad.benefit.presentation.feed.FeedConfig;
import com.skplanet.skpad.benefit.presentation.feed.FeedHandler;
import com.skplanet.skpad.benefit.presentation.feed.header.DefaultFeedHeaderViewAdapter;

public class FeedAdapter {

    private Context context;
    private FeedHandler feedHandler;

    public FeedAdapter(Context context) {
        this.context = context;
        createFeedHandler();
    }

    public void createFeedHandler() {
        feedHandler = new FeedHandler(createFeedConfig());
    }

    public FeedConfig  createFeedConfig() {
        FeedConfig.Builder builder = new FeedConfig.Builder(context, Constants.FEED_UNIT_ID)
                .feedHeaderViewAdapterClass(DefaultFeedHeaderViewAdapter.class)
                .adsAdapterClass(CustomFeedAdsAdapter.class)
                .articlesEnabled(true)
                .autoLoadingEnabled(true)
                .tabUiEnabled(true)
                .filterUiEnabled(true)
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
