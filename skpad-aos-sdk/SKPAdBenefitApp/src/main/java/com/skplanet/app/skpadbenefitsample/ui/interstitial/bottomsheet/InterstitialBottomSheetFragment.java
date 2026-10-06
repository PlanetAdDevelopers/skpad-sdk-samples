package com.skplanet.app.skpadbenefitsample.ui.interstitial.bottomsheet;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.skplanet.app.skpadbenefitsample.Constants;
import com.skplanet.app.skpadbenefitsample.R;
import com.skplanet.app.skpadbenefitsample.data.OptionsPreferences;
import com.skplanet.skpad.benefit.core.ad.AdError;
import com.skplanet.skpad.benefit.presentation.interstitial.InterstitialAdConfig;
import com.skplanet.skpad.benefit.presentation.interstitial.InterstitialAdHandler;
import com.skplanet.skpad.benefit.presentation.interstitial.InterstitialAdHandlerFactory;

import java.lang.ref.WeakReference;

/**
 * Interstitial BottomSheet 광고 화면
 *
 * 광고 개수, 타이틀, UI 옵션 스위치로 InterstitialAdConfig를 구성하고
 * InterstitialAdHandler.Type.BottomSheet로 광고를 표시합니다.
 */
public class InterstitialBottomSheetFragment extends Fragment {

    @Nullable private OptionsPreferences optionsPrefs;

    @Nullable private TextView tvStatus;
    @Nullable private Button btnShow;
    @Nullable private EditText etAdCount;
    @Nullable private EditText etTitleText;
    @Nullable private SwitchCompat swTopIcon;
    @Nullable private SwitchCompat swShowInquiry;
    @Nullable private SwitchCompat swBgColor;
    @Nullable private SwitchCompat swTitleColor;
    @Nullable private SwitchCompat swCtaIcon;
    @Nullable private SwitchCompat swCtaBgColor;

    @Nullable private InterstitialAdHandler adHandler;

    // Cached ColorStateList for performance optimization
    @Nullable private ColorStateList ctaBackgroundColorStateList;
    @Nullable private ColorStateList ctaTextColorStateList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_interstitial_bottomsheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        optionsPrefs = new OptionsPreferences(requireContext());

        tvStatus = view.findViewById(R.id.tv_status);
        btnShow = view.findViewById(R.id.btn_show);
        etAdCount = view.findViewById(R.id.et_ad_count);
        etTitleText = view.findViewById(R.id.et_title_text);
        swTopIcon = view.findViewById(R.id.sw_top_icon);
        swShowInquiry = view.findViewById(R.id.sw_show_inquiry);
        swBgColor = view.findViewById(R.id.sw_bg_color);
        swTitleColor = view.findViewById(R.id.sw_title_color);
        swCtaIcon = view.findViewById(R.id.sw_cta_icon);
        swCtaBgColor = view.findViewById(R.id.sw_cta_bg_color);

        // 저장된 옵션 로드 및 저장 리스너 설정
        loadSavedOptions();
        setupOptionsSaveListeners();

        final String unitId = Constants.INTERSTITIAL_UNIT_ID;
        if (tvStatus != null) tvStatus.setText("Interstitial Unit: " + unitId);
        if (btnShow != null) btnShow.setOnClickListener(v -> showAd(unitId));
    }

    private void loadSavedOptions() {
        OptionsPreferences prefs = optionsPrefs;
        if (prefs == null) return;
        if (etAdCount != null) etAdCount.setText(String.valueOf(prefs.getBottomSheetAdCount()));
        if (swTopIcon != null) swTopIcon.setChecked(prefs.getBottomSheetTopIcon());
        if (swShowInquiry != null) swShowInquiry.setChecked(prefs.getBottomSheetShowInquiry());
        if (swBgColor != null) swBgColor.setChecked(prefs.getBottomSheetBgColor());
        if (swTitleColor != null) swTitleColor.setChecked(prefs.getBottomSheetTitleColor());
        if (swCtaIcon != null) swCtaIcon.setChecked(prefs.getBottomSheetCtaIcon());
        if (swCtaBgColor != null) swCtaBgColor.setChecked(prefs.getBottomSheetCtaBgColor());
    }

    private void setupOptionsSaveListeners() {
        OptionsPreferences prefs = optionsPrefs;
        if (prefs == null) return;
        if (etAdCount != null) {
            etAdCount.setOnFocusChangeListener((v, hasFocus) -> {
                Integer value = parseIntOrNull(etAdCount);
                if (!hasFocus && value != null) prefs.setBottomSheetAdCount(value);
            });
        }
        if (swTopIcon != null) swTopIcon.setOnCheckedChangeListener((b, c) -> prefs.setBottomSheetTopIcon(c));
        if (swShowInquiry != null) swShowInquiry.setOnCheckedChangeListener((b, c) -> prefs.setBottomSheetShowInquiry(c));
        if (swBgColor != null) swBgColor.setOnCheckedChangeListener((b, c) -> prefs.setBottomSheetBgColor(c));
        if (swTitleColor != null) swTitleColor.setOnCheckedChangeListener((b, c) -> prefs.setBottomSheetTitleColor(c));
        if (swCtaIcon != null) swCtaIcon.setOnCheckedChangeListener((b, c) -> prefs.setBottomSheetCtaIcon(c));
        if (swCtaBgColor != null) swCtaBgColor.setOnCheckedChangeListener((b, c) -> prefs.setBottomSheetCtaBgColor(c));
    }

    private void showAd(String unitId) {
        if (tvStatus != null) tvStatus.setText("광고 로딩 중...");

        Integer adCount = parseIntOrNull(etAdCount);
        String titleText = null;
        if (etTitleText != null && !etTitleText.getText().toString().trim().isEmpty()) {
            titleText = etTitleText.getText().toString();
        }

        InterstitialAdConfig.Builder builder = new InterstitialAdConfig.Builder()
                .showInquiryButton(isChecked(swShowInquiry));

        if (adCount != null) builder.adCount(adCount);
        if (titleText != null) builder.titleText(titleText);

        if (isChecked(swTopIcon)) {
            builder.topIcon(R.drawable.ic_notification_pop);
        }
        if (isChecked(swBgColor)) {
            builder.layoutBackgroundColor(R.color.demo_ad_background);
        }
        if (isChecked(swTitleColor)) {
            builder.textColor(R.color.demo_ad_text);
        }
        if (isChecked(swCtaIcon)) {
            builder.ctaRewardDrawable(R.drawable.ic_custom_reward)
                    .ctaParticipatedDrawable(R.drawable.ic_notification_pop);
        }
        if (isChecked(swCtaBgColor)) {
            builder.ctaViewBackgroundColorList(getCtaBackgroundColorStateList())
                    .ctaViewTextColor(getCtaTextColorStateList());
        }

        InterstitialAdHandler handler = new InterstitialAdHandlerFactory().create(unitId, InterstitialAdHandler.Type.BottomSheet);
        adHandler = handler;

        // WeakReference로 Fragment를 참조하여 메모리 누수 방지
        WeakReference<InterstitialBottomSheetFragment> fragmentRef = new WeakReference<>(this);
        handler.show(requireActivity(), builder.build(), new InterstitialAdHandler.OnInterstitialAdEventListener() {
            @Override
            public void onAdLoaded() {
                updateStatus(fragmentRef, "광고 표시 중");
            }

            @Override
            public void onAdLoadFailed(@Nullable AdError error) {
                updateStatus(fragmentRef, "로드 실패: " + (error != null ? error.getErrorType() : null));
            }

            @Override
            public void onFinish() {
                updateStatus(fragmentRef, "광고 종료. 다시 표시하려면 버튼을 누르세요.");
            }
        });
    }

    private static void updateStatus(WeakReference<InterstitialBottomSheetFragment> fragmentRef, String message) {
        InterstitialBottomSheetFragment fragment = fragmentRef.get();
        if (fragment == null || !fragment.isAdded() || fragment.getView() == null) return;
        if (fragment.tvStatus != null) fragment.tvStatus.setText(message);
    }

    private static boolean isChecked(@Nullable SwitchCompat sw) {
        return sw != null && sw.isChecked();
    }

    @Nullable
    private static Integer parseIntOrNull(@Nullable EditText editText) {
        if (editText == null) return null;
        try {
            return Integer.parseInt(editText.getText().toString().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private ColorStateList getCtaBackgroundColorStateList() {
        if (ctaBackgroundColorStateList != null) return ctaBackgroundColorStateList;
        Context ctx = getContext();
        if (ctx == null) return ColorStateList.valueOf(0);
        int[][] states = {
                new int[]{android.R.attr.state_enabled},
                new int[]{android.R.attr.state_pressed}
        };
        int[] colors = {
                ContextCompat.getColor(ctx, R.color.demo_ad_cta),
                ContextCompat.getColor(ctx, R.color.demo_ad_cta_pressed)
        };
        ctaBackgroundColorStateList = new ColorStateList(states, colors);
        return ctaBackgroundColorStateList;
    }

    private ColorStateList getCtaTextColorStateList() {
        if (ctaTextColorStateList != null) return ctaTextColorStateList;
        Context ctx = getContext();
        if (ctx == null) return ColorStateList.valueOf(0);
        int[][] states = {
                new int[]{android.R.attr.state_enabled},
                new int[]{android.R.attr.state_pressed}
        };
        int[] colors = {
                ContextCompat.getColor(ctx, R.color.demo_ad_cta_text),
                ContextCompat.getColor(ctx, R.color.demo_ad_cta_text)
        };
        ctaTextColorStateList = new ColorStateList(states, colors);
        return ctaTextColorStateList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tvStatus = null;
        btnShow = null;
        etAdCount = null;
        etTitleText = null;
        swTopIcon = null;
        swShowInquiry = null;
        swBgColor = null;
        swTitleColor = null;
        swCtaIcon = null;
        swCtaBgColor = null;

        adHandler = null;
        ctaBackgroundColorStateList = null;
        ctaTextColorStateList = null;
    }
}
