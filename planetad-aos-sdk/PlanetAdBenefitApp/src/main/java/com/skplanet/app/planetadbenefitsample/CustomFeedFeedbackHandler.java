package com.skplanet.app.planetadbenefitsample;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.skplanet.skpad.benefit.presentation.feed.feedback.FeedFeedbackHandler;

public class CustomFeedFeedbackHandler implements FeedFeedbackHandler {

    @NonNull
    @Override
    public View getBaseRewardNotificationView(@NonNull Context context, int reward) {
        TextView text = new TextView(context);
        text.setText("Custom reward view:" + reward);
        return text;
    }
}
