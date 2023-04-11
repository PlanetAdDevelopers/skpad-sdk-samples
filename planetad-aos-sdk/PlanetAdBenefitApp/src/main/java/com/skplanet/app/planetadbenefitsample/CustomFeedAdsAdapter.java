package com.skplanet.app.planetadbenefitsample;

import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.skplanet.skpad.benefit.presentation.feed.ad.DefaultAdsAdapter;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAd;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAdView;
import com.skplanet.skpad.benefit.presentation.notification.RewardNotificationConfig;
import com.skplanet.skpad.benefit.presentation.notification.SKPAdPush;
import com.skplanet.skpad.benefit.presentation.reward.RewardResult;

public class CustomFeedAdsAdapter extends DefaultAdsAdapter {

    private final NativeAdView.OnNativeAdEventListener listener = new NativeAdView.OnNativeAdEventListener() {
        @Override
        public void onImpressed(final @NonNull NativeAdView view, final @NonNull NativeAd nativeAd) {
            Toast.makeText(view.getContext(), "onImpressed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onClicked(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
            Toast.makeText(view.getContext(), "onClicked", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardRequested(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
            Toast.makeText(view.getContext(), "onRewardRequested", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewarded(@NonNull NativeAdView view, @NonNull NativeAd nativeAd, @Nullable RewardResult nativeAdRewardResult) {
            Toast.makeText(view.getContext(), "onRewarded: " + nativeAdRewardResult, Toast.LENGTH_SHORT).show();
            // TODO RewardNotificationConfig set config once
            if (nativeAdRewardResult == RewardResult.SUCCESS) {
                SKPAdPush.showRewardNotification(view.getContext(), new RewardNotificationConfig.Builder().build(), nativeAd.getAd().getReward());
            }
        }

        @Override
        public void onParticipated(final @NonNull NativeAdView view, final @NonNull NativeAd nativeAd) {
            Toast.makeText(view.getContext(), "onParticipated", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onBindViewHolder(NativeAdViewHolder holder, NativeAd nativeAd) {
        super.onBindViewHolder(holder, nativeAd);
        final NativeAdView view = (NativeAdView) holder.itemView;
        view.addOnNativeAdEventListener(listener);
    }

    @Override
    public NativeAdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }
}
