package com.skplanet.app.skpadbenefitsample.ui.pop;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;

import com.skplanet.skpad.benefit.pop.toolbar.DefaultPopToolbarHolder;
import com.skplanet.skpad.benefit.pop.toolbar.PopToolbar;
import com.skplanet.app.skpadbenefitsample.R;

/**
 * 커스텀 POP Toolbar Holder
 *
 * <p>POP 화면 상단의 툴바를 커스터마이징하여 제공합니다.</p>
 *
 * <h2>커스터마이징 요소:</h2>
 * <ul>
 *   <li><b>제목</b>: "Custom Pop Toolbar"로 변경</li>
 *   <li><b>아이콘</b>: 앱 런처 아이콘 사용</li>
 *   <li><b>배경색</b>: 다크 그레이 (DKGRAY) 적용</li>
 *   <li><b>설정 메뉴</b>: 기본 설정 메뉴 아이템 추가</li>
 * </ul>
 *
 * <h2>기능:</h2>
 * <ul>
 *   <li>POP 진입 시 상단에 커스텀 스타일의 툴바 표시</li>
 *   <li>사용자가 POP 설정에 빠르게 접근 가능</li>
 *   <li>브랜딩 일관성 유지 (앱 아이콘 표시)</li>
 * </ul>
 *
 * @see DefaultPopToolbarHolder 부모 클래스
 * @see PopToolbar SDK에서 제공하는 툴바 컴포넌트
 */
public class CustomPopToolbarHolder extends DefaultPopToolbarHolder {

    /**
     * 커스텀 툴바 뷰를 생성하고 반환합니다
     *
     * <p><b>설정 항목</b>:</p>
     * <ol>
     *   <li>PopToolbar 인스턴스 생성</li>
     *   <li>제목 설정: "Custom Pop Toolbar"</li>
     *   <li>아이콘 설정: 앱 런처 아이콘</li>
     *   <li>배경색 설정: 다크 그레이</li>
     *   <li>설정 메뉴 아이템 추가</li>
     * </ol>
     *
     * @param activity 툴바가 표시될 Activity
     * @param unitId POP Unit ID
     * @return 생성된 커스텀 툴바 View
     */
    @Override
    public View getView(Activity activity, @NonNull final String unitId) {
        // 1. 툴바 생성
        toolbar = new PopToolbar(activity);

        // 2. 커스텀 스타일 적용
        toolbar.setTitle("Custom Pop Toolbar");
        toolbar.setIconResource(R.mipmap.ic_launcher);
        toolbar.setBackgroundColor(Color.DKGRAY);

        // 3. 설정 메뉴 추가
        addSettingsMenuItemView(activity, unitId);

        return toolbar;
    }
}

