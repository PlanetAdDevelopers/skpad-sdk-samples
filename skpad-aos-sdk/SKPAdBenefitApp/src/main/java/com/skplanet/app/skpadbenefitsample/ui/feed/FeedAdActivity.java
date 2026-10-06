package com.skplanet.app.skpadbenefitsample.ui.feed;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.skplanet.app.skpadbenefitsample.BaseAdActivity;

public class FeedAdActivity extends BaseAdActivity {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return new FeedAdFragment();
    }

    @NonNull
    @Override
    protected String getAdTitle() {
        return "Feed";
    }
}
