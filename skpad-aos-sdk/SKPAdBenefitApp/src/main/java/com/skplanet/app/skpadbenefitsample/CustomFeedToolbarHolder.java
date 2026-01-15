package com.skplanet.app.skpadbenefitsample;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;

import com.skplanet.skpad.benefit.presentation.feed.toolbar.DefaultFeedToolbarHolder;
import com.skplanet.skpad.benefit.presentation.feed.toolbar.FeedToolbar;
import com.skplanet.skpad.benefit.presentation.feed.toolbar.menu.presenter.layout.DefaultMenuLayout;
import com.skplanet.skpad.benefit.presentation.feed.toolbar.menu.presenter.layout.MenuLayout;

public class CustomFeedToolbarHolder extends DefaultFeedToolbarHolder {
    @Override
    public View getView(Activity activity, @NonNull final String unitId) {
        toolbar = new FeedToolbar(activity); // FeedToolbar 에서 제공하는 기본 Template 사용
        toolbar.setTitle("Custom Toolbar Title");

        toolbar.setIconResource(R.mipmap.ic_launcher);
        toolbar.setBackgroundColor(Color.parseColor("#123456"));

        addInquiryMenuItemView(activity); // 문의하기 버튼은 이 함수를 통해 간단하게 추가 가능합니다.
        addRightMenuItemView1(activity); // custom 버튼 추가
        return toolbar;
    }

    // custom 버튼 추가는 DefaultMenuLayout 를 사용하여 View 를 생성하고
    // toolbar.addRightMenuButton 를 사용하여 toolbar 에 추가합니다.
    private void addRightMenuItemView1(@NonNull final Activity activity) {
        MenuLayout menuLayout = new DefaultMenuLayout(activity, R.mipmap.ic_launcher);
        menuLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showInquiry(); // showInquiry 를 호출하여 문의하기 페이지로 연결합니다.
            }
        });
        toolbar.addRightMenuButton(menuLayout);
    }
}