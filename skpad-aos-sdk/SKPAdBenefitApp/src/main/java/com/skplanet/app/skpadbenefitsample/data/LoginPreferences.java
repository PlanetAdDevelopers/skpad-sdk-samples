package com.skplanet.app.skpadbenefitsample.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.skplanet.app.skpadbenefitsample.Constants;
import com.skplanet.skpad.benefit.SKPAdBenefit;
import com.skplanet.skpad.benefit.core.models.UserProfile;

/**
 * 샘플 앱의 로그인 상태를 저장합니다.
 *
 * OptionsPreferences.resetToDefault()(옵션 초기화)에 로그인 상태가 지워지지 않도록 별도 파일로 관리합니다.
 */
public class LoginPreferences {

    private static final String PREFS_NAME = "benefit_sample_login";
    private static final String KEY_LOGGED_IN = "logged_in";

    private final SharedPreferences prefs;

    public LoginPreferences(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGGED_IN, false);
    }

    public void setLoggedIn(boolean loggedIn) {
        prefs.edit().putBoolean(KEY_LOGGED_IN, loggedIn).apply();
    }

    /**
     * 로그인 사용자 정보를 SDK에 설정합니다.
     * 광고 개인화와 리워드 적립 대상 식별에 사용됩니다.
     */
    public static void applyUserProfile() {
        UserProfile userProfile = new UserProfile.Builder(SKPAdBenefit.getUserProfile())
                .userId(Constants.USER_ID)
                .gender(Constants.GENDER)
                .birthYear(Constants.BIRTHDAY)
                .region(Constants.REGION)
                .build();
        SKPAdBenefit.setUserProfile(userProfile);
    }
}
