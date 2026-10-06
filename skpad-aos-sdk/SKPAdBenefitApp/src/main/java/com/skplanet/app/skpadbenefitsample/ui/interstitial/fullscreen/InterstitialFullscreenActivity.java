package com.skplanet.app.skpadbenefitsample.ui.interstitial.fullscreen;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.skplanet.app.skpadbenefitsample.BaseAdActivity;

public class InterstitialFullscreenActivity extends BaseAdActivity {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return new InterstitialFullscreenFragment();
    }

    @NonNull
    @Override
    protected String getAdTitle() {
        return "Interstitial Fullscreen";
    }
}
