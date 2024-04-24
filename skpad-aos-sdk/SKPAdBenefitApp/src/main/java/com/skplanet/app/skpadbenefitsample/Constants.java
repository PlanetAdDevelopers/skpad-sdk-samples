package com.skplanet.app.skpadbenefitsample;

import com.skplanet.skpad.benefit.core.models.UserProfile;

public class Constants {

    public static final String APP_KEY = "263145199717132";

    public static final String NATIVEAD_UNIT_ID = "443067018104046";
    public static final String INTERSTITIAL_UNIT_ID = "187488886283457";
    public static final String FEED_UNIT_ID = "80276338026190";
    public static final String POP_UNIT_ID = "133807838388589";

    public static final String USER_ID = "PLANET AD Sample ID";

    public static final String REGION = "서울특별시 관악구";
    public static final UserProfile.Gender GENDER = UserProfile.Gender.MALE;
    public static final int BIRTHDAY = 1979;

    public static final String WEB_SDK_TEST_URL = "https://m.planetad.co.kr/pages/integration/websdk.html";

    // 만 14세 미만일 경우 VOC(문의하기) 기능을 제공해서는 안됩니다.
    public static final boolean OLDER_14YEAR = true;
}
