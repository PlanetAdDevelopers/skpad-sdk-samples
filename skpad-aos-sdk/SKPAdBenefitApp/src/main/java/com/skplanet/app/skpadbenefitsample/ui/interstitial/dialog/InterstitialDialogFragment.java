package com.skplanet.app.skpadbenefitsample.ui.interstitial.dialog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.skplanet.app.skpadbenefitsample.Constants;
import com.skplanet.app.skpadbenefitsample.R;
import com.skplanet.app.skpadbenefitsample.data.OptionsPreferences;
import com.skplanet.app.skpadbenefitsample.databinding.FragmentInterstitialDialogBinding;
import com.skplanet.skpad.benefit.core.ad.AdError;
import com.skplanet.skpad.benefit.presentation.interstitial.InterstitialAdConfig;
import com.skplanet.skpad.benefit.presentation.interstitial.InterstitialAdHandler;
import com.skplanet.skpad.benefit.presentation.interstitial.InterstitialAdHandlerFactory;

import java.lang.ref.WeakReference;

/**
 * Interstitial Dialog 광고 화면 (ViewBinding 사용 예시)
 *
 * 옵션 스위치(상단 아이콘, 문의하기, 배경/타이틀 색상, CTA 아이콘/배경색)와 타이틀 텍스트로
 * InterstitialAdConfig를 구성하고 InterstitialAdHandler.Type.Dialog로 광고를 표시합니다.
 */
public class InterstitialDialogFragment extends Fragment {

    private static final int DEFAULT_COLOR = 0;
    private static final String MSG_LOADING = "광고 로딩 중...";
    private static final String MSG_SHOWING = "광고 표시 중";
    private static final String MSG_FINISHED = "광고 종료. 다시 표시하려면 버튼을 누르세요.";
    private static final String MSG_INVALID_FRAGMENT = "Fragment가 유효하지 않습니다.";
    private static final String MSG_LOAD_FAILED_PREFIX = "로드 실패: ";

    @Nullable private FragmentInterstitialDialogBinding binding;
    @Nullable private OptionsPreferences optionsPrefs;
    @Nullable private InterstitialAdHandler adHandler;
    @Nullable private ColorStateList ctaBackgroundColorStateList;
    @Nullable private ColorStateList ctaTextColorStateList;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentInterstitialDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        optionsPrefs = new OptionsPreferences(requireContext());

        // 저장된 옵션 로드 및 저장 리스너 설정
        loadSavedOptions();
        setupOptionsSaveListeners();

        setupAdButton();
    }

    private void setupAdButton() {
        FragmentInterstitialDialogBinding b = binding;
        if (b == null) return;
        b.tvStatus.setText("Interstitial Unit: " + Constants.INTERSTITIAL_UNIT_ID);
        b.btnShow.setOnClickListener(v -> showAd(Constants.INTERSTITIAL_UNIT_ID));
    }

    private void loadSavedOptions() {
        FragmentInterstitialDialogBinding b = binding;
        OptionsPreferences prefs = optionsPrefs;
        if (b == null || prefs == null) return;
        b.swTopIcon.setChecked(prefs.getDialogTopIcon());
        b.swShowInquiry.setChecked(prefs.getDialogShowInquiry());
        b.swBgColor.setChecked(prefs.getDialogBgColor());
        b.swTitleColor.setChecked(prefs.getDialogTitleColor());
        b.swCtaIcon.setChecked(prefs.getDialogCtaIcon());
        b.swCtaBgColor.setChecked(prefs.getDialogCtaBgColor());
    }

    private void setupOptionsSaveListeners() {
        FragmentInterstitialDialogBinding b = binding;
        OptionsPreferences prefs = optionsPrefs;
        if (b == null || prefs == null) return;
        b.swTopIcon.setOnCheckedChangeListener((v, c) -> prefs.setDialogTopIcon(c));
        b.swShowInquiry.setOnCheckedChangeListener((v, c) -> prefs.setDialogShowInquiry(c));
        b.swBgColor.setOnCheckedChangeListener((v, c) -> prefs.setDialogBgColor(c));
        b.swTitleColor.setOnCheckedChangeListener((v, c) -> prefs.setDialogTitleColor(c));
        b.swCtaIcon.setOnCheckedChangeListener((v, c) -> prefs.setDialogCtaIcon(c));
        b.swCtaBgColor.setOnCheckedChangeListener((v, c) -> prefs.setDialogCtaBgColor(c));
    }

    /** Fragment가 유효할 때만 상태 텍스트를 갱신합니다. */
    private void updateStatusSafely(String message) {
        if (isAdded() && binding != null) {
            binding.tvStatus.setText(message);
        }
    }

    /** 스위치 상태에 따라 UI 커스터마이징 옵션을 적용합니다. */
    private void applyUiOptions(InterstitialAdConfig.Builder builder, @NonNull FragmentInterstitialDialogBinding b,
                                @Nullable String titleText) {
        builder.showInquiryButton(b.swShowInquiry.isChecked());

        if (titleText != null) {
            builder.titleText(titleText);
        }
        if (b.swTopIcon.isChecked()) {
            builder.topIcon(R.drawable.ic_notification_pop);
        }
        if (b.swBgColor.isChecked()) {
            builder.layoutBackgroundColor(R.color.demo_ad_background);
        }
        if (b.swTitleColor.isChecked()) {
            builder.textColor(R.color.demo_ad_text);
        }
        if (b.swCtaIcon.isChecked()) {
            builder.ctaRewardDrawable(R.drawable.ic_custom_reward);
            builder.ctaParticipatedDrawable(R.drawable.ic_notification_pop);
        }
        if (b.swCtaBgColor.isChecked()) {
            builder.ctaViewBackgroundColorList(getCtaBackgroundColorStateList());
            builder.ctaViewTextColor(getCtaTextColorStateList());
        }
    }

    private void showAd(String unitId) {
        FragmentInterstitialDialogBinding b = binding;
        if (b == null) return;

        updateStatusSafely(MSG_LOADING);

        CharSequence input = b.etTitleText.getText();
        String titleText = (input != null && !input.toString().trim().isEmpty()) ? input.toString() : null;

        InterstitialAdConfig.Builder builder = new InterstitialAdConfig.Builder();
        applyUiOptions(builder, b, titleText);
        InterstitialAdConfig config = builder.build();

        FragmentActivity currentActivity = getActivity();
        if (currentActivity == null || !isAdded()) {
            updateStatusSafely(MSG_INVALID_FRAGMENT);
            return;
        }

        // WeakReference로 Fragment를 참조하여 메모리 누수 방지
        WeakReference<InterstitialDialogFragment> fragmentRef = new WeakReference<>(this);
        adHandler = new InterstitialAdHandlerFactory().create(unitId, InterstitialAdHandler.Type.Dialog);
        adHandler.show(currentActivity, config, createAdEventListener(fragmentRef));
    }

    private static InterstitialAdHandler.OnInterstitialAdEventListener createAdEventListener(
            WeakReference<InterstitialDialogFragment> fragmentRef) {
        return new InterstitialAdHandler.OnInterstitialAdEventListener() {
            @Override
            public void onAdLoaded() {
                InterstitialDialogFragment fragment = fragmentRef.get();
                if (fragment != null) fragment.updateStatusSafely(MSG_SHOWING);
            }

            @Override
            public void onAdLoadFailed(@Nullable AdError error) {
                InterstitialDialogFragment fragment = fragmentRef.get();
                if (fragment != null) {
                    fragment.updateStatusSafely(MSG_LOAD_FAILED_PREFIX + (error != null ? error.getErrorType() : null));
                }
            }

            @Override
            public void onFinish() {
                InterstitialDialogFragment fragment = fragmentRef.get();
                if (fragment != null) fragment.updateStatusSafely(MSG_FINISHED);
            }
        };
    }

    private static ColorStateList createColorStateList(int enabledColor, int pressedColor) {
        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed}
                },
                new int[]{enabledColor, pressedColor}
        );
    }

    private ColorStateList getCtaBackgroundColorStateList() {
        if (ctaBackgroundColorStateList != null) return ctaBackgroundColorStateList;
        Context ctx = getContext();
        if (ctx == null) return ColorStateList.valueOf(DEFAULT_COLOR);
        ctaBackgroundColorStateList = createColorStateList(
                ContextCompat.getColor(ctx, R.color.demo_ad_cta),       // Enabled state color
                ContextCompat.getColor(ctx, R.color.demo_ad_cta_pressed)   // Pressed state color
        );
        return ctaBackgroundColorStateList;
    }

    private ColorStateList getCtaTextColorStateList() {
        if (ctaTextColorStateList != null) return ctaTextColorStateList;
        Context ctx = getContext();
        if (ctx == null) return ColorStateList.valueOf(DEFAULT_COLOR);
        int textColor = ContextCompat.getColor(ctx, R.color.demo_ad_cta_text);
        ctaTextColorStateList = createColorStateList(textColor, textColor);
        return ctaTextColorStateList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FragmentInterstitialDialogBinding b = binding;
        if (b != null) {
            b.swTopIcon.setOnCheckedChangeListener(null);
            b.swShowInquiry.setOnCheckedChangeListener(null);
            b.swBgColor.setOnCheckedChangeListener(null);
            b.swTitleColor.setOnCheckedChangeListener(null);
            b.swCtaIcon.setOnCheckedChangeListener(null);
            b.swCtaBgColor.setOnCheckedChangeListener(null);
            b.btnShow.setOnClickListener(null);
        }
        binding = null;
        adHandler = null;
        ctaBackgroundColorStateList = null;
        ctaTextColorStateList = null;
        optionsPrefs = null;
    }
}
