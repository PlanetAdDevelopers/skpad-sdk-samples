package com.skplanet.app.skpadbenefitsample.ui.interstitial.bottomsheet;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.skplanet.app.skpadbenefitsample.BaseAdActivity;

public class InterstitialBottomSheetActivity extends BaseAdActivity {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return new InterstitialBottomSheetFragment();
    }

    @NonNull
    @Override
    protected String getAdTitle() {
        return "Interstitial BottomSheet";
    }
}
