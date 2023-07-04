package com.skplanet.app.skpadbenefitsample;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.skplanet.skpad.browser.SKPAdBrowser;
import com.skplanet.skpad.browser.SKPAdBrowserFragment;

public class CustomInAppBrowserActivity extends FragmentActivity {
    public static final String EXTRA_LANDING_URL = "com.skplanet.skpad.benefit.sample.customizable.EXTRA_LANDING_URL";

    private SKPAdBrowser.OnBrowserEventListener onBrowserEventListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inapp_browser);

        Intent intent = getIntent();
        SKPAdBrowserFragment fragment = SKPAdBrowser.getInstance(this).getFragment(intent.getStringExtra(EXTRA_LANDING_URL));
        getSupportFragmentManager().beginTransaction().add(R.id.inappBrowserFragment, fragment).commit();
        initBrowserEventListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SKPAdBrowser.getInstance(this).removeBrowserEventListener(onBrowserEventListener);
    }

    private void initBrowserEventListener() {
        onBrowserEventListener = new SKPAdBrowser.OnBrowserEventListener() {
            @Override
            public void onDeepLinkOpened() {
                finish();
            }
        };
        SKPAdBrowser.getInstance(this).addBrowserEventListener(onBrowserEventListener);
    }
}
