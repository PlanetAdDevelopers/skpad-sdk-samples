package com.skplanet.app.skpadbenefitsample.ui.web;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.skplanet.app.skpadbenefitsample.BaseAdActivity;

public class WebSdkActivity extends BaseAdActivity {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return new WebSdkFragment();
    }

    @NonNull
    @Override
    protected String getAdTitle() {
        return "Web SDK";
    }
}
