package com.skplanet.app.skpadbenefitsample.ui.interstitial.fullscreen;

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
import androidx.fragment.app.Fragment;

import com.skplanet.app.skpadbenefitsample.Constants;
import com.skplanet.app.skpadbenefitsample.R;
import com.skplanet.app.skpadbenefitsample.data.OptionsPreferences;
import com.skplanet.skpad.benefit.core.ad.AdError;
import com.skplanet.skpad.benefit.presentation.interstitial.InterstitialAdConfig;
import com.skplanet.skpad.benefit.presentation.interstitial.InterstitialAdDataManager;
import com.skplanet.skpad.benefit.presentation.interstitial.InterstitialAdHandler;
import com.skplanet.skpad.benefit.presentation.interstitial.InterstitialAdHandlerFactory;
import com.skplanet.skpad.benefit.presentation.interstitial.fullscreen.InterstitialAdFullScreenHandler;

import java.lang.ref.WeakReference;

/**
 * Interstitial Fullscreen 광고 화면
 *
 * 문의하기, 타이틀 색상, 커스텀 어댑터, 커스텀 에러뷰 옵션으로
 * InterstitialAdConfig를 구성하고 InterstitialAdHandler.Type.FullScreen으로 광고를 표시합니다.
 */
public class InterstitialFullscreenFragment extends Fragment {

    @Nullable private OptionsPreferences optionsPrefs;

    @Nullable private TextView tvStatus;
    @Nullable private Button btnShow;
    @Nullable private EditText etTitleText;
    @Nullable private SwitchCompat swShowInquiry;
    @Nullable private SwitchCompat swTitleColor;
    @Nullable private SwitchCompat swCustomAdapter;
    @Nullable private SwitchCompat swErrorView;

    @Nullable private InterstitialAdHandler adHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_interstitial_fullscreen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        optionsPrefs = new OptionsPreferences(requireContext());

        tvStatus = view.findViewById(R.id.tv_status);
        btnShow = view.findViewById(R.id.btn_show);
        etTitleText = view.findViewById(R.id.et_title_text);
        swShowInquiry = view.findViewById(R.id.sw_show_inquiry);
        swTitleColor = view.findViewById(R.id.sw_title_color);
        swCustomAdapter = view.findViewById(R.id.sw_custom_adapter);
        swErrorView = view.findViewById(R.id.sw_error_view);

        // 저장된 옵션 복원 및 자동 저장 리스너 설정
        loadSavedOptions();
        setupOptionsSaveListeners();

        final String unitId = Constants.INTERSTITIAL_UNIT_ID;
        if (tvStatus != null) tvStatus.setText("Interstitial Unit: " + unitId);
        if (btnShow != null) btnShow.setOnClickListener(v -> showAd(unitId));
    }

    private void loadSavedOptions() {
        OptionsPreferences prefs = optionsPrefs;
        if (prefs == null) return;
        if (swShowInquiry != null) swShowInquiry.setChecked(prefs.getFullscreenShowInquiry());
        if (swTitleColor != null) swTitleColor.setChecked(prefs.getFullscreenTitleColor());
        if (swCustomAdapter != null) swCustomAdapter.setChecked(prefs.getFullscreenCustomAdapter());
        if (swErrorView != null) swErrorView.setChecked(prefs.getFullscreenErrorView());
    }

    private void setupOptionsSaveListeners() {
        OptionsPreferences prefs = optionsPrefs;
        if (prefs == null) return;
        if (swShowInquiry != null) swShowInquiry.setOnCheckedChangeListener((b, c) -> prefs.setFullscreenShowInquiry(c));
        if (swTitleColor != null) swTitleColor.setOnCheckedChangeListener((b, c) -> prefs.setFullscreenTitleColor(c));
        if (swCustomAdapter != null) swCustomAdapter.setOnCheckedChangeListener((b, c) -> prefs.setFullscreenCustomAdapter(c));
        if (swErrorView != null) swErrorView.setOnCheckedChangeListener((b, c) -> prefs.setFullscreenErrorView(c));
    }

    private void showAd(String unitId) {
        if (tvStatus != null) tvStatus.setText("광고 로딩 중...");

        InterstitialAdConfig.Builder builder = new InterstitialAdConfig.Builder()
                .showInquiryButton(isChecked(swShowInquiry));

        if (etTitleText != null && !etTitleText.getText().toString().trim().isEmpty()) {
            builder.titleText(etTitleText.getText().toString());
        }
        if (isChecked(swTitleColor)) {
            builder.textColor(R.color.demo_ad_text);
        }
        if (isChecked(swCustomAdapter)) {
            builder.adsAdapterClass(CustomInterstitialFullScreenAdsAdapter.class);
        }
        if (isChecked(swErrorView)) {
            builder.errorViewHolderClass(CustomInterstitialFullScreenErrorViewHolder.class);
        }

        InterstitialAdHandler handler = new InterstitialAdHandlerFactory().create(unitId, InterstitialAdHandler.Type.FullScreen);
        adHandler = handler;

        if (isChecked(swErrorView)) {
            // [샘플 앱 전용 – 실제 앱에서 사용하지 마세요]
            // SDK 내부 광고 데이터를 강제로 비워 Custom Error View를 바로 확인하기 위한 테스트 코드입니다.
            // 실제 앱에서는 errorViewHolderClass(...)만 등록하면 광고 로드 실패 시 SDK가 에러 화면을 표시합니다.
            InterstitialAdDataManager.getInstance().setNativeAds(unitId, null);
            if (handler instanceof InterstitialAdFullScreenHandler) {
                ((InterstitialAdFullScreenHandler) handler).showErrorLayout(requireActivity(), builder.build());
            }
            if (tvStatus != null) tvStatus.setText("에러뷰 표시 중");
        } else {
            // 일반 모드: 실제 광고 표시
            // SDK 싱글턴(InterstitialAdDataManager)이 리스너를 보유해도
            // Fragment가 GC될 수 있도록 WeakReference로 참조합니다.
            WeakReference<InterstitialFullscreenFragment> fragmentRef = new WeakReference<>(this);
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
    }

    private static void updateStatus(WeakReference<InterstitialFullscreenFragment> fragmentRef, String message) {
        InterstitialFullscreenFragment fragment = fragmentRef.get();
        if (fragment == null || !fragment.isAdded() || fragment.getView() == null) return;
        if (fragment.tvStatus != null) fragment.tvStatus.setText(message);
    }

    private static boolean isChecked(@Nullable SwitchCompat sw) {
        return sw != null && sw.isChecked();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SwitchCompat[] switches = {swShowInquiry, swTitleColor, swCustomAdapter, swErrorView};
        for (SwitchCompat sw : switches) {
            if (sw != null) sw.setOnCheckedChangeListener(null);
        }
        if (btnShow != null) btnShow.setOnClickListener(null);

        tvStatus = null;
        btnShow = null;
        etTitleText = null;
        swShowInquiry = null;
        swTitleColor = null;
        swCustomAdapter = null;
        swErrorView = null;

        adHandler = null;
        optionsPrefs = null;
    }
}
