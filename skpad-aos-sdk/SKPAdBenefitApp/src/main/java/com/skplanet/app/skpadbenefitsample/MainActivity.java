package com.skplanet.app.skpadbenefitsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.skplanet.app.skpadbenefitsample.adapter.FeedAdapter;
import com.skplanet.app.skpadbenefitsample.adapter.InterstitialAdapter;
import com.skplanet.app.skpadbenefitsample.adapter.NativeAdAdapter;
import com.skplanet.app.skpadbenefitsample.adapter.PopAdapger;
import com.skplanet.skpad.benefit.SKPAdBenefit;
import com.skplanet.skpad.benefit.core.ad.AdError;
import com.skplanet.skpad.benefit.core.js.SKPAdBenefitJavascriptInterface;
import com.skplanet.skpad.benefit.core.models.UserProfile;
import com.skplanet.skpad.benefit.pop.SKPAdPop;
import com.skplanet.skpad.benefit.presentation.DefaultLauncher;
import com.skplanet.skpad.benefit.presentation.feed.FeedHandler;
import com.skplanet.skpad.benefit.presentation.interstitial.InterstitialAdHandler;
import com.skplanet.skpad.browser.SKPAdBrowser;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "SKPAdBenefit";

    private FeedAdapter feedAdapter;
    private PopAdapger popAdapger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCommon();

        initNativeAd();

        initFeed();

        initPop();

        initInterstital();

        initWebSDK();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PopAdapger.REQUEST_CODE_SHOW_POP) {
            if (SKPAdPop.hasPermission(this)) {
                popAdapger.showPopOrRequestOverlayPermissionIfNeeded();
            }
        }
    }


    private void initCommon() {
        Button logIn = findViewById(R.id.login_button);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserProfile();
            }
        });

        Button logOut = findViewById(R.id.logout_button);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearUserProfile();
            }
        });

        // 앱 내 Custom Launcher를 설정 시 사용.
        Switch inAppBrowserSwitch = findViewById(R.id.inapp_browser_switch);
        inAppBrowserSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SKPAdBenefit.setLauncher(new CustomLauncher());
                } else {
                    SKPAdBenefit.setLauncher(new DefaultLauncher());
                }
            }
        });
    }

    private void initNativeAd() {
        Button showNativeButton = findViewById(R.id.native_ad_button);
        showNativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View nativeAdLayout = findViewById(R.id.skp_ad_native_layout);
                new NativeAdAdapter(MainActivity.this).loadAds(nativeAdLayout);
            }
        });
    }

    private void initFeed() {

        initFeedHandler();

        Button preload = findViewById(R.id.feed_preload);
        preload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                feedAdapter.preload(new FeedHandler.FeedPreloadListener() {
                    @Override
                    public void onPreloaded() {
                        syncTotalRewardText();
                        Toast.makeText(MainActivity.this, "Success to prereload feed data!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(AdError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "Failed to prereload! ErrorType:" + error.getErrorType() + " " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        Button showFeedButton = findViewById(R.id.feed_button);
        showFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FeedAdapter(MainActivity.this).show();
            }
        });

        Button resetFeedButton = findViewById(R.id.feed_reset_button);
        resetFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFeedHandler();
            }
        });
    }

    private void initPop() {

        popAdapger = new PopAdapger(MainActivity.this);

        Button showPopButon = findViewById(R.id.pop_show_button);
        showPopButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAdapger.showPopOrRequestOverlayPermissionIfNeeded();
            }
        });

        Button unregisterPopButton = findViewById(R.id.pop_unregister_button);
        unregisterPopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAdapger.remove();
            }
        });
    }

    private void syncTotalRewardText() {
        TextView feedTotalRewardText = findViewById(R.id.total_reward_text);
        feedTotalRewardText.setText("" + feedAdapter.getTotalReward());
    }


    private void initInterstital() {

        Button showInsterstitialButton = findViewById(R.id.interstitial_ad_button);
        showInsterstitialButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Spinner interstitialTypeSpinner = findViewById(R.id.interstitial_type_spinner);
                final int selectedType = interstitialTypeSpinner.getSelectedItemPosition();
                final InterstitialAdHandler.Type interstitialType = selectedType == 0 || selectedType == Spinner.INVALID_POSITION
                        ? InterstitialAdHandler.Type.Dialog
                        : InterstitialAdHandler.Type.BottomSheet;

                new InterstitialAdapter(MainActivity.this).show(interstitialType);
            }
        });
    }

    private void initWebSDK() {

        Button webBrowserButton = findViewById(R.id.launch_browser_button);
        webBrowserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SKPAdBrowser.getInstance(MainActivity.this)
                        .addJavascriptInterface(SKPAdBenefitJavascriptInterface.class)
                        .addBrowserEventListener(new SKPAdBrowser.OnBrowserEventListener() {
                            @Override
                            public void onBrowserClosed() {
                                Toast.makeText(MainActivity.this, "onBrowserClosed", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onBrowserOpened() {
                                Toast.makeText(MainActivity.this, "onBrowserOpened", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLanding() {
                                Toast.makeText(MainActivity.this, "onLanding", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPageLoaded(String url) {
                                Toast.makeText(MainActivity.this, "onPageLoaded", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPageLoadError() {
                                Toast.makeText(MainActivity.this, "onPageLoadError", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onUrlLoading(String url) {
                                Toast.makeText(MainActivity.this, "onUrlLoading", Toast.LENGTH_SHORT).show();
                            }
                        }).open(Constants.WEB_SDK_TEST_URL);
            }
        });    }


    private void setUserProfile() {
        // 사용자 정보 설정
        final UserProfile userProfile = new UserProfile.Builder(SKPAdBenefit.getUserProfile())
                .userId(Constants.USER_ID)
                .gender(Constants.GENDER)
                .birthYear(Constants.BIRTHDAY)
                .build();
        SKPAdBenefit.setUserProfile(userProfile);
    }

    private void clearUserProfile() {
        // 사용자 정보 초기화
        SKPAdBenefit.setUserProfile(null);
    }

    private void initFeedHandler() {
        feedAdapter = new FeedAdapter(MainActivity.this);
    }
}