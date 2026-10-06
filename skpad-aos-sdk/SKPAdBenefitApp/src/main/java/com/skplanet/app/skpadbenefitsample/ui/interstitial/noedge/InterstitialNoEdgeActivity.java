package com.skplanet.app.skpadbenefitsample.ui.interstitial.noedge;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.skplanet.app.skpadbenefitsample.BaseAdActivity;

public class InterstitialNoEdgeActivity extends BaseAdActivity {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return new InterstitialNoEdgeFragment();
    }

    @NonNull
    @Override
    protected String getAdTitle() {
        return "Interstitial No Edge";
    }
}
