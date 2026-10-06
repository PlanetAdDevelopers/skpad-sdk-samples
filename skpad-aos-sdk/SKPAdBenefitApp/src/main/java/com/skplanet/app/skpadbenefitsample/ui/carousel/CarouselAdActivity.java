package com.skplanet.app.skpadbenefitsample.ui.carousel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.skplanet.app.skpadbenefitsample.BaseAdActivity;

public class CarouselAdActivity extends BaseAdActivity {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return new CarouselAdFragment();
    }

    @NonNull
    @Override
    protected String getAdTitle() {
        return "Carousel";
    }
}
