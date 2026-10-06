package com.skplanet.app.skpadbenefitsample.ui.nativead;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.skplanet.app.skpadbenefitsample.BaseAdActivity;

public class NativeAdActivity extends BaseAdActivity {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return new NativeAdFragment();
    }

    @NonNull
    @Override
    protected String getAdTitle() {
        return "Native";
    }
}
