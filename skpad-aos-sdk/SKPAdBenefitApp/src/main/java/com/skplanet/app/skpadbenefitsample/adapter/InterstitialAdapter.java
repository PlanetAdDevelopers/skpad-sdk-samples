package com.skplanet.app.skpadbenefitsample.adapter;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.skplanet.app.skpadbenefitsample.Constants;
import com.skplanet.skpad.benefit.core.ad.AdError;
import com.skplanet.skpad.benefit.presentation.interstitial.InterstitialAdHandler;
import com.skplanet.skpad.benefit.presentation.interstitial.InterstitialAdHandlerFactory;

public class InterstitialAdapter {

    private Context context;

    public InterstitialAdapter(Context context) {
        this.context = context;
    }

    public void show(InterstitialAdHandler.Type interstitialType) {
        final InterstitialAdHandler.OnInterstitialAdEventListener eventListener = new InterstitialAdHandler.OnInterstitialAdEventListener() {
            @Override
            public void onAdLoadFailed(@NonNull AdError adError) {
                Toast.makeText(context, "Failed to load ads: " + adError.getErrorType().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onFinish() {
                Toast.makeText(context, "InterstitialAd finished", Toast.LENGTH_SHORT).show();
            }
        };

        final InterstitialAdHandler interstitialAdHandler = new InterstitialAdHandlerFactory().create(Constants.INTERSTITIAL_UNIT_ID, interstitialType);
//        interstitialAdHandler.show(context,
//                new InterstitialAdConfig.Builder()
//                        .build(),
//                eventListener);

        interstitialAdHandler.show(context, null, eventListener);

    }
}
