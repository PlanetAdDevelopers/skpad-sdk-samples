package com.skplanet.app.skpadbenefitsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.skplanet.app.skpadbenefitsample.data.LoginPreferences;
import com.skplanet.app.skpadbenefitsample.data.OptionsPreferences;
import com.skplanet.app.skpadbenefitsample.ui.banner.NativeAdBannerActivity;
import com.skplanet.app.skpadbenefitsample.ui.carousel.CarouselAdActivity;
import com.skplanet.app.skpadbenefitsample.ui.feed.FeedAdActivity;
import com.skplanet.app.skpadbenefitsample.ui.interstitial.bottomsheet.InterstitialBottomSheetActivity;
import com.skplanet.app.skpadbenefitsample.ui.interstitial.dialog.InterstitialDialogActivity;
import com.skplanet.app.skpadbenefitsample.ui.interstitial.fullscreen.InterstitialFullscreenActivity;
import com.skplanet.app.skpadbenefitsample.ui.interstitial.noedge.InterstitialNoEdgeActivity;
import com.skplanet.app.skpadbenefitsample.ui.nativead.NativeAdActivity;
import com.skplanet.app.skpadbenefitsample.ui.pop.PopActivity;
import com.skplanet.app.skpadbenefitsample.ui.web.WebSdkActivity;
import com.skplanet.skpad.benefit.SKPAdBenefit;
import com.skplanet.skpad.benefit.core.models.AutoplayType;
import com.skplanet.skpad.benefit.core.models.UserPreferences;
import com.skplanet.skpad.benefit.core.utils.LayoutUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 광고 타입 선택 메인 Activity
 *
 * 테스트하고 싶은 광고 타입 목록과 공통 옵션(동영상 자동재생, 옵션 초기화, 로그아웃)을 제공합니다.
 */
public class AdSelectorActivity extends AppCompatActivity {

    /** 테스트 가능한 광고 타입 목록 (클릭 시 해당 Activity로 이동) */
    private final List<String> adTypes = Arrays.asList(
            "Native",
            "Carousel",
            "Banner",
            "Feed",
            "Interstitial Dialog",
            "Interstitial BottomSheet",
            "Interstitial Fullscreen",
            "Interstitial No Edge",
            "Web SDK",
            "POP"
    );

    private OptionsPreferences optionsPrefs;

    /** Spinner 초기 콜백 방지 플래그 (setSelection 후 첫 콜백 무시) */
    private boolean isAutoplaySpinnerInitialized = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutUtils.applyStatusBarMode(this, getWindow());
        LayoutUtils.applyInsetsArea(this);
        setContentView(R.layout.activity_ad_selector);

        optionsPrefs = new OptionsPreferences(this);

        // 초기화 버튼
        findViewById(R.id.btn_reset_options).setOnClickListener(v -> showResetConfirmDialog());

        // 로그아웃 버튼
        Button btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(v -> {
            SKPAdBenefit.setUserProfile(null);
            new LoginPreferences(this).setLoggedIn(false);
            Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        // Video AutoPlay 스피너 - 저장된 값 로드
        Spinner spinnerAutoplay = findViewById(R.id.spinner_video_autoplay);
        spinnerAutoplay.setSelection(optionsPrefs.getVideoAutoplay());
        spinnerAutoplay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isAutoplaySpinnerInitialized) {
                    isAutoplaySpinnerInitialized = true;
                    return;
                }
                handleAutoplaySelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ListView listView = findViewById(R.id.list_ad_types);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.list_item_ad_type, android.R.id.text1, adTypes));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Class<?> target = getActivityClass(adTypes.get(position));
            if (target != null) {
                startActivity(new Intent(this, target));
            }
        });

        // navigate_to extra 확인하여 자동 이동
        String navigateTo = getIntent().getStringExtra("navigate_to");
        if ("POP".equals(navigateTo)) {
            // AdSelectorActivity를 백스택에 유지한 채로 PopActivity 시작
            startActivity(new Intent(this, PopActivity.class));
        }
    }

    @Nullable
    private Class<?> getActivityClass(String adType) {
        switch (adType) {
            case "Native":
                return NativeAdActivity.class;
            case "Carousel":
                return CarouselAdActivity.class;
            case "Banner":
                return NativeAdBannerActivity.class;
            case "Feed":
                return FeedAdActivity.class;
            case "Interstitial Dialog":
                return InterstitialDialogActivity.class;
            case "Interstitial BottomSheet":
                return InterstitialBottomSheetActivity.class;
            case "Interstitial Fullscreen":
                return InterstitialFullscreenActivity.class;
            case "Interstitial No Edge":
                return InterstitialNoEdgeActivity.class;
            case "Web SDK":
                return WebSdkActivity.class;
            case "POP":
                return PopActivity.class;
            default:
                return null;
        }
    }

    private void showResetConfirmDialog() {
        new AlertDialog.Builder(this)
                .setTitle("옵션 초기화")
                .setMessage("모든 옵션을 기본값으로 초기화하시겠습니까?")
                .setPositiveButton("확인", (dialog, which) -> resetOptions())
                .setNegativeButton("취소", null)
                .show();
    }

    private void handleAutoplaySelection(int position) {
        optionsPrefs.setVideoAutoplay(position);
        AutoplayType autoplayType;
        switch (position) {
            case 1:
                autoplayType = AutoplayType.DISABLED;
                break;
            case 2:
                autoplayType = AutoplayType.ENABLED;
                break;
            case 3:
                autoplayType = AutoplayType.ON_WIFI;
                break;
            default:
                autoplayType = null;
                break;
        }
        SKPAdBenefit.setUserPreferences(
                autoplayType == null
                        ? null
                        : new UserPreferences.Builder(SKPAdBenefit.getUserPreferences())
                        .autoplayType(autoplayType)
                        .build()
        );
    }

    private void resetOptions() {
        optionsPrefs.resetToDefault();

        // UI 업데이트
        ((Spinner) findViewById(R.id.spinner_video_autoplay)).setSelection(OptionsPreferences.DEFAULT_VIDEO_AUTOPLAY);

        // UserPreferences 초기화
        SKPAdBenefit.setUserPreferences(null);

        Toast.makeText(this, "옵션이 초기화되었습니다.", Toast.LENGTH_SHORT).show();
    }
}
