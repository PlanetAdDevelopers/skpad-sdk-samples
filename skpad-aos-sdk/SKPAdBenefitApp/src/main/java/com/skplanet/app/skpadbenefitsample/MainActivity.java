package com.skplanet.app.skpadbenefitsample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.skplanet.app.skpadbenefitsample.data.LoginPreferences;
import com.skplanet.skpad.benefit.core.utils.LayoutUtils;

/**
 * 앱의 첫 진입점 - 로그인 Activity
 *
 * 이미 로그인 상태라면 AdSelectorActivity로 바로 이동하고,
 * 그렇지 않으면 로그인 화면(MainFragment)을 표시합니다.
 *
 * Android 13+ 에서는 POP(Foreground Service) 알림 표시를 위해 POST_NOTIFICATIONS 권한을 요청합니다.
 */
public class MainActivity extends AppCompatActivity {

    /** MainFragment 식별용 태그 */
    private static final String FRAGMENT_TAG = "main_fragment";

    /** 알림 권한 요청 코드 (Android 13+) */
    private static final int REQUEST_CODE_POST_NOTIFICATIONS = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutUtils.applyStatusBarMode(this, getWindow());
        LayoutUtils.applyInsetsArea(this);

        setContentView(R.layout.activity_main);

        // 이미 로그인 상태면 AdSelectorActivity로 바로 이동
        if (new LoginPreferences(this).isLoggedIn()) {
            Intent adSelectorIntent = new Intent(this, AdSelectorActivity.class);
            // navigate_to extra를 AdSelectorActivity로 전달 (POP 재시작 후 POP 화면 자동 진입)
            String navigateTo = getIntent().getStringExtra("navigate_to");
            if (navigateTo != null) {
                adSelectorIntent.putExtra("navigate_to", navigateTo);
            }
            startActivity(adSelectorIntent);
            finish();
            return;
        }

        // Android 13+ (API 33) 알림 권한 요청 - Foreground Service(POP) 정상 동작에 필요
        requestPostNotificationsPermissionIfNeeded();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MainFragment(), FRAGMENT_TAG)
                    .commit();
        }
    }

    private void requestPostNotificationsPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_CODE_POST_NOTIFICATIONS
                );
            }
        }
    }
}
