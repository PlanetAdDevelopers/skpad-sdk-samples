package com.skplanet.app.skpadbenefitsample.ui.carousel;

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
import com.skplanet.skpad.benefit.presentation.feed.carousel.NativeAdCarouselConfig;
import com.skplanet.skpad.benefit.presentation.feed.carousel.NativeAdCarouselView;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAd;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAdLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 캐러셀(NativeAdCarouselView) 광고를 표시하는 Fragment
 *
 * NativeAdLoader.loadAds()로 여러 개의 광고를 받아 NativeAdCarouselView에 표시합니다.
 * 개수, 자동 슬라이드 간격, 좌우 노출 폭, 간격, Loop, 인디케이터, 자동 슬라이드 옵션을 지원합니다.
 */
public class CarouselAdFragment extends Fragment {

    @Nullable private OptionsPreferences optionsPrefs;
    @Nullable private TextView tvStatus;
    @Nullable private TextView tvError;
    @Nullable private Button btnReload;
    @Nullable private NativeAdCarouselView carouselView;

    // Option views
    @Nullable private EditText etCount;
    @Nullable private EditText etDuration;
    @Nullable private EditText etSidePeek;
    @Nullable private EditText etItemSpacing;
    @Nullable private SwitchCompat swLoop;
    @Nullable private SwitchCompat swIndicator;
    @Nullable private SwitchCompat swAutoPaging;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carousel_ad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        optionsPrefs = new OptionsPreferences(requireContext());

        tvStatus = view.findViewById(R.id.tv_status);
        tvError = view.findViewById(R.id.tv_error);
        btnReload = view.findViewById(R.id.btn_reload);
        carouselView = view.findViewById(R.id.carousel_view);

        etCount = view.findViewById(R.id.et_count);
        etDuration = view.findViewById(R.id.et_duration);
        etSidePeek = view.findViewById(R.id.et_side_peek);
        etItemSpacing = view.findViewById(R.id.et_item_spacing);
        swLoop = view.findViewById(R.id.sw_loop);
        swIndicator = view.findViewById(R.id.sw_indicator);
        swAutoPaging = view.findViewById(R.id.sw_autopaging);

        // 저장된 옵션 로드 및 저장 리스너 설정
        loadSavedOptions();
        setupOptionsSaveListeners();

        if (btnReload != null) {
            btnReload.setOnClickListener(v -> loadAds());
            btnReload.setVisibility(View.VISIBLE);
        }
    }

    private void loadSavedOptions() {
        OptionsPreferences prefs = optionsPrefs;
        if (prefs == null) return;
        if (etCount != null) etCount.setText(String.valueOf(prefs.getCarouselCount()));
        if (etDuration != null) etDuration.setText(String.valueOf(prefs.getCarouselDuration()));
        if (etSidePeek != null) etSidePeek.setText(String.valueOf(prefs.getCarouselSidePeek()));
        if (etItemSpacing != null) etItemSpacing.setText(String.valueOf(prefs.getCarouselItemSpacing()));
        if (swLoop != null) swLoop.setChecked(prefs.getCarouselLoop());
        if (swIndicator != null) swIndicator.setChecked(prefs.getCarouselIndicator());
        if (swAutoPaging != null) swAutoPaging.setChecked(prefs.getCarouselAutoPaging());
    }

    private void setupOptionsSaveListeners() {
        if (etCount != null) {
            etCount.setOnFocusChangeListener((v, hasFocus) -> {
                Integer value = parseIntOrNull(etCount);
                if (!hasFocus && value != null && optionsPrefs != null) optionsPrefs.setCarouselCount(value);
            });
        }
        if (etDuration != null) {
            etDuration.setOnFocusChangeListener((v, hasFocus) -> {
                Integer value = parseIntOrNull(etDuration);
                if (!hasFocus && value != null && optionsPrefs != null) optionsPrefs.setCarouselDuration(value);
            });
        }
        if (etSidePeek != null) {
            etSidePeek.setOnFocusChangeListener((v, hasFocus) -> {
                Integer value = parseIntOrNull(etSidePeek);
                if (!hasFocus && value != null && optionsPrefs != null) optionsPrefs.setCarouselSidePeek(value);
            });
        }
        if (etItemSpacing != null) {
            etItemSpacing.setOnFocusChangeListener((v, hasFocus) -> {
                Integer value = parseIntOrNull(etItemSpacing);
                if (!hasFocus && value != null && optionsPrefs != null) optionsPrefs.setCarouselItemSpacing(value);
            });
        }
        if (swLoop != null) {
            swLoop.setOnCheckedChangeListener((b, isChecked) -> {
                if (optionsPrefs != null) optionsPrefs.setCarouselLoop(isChecked);
            });
        }
        if (swIndicator != null) {
            swIndicator.setOnCheckedChangeListener((b, isChecked) -> {
                if (optionsPrefs != null) optionsPrefs.setCarouselIndicator(isChecked);
            });
        }
        if (swAutoPaging != null) {
            swAutoPaging.setOnCheckedChangeListener((b, isChecked) -> {
                if (optionsPrefs != null) optionsPrefs.setCarouselAutoPaging(isChecked);
            });
        }
    }

    private void loadAds() {
        // 캐러셀은 Native Unit ID를 사용합니다.
        final String unitId = Constants.NATIVEAD_UNIT_ID;

        int count = parseIntOrDefault(etCount, OptionsPreferences.DEFAULT_CAROUSEL_COUNT);
        final int duration = parseIntOrDefault(etDuration, 3000);
        final int sidePeek = parseIntOrDefault(etSidePeek, 0);
        final int itemSpacing = parseIntOrDefault(etItemSpacing, 0);

        if (tvStatus != null) tvStatus.setText("광고 로딩 중... (Unit: " + unitId + ")");
        if (tvError != null) tvError.setVisibility(View.GONE);
        if (btnReload != null) btnReload.setVisibility(View.GONE);
        if (carouselView != null) carouselView.setVisibility(View.INVISIBLE);

        NativeAdLoader loader = new NativeAdLoader(unitId);
        loader.loadAds(new NativeAdLoader.OnAdsLoadedListener() {
            @Override
            public void onLoadError(@NonNull AdError error) {
                if (!isAdded()) return;
                requireActivity().runOnUiThread(() -> showError("광고 로드 실패: " + error.getErrorType()));
            }

            @Override
            public void onAdsLoaded(@NonNull Collection<NativeAd> nativeAds) {
                if (!isAdded()) return;
                List<NativeAd> ads = new ArrayList<>();
                for (NativeAd ad : nativeAds) {
                    if (ad != null) ads.add(ad);
                }
                requireActivity().runOnUiThread(() -> bindCarousel(ads, duration, sidePeek, itemSpacing));
            }
        }, count);
    }

    private void bindCarousel(List<NativeAd> nativeAds, int duration, int sidePeek, int itemSpacing) {
        if (nativeAds.isEmpty()) {
            showError("표시할 광고가 없습니다.");
            return;
        }

        NativeAdCarouselConfig.Builder configBuilder = new NativeAdCarouselConfig.Builder()
                .setLoop(swLoop == null || swLoop.isChecked())
                .setPageIndex(swIndicator == null || swIndicator.isChecked())
                .setAutoPaging(swAutoPaging != null && swAutoPaging.isChecked())
                .setAutoPagingDuration(duration)
                .setSideItemPeek(sidePeek)
                .setItemSpacing(itemSpacing);

        if (carouselView != null) {
            carouselView.setNativeAdList(nativeAds, configBuilder.build());
            carouselView.setVisibility(View.VISIBLE);
        }
        if (tvStatus != null) tvStatus.setText("광고 로드 완료 (" + nativeAds.size() + "개)");
        if (tvError != null) tvError.setVisibility(View.GONE);
        if (btnReload != null) btnReload.setVisibility(View.VISIBLE);
    }

    private void showError(String message) {
        if (tvStatus != null) tvStatus.setText("오류");
        if (tvError != null) {
            tvError.setText(message);
            tvError.setVisibility(View.VISIBLE);
        }
        if (carouselView != null) carouselView.setVisibility(View.INVISIBLE);
        if (btnReload != null) btnReload.setVisibility(View.VISIBLE);
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

    private static int parseIntOrDefault(@Nullable EditText editText, int defaultValue) {
        Integer value = parseIntOrNull(editText);
        return value != null ? value : defaultValue;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 리스너 제거
        if (swLoop != null) swLoop.setOnCheckedChangeListener(null);
        if (swIndicator != null) swIndicator.setOnCheckedChangeListener(null);
        if (swAutoPaging != null) swAutoPaging.setOnCheckedChangeListener(null);
        if (etCount != null) etCount.setOnFocusChangeListener(null);
        if (etDuration != null) etDuration.setOnFocusChangeListener(null);
        if (etSidePeek != null) etSidePeek.setOnFocusChangeListener(null);
        if (etItemSpacing != null) etItemSpacing.setOnFocusChangeListener(null);
        if (btnReload != null) btnReload.setOnClickListener(null);

        tvStatus = null;
        tvError = null;
        btnReload = null;
        carouselView = null;
        etCount = null;
        etDuration = null;
        etSidePeek = null;
        etItemSpacing = null;
        swLoop = null;
        swIndicator = null;
        swAutoPaging = null;
        optionsPrefs = null;
    }
}
