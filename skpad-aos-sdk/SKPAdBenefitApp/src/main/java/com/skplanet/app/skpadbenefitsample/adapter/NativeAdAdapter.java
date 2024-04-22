package com.skplanet.app.skpadbenefitsample.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.skplanet.app.skpadbenefitsample.Constants;
import com.skplanet.app.skpadbenefitsample.R;
import com.skplanet.skpad.benefit.core.ad.AdError;
import com.skplanet.skpad.benefit.core.models.Ad;
import com.skplanet.skpad.benefit.core.models.Creative;
import com.skplanet.skpad.benefit.presentation.guide.AdInfoView;
import com.skplanet.skpad.benefit.presentation.guide.InquiryView;
import com.skplanet.skpad.benefit.presentation.media.CtaPresenter;
import com.skplanet.skpad.benefit.presentation.media.CtaView;
import com.skplanet.skpad.benefit.presentation.media.MediaView;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAd;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAdLoader;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAdView;
import com.skplanet.skpad.benefit.presentation.reward.RewardResult;
import com.skplanet.skpad.benefit.presentation.video.VideoErrorStatus;
import com.skplanet.skpad.benefit.presentation.video.VideoEventListener;

import java.util.ArrayList;
import java.util.List;

public class NativeAdAdapter {

    private Context context;

    public NativeAdAdapter(Context context) {
        this.context = context;
    }

    public void loadAds(View nativeAdLayout) {

        NativeAdLoader nativeAdLoader = new NativeAdLoader(Constants.NATIVEAD_UNIT_ID);
        nativeAdLoader.loadAd(new NativeAdLoader.OnAdLoadedListener() {
            @Override
            public void onLoadError(@NonNull AdError error) {
                Toast.makeText(context, "Failed to load native ad! error message:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(@NonNull NativeAd nativeAd) {
                populateAd(context, nativeAd, nativeAdLayout);
            }
        });
    }

    private void populateAd(Context context, final NativeAd nativeAd, View nativeAdLayout) {
        final Ad ad = nativeAd.getAd();
        final Creative.Type creativeType = ad.getCreative() == null ? null : ad.getCreative().getType();

        final TextView cardTitleTextView = nativeAdLayout.findViewById(R.id.card_title_text);
        final NativeAdView nativeAdView = nativeAdLayout.findViewById(R.id.native_ad_view);
        final MediaView mediaView = nativeAdLayout.findViewById(R.id.ad_media_view);
        final TextView titleTextView = nativeAdLayout.findViewById(R.id.ad_title_text);
        final TextView descriptionTextView = nativeAdLayout.findViewById(R.id.ad_description_text);
        final ImageView iconImageView = nativeAdLayout.findViewById(R.id.ad_icon_image);
        final CtaView ctaView = nativeAdLayout.findViewById(R.id.ad_cta_view);
        final AdInfoView informationView = nativeAdLayout.findViewById(R.id.information);
        final InquiryView inquiryButon = nativeAdLayout.findViewById(R.id.inquiryButton);
        cardTitleTextView.setText(R.string.native_ad_title);
        cardTitleTextView.setTextSize(14);

        mediaView.setCreative(ad.getCreative());
        titleTextView.setText(ad.getTitle());
        descriptionTextView.setText(ad.getDescription());
        Glide.with(context).load(ad.getIconUrl()).into(iconImageView);
        final CtaPresenter ctaPresenter = new CtaPresenter(ctaView);
        ctaPresenter.bind(nativeAd);

        final List<View> clickableViews = new ArrayList<>();
        clickableViews.add(ctaView);
        clickableViews.add(mediaView);

        if (Creative.Type.IMAGE.equals(creativeType)) {
            titleTextView.setVisibility(View.GONE);
            iconImageView.setVisibility(View.GONE);
            descriptionTextView.setVisibility(View.GONE);
        } else {
            titleTextView.setVisibility(View.VISIBLE);
            iconImageView.setVisibility(View.VISIBLE);
            descriptionTextView.setVisibility(View.VISIBLE);
            clickableViews.add(titleTextView);
            clickableViews.add(iconImageView);
            clickableViews.add(descriptionTextView);
        }

        nativeAdView.setMediaView(mediaView);
        nativeAdView.setClickableViews(clickableViews);
        nativeAdView.setNativeAd(nativeAd);
        nativeAdView.setAdInfoView(informationView);
        nativeAdView.setInquiryView(inquiryButon);

        // 만 14세 이상인 경우에만 VOC(문의하기) 기능을 노출해야합니다.
        inquiryButon.setVisibility(Constants.OLDER_14YEAR ? View.VISIBLE : View.INVISIBLE);

        mediaView.setVideoEventListener(new VideoEventListener() {
            @Override
            public void onVideoStarted() {
                Toast.makeText(mediaView.getContext(), "onVideoStarted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResume() {
                Toast.makeText(mediaView.getContext(), "onVideoResume", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPause() {
                Toast.makeText(mediaView.getContext(), "onVideoPause", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReplay() {
                Toast.makeText(mediaView.getContext(), "onVideoReplay", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLanding() {
                Toast.makeText(mediaView.getContext(), "onLanding", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull VideoErrorStatus videoErrorStatus, @Nullable String errorMessage) {
                if (errorMessage != null) {
                    Toast.makeText(mediaView.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onVideoEnded() {
                Toast.makeText(mediaView.getContext(), "onVideoEnded", Toast.LENGTH_SHORT).show();
            }
        });

        nativeAdView.addOnNativeAdEventListener(new NativeAdView.OnNativeAdEventListener() {
            @Override
            public void onImpressed(final @NonNull NativeAdView view, final @NonNull NativeAd nativeAd) {
                Toast.makeText(context, "onImpressed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClicked(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
                Toast.makeText(context, "onClicked", Toast.LENGTH_SHORT).show();
                ctaPresenter.bind(nativeAd);
            }

            @Override
            public void onRewardRequested(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
                Toast.makeText(context, "onRewardRequested", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewarded(@NonNull NativeAdView view, @NonNull NativeAd nativeAd, @Nullable RewardResult rewardResult) {
                Toast.makeText(context, "onRewarded: " + rewardResult, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onParticipated(final @NonNull NativeAdView view, final @NonNull NativeAd nativeAd) {
                Toast.makeText(context, "onParticipated", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ctaPresenter.bind(nativeAd);
                    }
                }, 1000);
            }
        });

        nativeAdLayout.setVisibility(View.VISIBLE);
    }
}
