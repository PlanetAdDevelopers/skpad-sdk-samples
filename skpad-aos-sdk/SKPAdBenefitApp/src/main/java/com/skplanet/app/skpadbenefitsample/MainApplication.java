package com.skplanet.app.skpadbenefitsample;

import android.app.Application;
import android.util.Log;

import com.skplanet.app.skpadbenefitsample.data.LoginPreferences;
import com.skplanet.app.skpadbenefitsample.data.OptionsPreferences;
import com.skplanet.app.skpadbenefitsample.ui.pop.CustomPopAdMessageView;
import com.skplanet.app.skpadbenefitsample.ui.pop.CustomPopFeedbackHandler;
import com.skplanet.app.skpadbenefitsample.ui.pop.CustomPopHeaderViewAdapter;
import com.skplanet.app.skpadbenefitsample.ui.pop.CustomPopToolbarHolder;
import com.skplanet.app.skpadbenefitsample.ui.pop.CustomPopUtilityLayoutHandler;
import com.skplanet.skpad.benefit.SKPAdBenefit;
import com.skplanet.skpad.benefit.SKPAdBenefitConfig;
import com.skplanet.skpad.benefit.pop.DefaultPopHeaderViewAdapter;
import com.skplanet.skpad.benefit.pop.PopConfig;
import com.skplanet.skpad.benefit.pop.PopNotificationConfig;
import com.skplanet.skpad.benefit.pop.SidePosition;
import com.skplanet.skpad.benefit.presentation.feed.FeedConfig;
import com.skplanet.skpad.benefit.presentation.feed.header.DefaultFeedHeaderViewAdapter;

import java.lang.reflect.Field;

/**
 * 샘플 앱의 Application 클래스
 *
 * 앱 시작 시 SKP AD Benefit SDK를 초기화합니다.
 * 광고 로더가 내부적으로 SDK 인스턴스를 사용하므로, 앱 시작 시점에 반드시 SKPAdBenefit.init()를 호출해야 합니다.
 *
 * 초기화 순서:
 * 1. 기존 SDK 인스턴스 리셋 (POP 옵션 변경 후 재시작 시 새 config 적용)
 * 2. Constants의 App ID / Unit ID로 Feed, POP Config 구성 (POP은 OptionsPreferences 옵션 반영)
 * 3. SKPAdBenefit.init() 호출
 * 4. 로그인 상태이면 UserProfile 설정
 */
public class MainApplication extends Application {

    private static final String TAG = "MainApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        initSkpAdBenefitSafely();
    }

    private void initSkpAdBenefitSafely() {
        OptionsPreferences optionsPrefs = new OptionsPreferences(this);

        // [샘플 앱 전용] 기존 instance가 있으면 강제로 리셋 (POP 옵션 저장 후 재시작 시 새 config 적용을 위해)
        resetSKPAdBenefitInstance();

        SKPAdBenefitConfig.Builder configBuilder = new SKPAdBenefitConfig.Builder(Constants.APP_KEY);

        configBuilder.add(
                new FeedConfig.Builder(this, Constants.FEED_UNIT_ID)
                        .feedHeaderViewAdapterClass(DefaultFeedHeaderViewAdapter.class)
                        .autoLoadingEnabled(false)
                        .showInquiryButton(Constants.OLDER_14YEAR) // 만 14세 이상인 경우에만 VOC(문의하기) 기능을 노출해야합니다.
                        .build()
        );

        configBuilder.add(buildPopConfig(optionsPrefs));

        SKPAdBenefitConfig config = configBuilder.build();

        // 중복 init 방지: SDK 내부에서 예외가 날 수 있어 try/catch로 보호
        try {
            SKPAdBenefit.init(this, config);
            Log.d(TAG, "SKPAdBenefit.init() completed successfully");
        } catch (Exception e) {
            Log.e(TAG, "SKPAdBenefit.init() failed: " + e.getMessage(), e);
            return; // init 실패 시 UserProfile 설정 생략
        }

        // 로그인 상태면 init 직후 UserProfile을 설정하여 세션을 미리 수립
        if (new LoginPreferences(this).isLoggedIn()) {
            try {
                LoginPreferences.applyUserProfile();
                Log.d(TAG, "UserProfile set successfully: " + Constants.USER_ID);
            } catch (Exception e) {
                Log.e(TAG, "Failed to set UserProfile: " + e.getMessage(), e);
            }
        }
    }

    private PopConfig buildPopConfig(OptionsPreferences optionsPrefs) {
        // POP Foreground Service 알림 설정
        PopNotificationConfig popNotificationConfig = new PopNotificationConfig.Builder(this)
                .smallIconResId(R.drawable.ic_notification_pop)
                .titleResId(R.string.pop_notification_title)
                .textResId(R.string.pop_notification_text)
                .colorResId(R.color.demo_pop_notification)
                .notificationId(1000)
                .build();

        // 저장된 옵션 로드
        PopConfig.PopIdleMode idleMode = optionsPrefs.getPopIdleMode() == 0
                ? PopConfig.PopIdleMode.INVISIBLE
                : PopConfig.PopIdleMode.TRANSLUCENT;

        Log.d(TAG, "POP init - idleTime: " + optionsPrefs.getPopIdleTime()
                + ", previewInterval: " + optionsPrefs.getPopPreviewInterval()
                + ", idleMode: " + optionsPrefs.getPopIdleMode());
        Log.d(TAG, "POP CustomHeader: " + optionsPrefs.getPopCustomHeader()
                + ", CustomFeedback: " + optionsPrefs.getPopCustomFeedback());

        PopConfig.Builder popConfigBuilder = new PopConfig.Builder(this, Constants.POP_UNIT_ID)
                .initialSidePosition(new SidePosition(SidePosition.Side.RIGHT, 0.6f))
                .initialPopIdleMode(idleMode)
                .idleTimeInMillis(optionsPrefs.getPopIdleTime())
                .previewIntervalInMillis(optionsPrefs.getPopPreviewInterval())
                .articlesEnabled(false)
                .popNotificationConfig(popNotificationConfig);

        // Custom Icon 적용
        if (optionsPrefs.getPopCustomIcon()) {
            Log.d(TAG, "Using Custom POP Icons (Purple/Gold)");
            popConfigBuilder
                    .iconResId(R.drawable.custom_pop_icon)
                    .rewardReadyIconResId(R.drawable.custom_pop_icon_reward_ready);
        } else {
            Log.d(TAG, "Using Default POP Icons");
            popConfigBuilder
                    .iconResId(R.drawable.ic_notification_pop)
                    .rewardReadyIconResId(R.drawable.ic_notification_pop);
        }

        // Custom 옵션 적용
        if (optionsPrefs.getPopCustomToolbar()) {
            popConfigBuilder.feedToolbarHolderClass(CustomPopToolbarHolder.class);
        }

        if (optionsPrefs.getPopCustomHeader()) {
            popConfigBuilder.feedHeaderViewAdapterClass(CustomPopHeaderViewAdapter.class);
        } else {
            popConfigBuilder.feedHeaderViewAdapterClass(DefaultPopHeaderViewAdapter.class);
        }

        if (optionsPrefs.getPopCustomAdMessage()) {
            popConfigBuilder.popAdMessageViewClass(CustomPopAdMessageView.class);
        }

        if (optionsPrefs.getPopCustomFeedback()) {
            Log.d(TAG, "POP CustomFeedback enabled - using CustomPopFeedbackHandler");
            popConfigBuilder.popFeedbackHandlerClass(CustomPopFeedbackHandler.class);
        } else {
            Log.d(TAG, "POP CustomFeedback disabled - using default");
        }

        if (optionsPrefs.getPopUtilityHandler()) {
            popConfigBuilder.popUtilityLayoutHandlerClass(CustomPopUtilityLayoutHandler.class);
        }

        return popConfigBuilder.build();
    }

    /**
     * [샘플 앱 전용 – 실제 앱에서 사용하지 마세요]
     *
     * SKPAdBenefit 인스턴스를 리플렉션으로 null로 리셋합니다.
     * POP 옵션을 바꿔 가며 테스트하기 위해, 앱 재시작 시 새로운 PopConfig로 SDK를 다시 초기화하려는 용도입니다.
     * SDK의 private 필드 이름에 의존하므로 SDK 버전에 따라 동작하지 않을 수 있습니다.
     * 실제 앱에서는 Application.onCreate()에서 SKPAdBenefit.init()을 한 번만 호출하세요.
     */
    private void resetSKPAdBenefitInstance() {
        try {
            Field field = SKPAdBenefit.class.getDeclaredField("instance");
            field.setAccessible(true);
            Object oldInstance = field.get(null);

            if (oldInstance != null) {
                Log.d(TAG, "Resetting existing SKPAdBenefit instance");
                field.set(null, null);
                Log.d(TAG, "SKPAdBenefit instance reset successfully");
            } else {
                Log.d(TAG, "No existing SKPAdBenefit instance (first launch)");
            }
        } catch (Exception e) {
            Log.d(TAG, "SKPAdBenefit instance reset failed: " + e.getMessage());
        }
    }
}
