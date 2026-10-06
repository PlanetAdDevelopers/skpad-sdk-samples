package com.skplanet.app.skpadbenefitsample;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.skplanet.skpad.benefit.core.utils.LayoutUtils;

/**
 * 광고 화면 Activity의 베이스 클래스
 *
 * 공통 Toolbar(뒤로가기 포함)를 구성하고 createFragment()가 반환한 Fragment를 표시합니다.
 */
public abstract class BaseAdActivity extends AppCompatActivity {

    @Nullable
    private MaterialToolbar toolbar;

    @NonNull
    protected abstract Fragment createFragment();

    @NonNull
    protected abstract String getAdTitle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutUtils.applyStatusBarMode(this, getWindow());
        LayoutUtils.applyInsetsArea(this);
        setContentView(R.layout.activity_ad_host);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getAdTitle());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ad_container, createFragment())
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        // Toolbar의 navigation button에 남아있는 pending callback을 제거하여
        // destroyed Activity를 참조하는 메모리 누수를 방지합니다.
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(null);
            for (int i = 0; i < toolbar.getChildCount(); i++) {
                Handler handler = toolbar.getChildAt(i).getHandler();
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                }
            }
        }
        toolbar = null;
        super.onDestroy();
    }
}
