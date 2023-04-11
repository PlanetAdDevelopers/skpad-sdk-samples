package com.skplanet.app.planetadbenefitsample.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import com.skplanet.app.planetadbenefitsample.Constants;
import com.skplanet.app.planetadbenefitsample.R;
import com.skplanet.skpad.benefit.SKPAdBenefit;
import com.skplanet.skpad.benefit.pop.PopOverlayPermissionConfig;
import com.skplanet.skpad.benefit.pop.SKPAdPop;

import io.mattcarroll.hover.overlay.OverlayPermission;

public class PopAdapger {

    public static final int REQUEST_CODE_SHOW_POP = 100;

    private SKPAdPop skpAdPop;
    private Activity activity;

    public PopAdapger(Activity activity) {
        this.activity = activity;
        createPop(activity);
    }

    public void createPop(Context context) {

        skpAdPop = new SKPAdPop(context, Constants.POP_UNIT_ID);
    }

    public void showPopOrRequestOverlayPermissionIfNeeded() {


        SKPAdBenefit.getPrivacyPolicyManager().grantConsent();
        if (SKPAdPop.hasPermission(activity) || Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            show();
        } else {
            SKPAdPop.requestPermissionWithDialog(activity,
                    new PopOverlayPermissionConfig.Builder(R.string.pop_name)
                            .settingsIntent(OverlayPermission.createIntentToRequestOverlayPermission(activity))
                            .requestCode(REQUEST_CODE_SHOW_POP)
                            .build()
            );
        }
    }

    public void show() {

        skpAdPop.preloadAndShowPop();
    }

    public void remove() {
        skpAdPop.removePop(activity);
    }
}
