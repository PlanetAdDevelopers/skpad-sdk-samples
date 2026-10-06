package com.skplanet.app.skpadbenefitsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.skplanet.app.skpadbenefitsample.data.LoginPreferences;

/**
 * 로그인 화면
 *
 * 로그인 버튼을 누르면 Constants의 사용자 정보로 UserProfile을 설정하고
 * 광고 유형 선택 화면(AdSelectorActivity)으로 이동합니다.
 */
public class MainFragment extends Fragment {

    @Nullable
    private Button btnShowAd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView sampleInfo = view.findViewById(R.id.tv_sample_info);
        sampleInfo.setText("App ID : " + Constants.APP_KEY
                + "\nUser ID : " + Constants.USER_ID
                + "\nGender : " + Constants.GENDER
                + "\nBirth Year : " + Constants.BIRTHDAY
                + "\nRegion : " + Constants.REGION);

        btnShowAd = view.findViewById(R.id.btn_show_ad);
        btnShowAd.setOnClickListener(v -> {
            LoginPreferences.applyUserProfile();
            // 로그인 상태 저장
            new LoginPreferences(requireContext()).setLoggedIn(true);
            Intent intent = new Intent(requireContext(), AdSelectorActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (btnShowAd != null) {
            btnShowAd.setOnClickListener(null);
            btnShowAd = null;
        }
    }
}
