package com.skplanet.app.skpadbenefitsample.ui.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.skplanet.app.skpadbenefitsample.Constants;
import com.skplanet.app.skpadbenefitsample.R;
import com.skplanet.app.skpadbenefitsample.data.OptionsPreferences;
import com.skplanet.skpad.benefit.presentation.feed.FeedConfig;
import com.skplanet.skpad.benefit.presentation.feed.FeedHandler;
import com.skplanet.skpad.benefit.presentation.feed.header.DefaultFeedHeaderViewAdapter;

/**
 * Feed 광고 화면
 *
 * 옵션 스위치(문의하기, 2열 그리드) 값으로 FeedConfig를 구성하고
 * FeedHandler.startFeedActivity()로 Feed를 엽니다.
 * 스위치 값은 OptionsPreferences에 저장되어 다음 실행 시에도 유지됩니다.
 */
public class FeedAdFragment extends Fragment {

    @Nullable private OptionsPreferences optionsPrefs;

    // Option switches
    @Nullable private SwitchCompat swInquiry;
    @Nullable private SwitchCompat swGrid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_ad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        optionsPrefs = new OptionsPreferences(requireContext());

        TextView tvStatus = view.findViewById(R.id.tv_status);
        Button btnShow = view.findViewById(R.id.btn_show);

        swInquiry = view.findViewById(R.id.sw_inquiry);
        swGrid = view.findViewById(R.id.sw_grid_switch);

        // 저장된 옵션 로드 및 저장 리스너 설정
        loadSavedOptions();
        setupOptionsSaveListeners();

        final String unitId = Constants.FEED_UNIT_ID;
        tvStatus.setText("Feed Unit: " + unitId);

        btnShow.setOnClickListener(v -> {
            FeedConfig.Builder builder = new FeedConfig.Builder(requireContext(), unitId)
                    .showInquiryButton(isChecked(swInquiry))
                    .autoLoadingEnabled(false)
                    .setGridMode(isChecked(swGrid))
                    .feedHeaderViewAdapterClass(DefaultFeedHeaderViewAdapter.class);

            new FeedHandler(builder.build()).startFeedActivity(requireActivity());
        });
    }

    private static boolean isChecked(@Nullable SwitchCompat sw) {
        return sw != null && sw.isChecked();
    }

    private void loadSavedOptions() {
        OptionsPreferences prefs = optionsPrefs;
        if (prefs == null) return;
        if (swInquiry != null) swInquiry.setChecked(prefs.getFeedInquiry());
        if (swGrid != null) swGrid.setChecked(prefs.getFeedGridLayout());
    }

    private void setupOptionsSaveListeners() {
        OptionsPreferences prefs = optionsPrefs;
        if (prefs == null) return;
        if (swInquiry != null) swInquiry.setOnCheckedChangeListener((b, c) -> prefs.setFeedInquiry(c));
        if (swGrid != null) swGrid.setOnCheckedChangeListener((b, c) -> prefs.setFeedGridLayout(c));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 리스너 제거 및 View 참조 정리
        SwitchCompat[] switches = {swInquiry, swGrid};
        for (SwitchCompat sw : switches) {
            if (sw != null) sw.setOnCheckedChangeListener(null);
        }
        swInquiry = null;
        swGrid = null;

        optionsPrefs = null;
    }
}
