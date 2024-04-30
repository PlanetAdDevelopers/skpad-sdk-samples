package com.skplanet.app.skpadbenefitsample;

import android.app.Application;
import android.content.Context;

import com.skplanet.skpad.benefit.SKPAdBenefit;
import com.skplanet.skpad.benefit.SKPAdBenefitConfig;
import com.skplanet.skpad.benefit.pop.DefaultPopHeaderViewAdapter;
import com.skplanet.skpad.benefit.pop.PopConfig;
import com.skplanet.skpad.benefit.pop.PopNotificationConfig;
import com.skplanet.skpad.benefit.pop.SidePosition;
import com.skplanet.skpad.benefit.pop.toolbar.DefaultPopToolbarHolder;
import com.skplanet.skpad.benefit.presentation.feed.FeedConfig;
import com.skplanet.skpad.benefit.presentation.feed.header.DefaultFeedHeaderViewAdapter;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initBenefit(this);

        // Planet AD SDK 동의 정보에 대한 사전 동의 API, 미호출 시 SDK에서 동의 정보를 얻기 위한 UI가 호출됨.
//        SKPAdBenefit.getPrivacyPolicyManager().grantConsent();
    }

    public void initBenefit(Context context) {

        FeedConfig feedConfig = new FeedConfig.Builder(context, Constants.FEED_UNIT_ID)
                .feedHeaderViewAdapterClass(DefaultFeedHeaderViewAdapter.class)
                .adsAdapterClass(CustomFeedAdsAdapter.class)
                .articlesEnabled(true)
                .showInquiryButton(Constants.OLDER_14YEAR) // // 만 14세 이상인 경우에만 VOC(문의하기) 기능을 노출해야합니다.
                .feedFeedbackHandlerClass(CustomFeedFeedbackHandler.class)
                .build();


        final SKPAdBenefitConfig benefitConfig = new SKPAdBenefitConfig.Builder(Constants.APP_KEY)
                .setFeedConfig(feedConfig)
                .setPopConfig(buildPopConfig())
                .build();

        SKPAdBenefit.init(context, benefitConfig);
    }

    private PopConfig buildPopConfig() {
        final PopConfig.Builder popConfigBuilder = new PopConfig.Builder(this, Constants.POP_UNIT_ID)
                .initialSidePosition(new SidePosition(SidePosition.Side.RIGHT, 0.6f))
                .initialPopIdleMode(PopConfig.PopIdleMode.INVISIBLE)
                .idleTimeInMillis(5000L)
                .previewIntervalInMillis(10 * 1000)
                .feedToolbarHolderClass(DefaultPopToolbarHolder.class) // For customizing, use TemplatePopToolbarHolder.class, CustomPopToolbarHolder.class
                .feedHeaderViewAdapterClass(DefaultPopHeaderViewAdapter.class) // For customizing use CustomPopHeaderViewAdapter.class
                .articlesEnabled(false)
                .popNotificationConfig(buildPopNotificationConfig())
                .popFeedbackHandlerClass(CustomPopFeedbackHandler.class)
                .iconResId(R.drawable.skpad_custom_pop_icon)
                .popUtilityLayoutHandlerClass(null)
                .rewardReadyIconResId(R.drawable.skpad_custom_pop_icon);

        return popConfigBuilder.build();
    }

    private PopNotificationConfig buildPopNotificationConfig() {
        return new PopNotificationConfig.Builder(this)
                .smallIconResId(R.drawable.sample_benefit_ic_notification_pop_gift)
                .titleResId(R.string.pop_notification_title)
                .textResId(R.string.pop_notification_text)
                .colorResId(R.color.colorPrimary)
                .notificationId(1000)
                .build();
    }
}
