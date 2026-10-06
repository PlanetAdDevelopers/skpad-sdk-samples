package com.skplanet.app.skpadbenefitsample.ui.interstitial.dialog;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.skplanet.app.skpadbenefitsample.BaseAdActivity;

public class InterstitialDialogActivity extends BaseAdActivity {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return new InterstitialDialogFragment();
    }

    @NonNull
    @Override
    protected String getAdTitle() {
        return "Interstitial Dialog";
    }
}
