package com.skplanet.app.planetadscreensample;

import android.app.Application;

import com.skplanet.skpadscreen.sdk.SKPAdScreen;
import com.skplanet.skpadscreen.sdk.SimpleLockerActivity;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SKPAdScreen.init(Constants.SCREEN_UNIT_ID, this, SimpleLockerActivity.class, R.drawable.image_on_fail);
    }
}
