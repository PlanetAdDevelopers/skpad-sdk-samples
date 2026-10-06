package com.skplanet.app.skpadbenefitsample.ui.banner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.skplanet.app.skpadbenefitsample.BaseAdActivity;

public class NativeAdBannerActivity extends BaseAdActivity {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return new NativeAdBannerFragment();
    }

    @NonNull
    @Override
    protected String getAdTitle() {
        return "Banner";
    }
}
