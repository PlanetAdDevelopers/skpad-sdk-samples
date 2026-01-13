package com.skplanet.app.skpadbenefitsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.skplanet.skpad.benefit.core.models.Ad;
import com.skplanet.skpad.benefit.core.models.Creative;
import com.skplanet.skpad.benefit.presentation.feed.ad.AdsAdapter;
import com.skplanet.skpad.benefit.presentation.media.CtaPresenter;
import com.skplanet.skpad.benefit.presentation.media.CtaView;
import com.skplanet.skpad.benefit.presentation.media.MediaView;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAd;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAdView;
import com.skplanet.skpad.benefit.presentation.reward.RewardResult;
import com.skplanet.skpad.benefit.presentation.video.VideoErrorStatus;
import com.skplanet.skpad.benefit.presentation.video.VideoEventListener;

import java.util.ArrayList;
import java.util.Collection;

public class CustomFeedAdsAdapter extends AdsAdapter<AdsAdapter.NativeAdViewHolder> {

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

        final Ad ad = nativeAd.getAd();
        final Creative.Type creativeType = ad.getCreative() == null ? null : ad.getCreative().getType();

        final MediaView mediaView = view.findViewById(com.skplanet.skpad.benefit.presentation.feed.R.id.mediaView);
        final LinearLayout titleLayout = view.findViewById(com.skplanet.skpad.benefit.presentation.feed.R.id.titleLayout);
        final TextView titleView = view.findViewById(com.skplanet.skpad.benefit.presentation.feed.R.id.textTitle);
        final ImageView iconView = view.findViewById(com.skplanet.skpad.benefit.presentation.feed.R.id.imageIcon);
        final TextView descriptionView = view.findViewById(com.skplanet.skpad.benefit.presentation.feed.R.id.textDescription);
        final CtaView ctaView = view.findViewById(com.skplanet.skpad.benefit.presentation.feed.R.id.ctaView);

        if (mediaView != null) {
            mediaView.setCreative(ad.getCreative());
            mediaView.setVideoEventListener(new VideoEventListener() {
                @Override
                public void onVideoStarted() {
                }

                @Override
                public void onResume() {
                }

                @Override
                public void onPause() {
                }

                @Override
                public void onReplay() {
                }

                @Override
                public void onLanding() {
                }

                @Override
                public void onError(@NonNull VideoErrorStatus videoErrorStatus, @Nullable String errorMessage) {
                    if (errorMessage != null) {
                        Toast.makeText(mediaView.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onVideoEnded() {
                }
            });
        }

        if (titleView != null) {
            titleView.setText(ad.getTitle());
        }

        int textVisibility = Creative.Type.IMAGE.equals(creativeType) ? View.GONE : View.VISIBLE;
        if (titleLayout != null) {
            titleLayout.setVisibility(textVisibility);
        }

        if (descriptionView != null) {
            descriptionView.setText(ad.getDescription());
            descriptionView.setVisibility(textVisibility);
        }

        if (iconView != null) {
            Glide.with(iconView.getContext()).load(ad.getIconUrl()).into(iconView);
        }

        final CtaPresenter ctaPresenter = new CtaPresenter(ctaView);
        ctaPresenter.bind(nativeAd);
        ctaView.setCtaTextSize(10);

        final Collection<View> clickableViews = new ArrayList<>();
        clickableViews.add(ctaView);
        clickableViews.add(mediaView);
        clickableViews.add(titleLayout);
        clickableViews.add(descriptionView);

        view.setAdInfoView(view.findViewById(com.skplanet.skpad.benefit.presentation.feed.R.id.information));
        view.setInquiryView(view.findViewById(com.skplanet.skpad.benefit.presentation.feed.R.id.inquiryButton));

        view.setMediaView(mediaView);
        view.setClickableViews(clickableViews);
        view.setNativeAd(nativeAd);
        view.addOnNativeAdEventListener(new NativeAdView.OnNativeAdEventListener() {
            @Override
            public void onImpressed(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
            }

            @Override
            public void onClicked(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
                ctaPresenter.bind(nativeAd);
            }

            @Override
            public void onRewardRequested(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
            }

            @Override
            public void onRewarded(@NonNull NativeAdView view, @NonNull NativeAd nativeAd, @Nullable RewardResult nativeAdRewardResult) {
            }

            @Override
            public void onParticipated(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
                ctaPresenter.bind(nativeAd);
            }
        });
    }

    @Override
    public NativeAdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final NativeAdView feedNativeAdView = (NativeAdView) inflater.inflate(R.layout.custom_feed_ad, parent, false);
        return new NativeAdViewHolder(feedNativeAdView);

    }
}
