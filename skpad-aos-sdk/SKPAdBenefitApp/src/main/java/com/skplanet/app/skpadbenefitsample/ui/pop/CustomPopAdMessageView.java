package com.skplanet.app.skpadbenefitsample.ui.pop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.skplanet.skpad.benefit.pop.message.PopAdMessageView;
import com.skplanet.app.skpadbenefitsample.R;
import com.skplanet.app.skpadbenefitsample.data.OptionsPreferences;

/**
 * 커스텀 POP 광고 메시지 뷰
 * - customAdMessage 스위치에 따라 화려한/기본 레이아웃 선택
 * - 사용자 정의 Duration 지원 (밀리초 -> 초 변환)
 */
public class CustomPopAdMessageView extends PopAdMessageView {

    private static final String TAG = "CustomPopAdMsgView";

    private final int durationInSeconds;
    private final boolean useCustomLayout;
    private TextView textTitle;
    private TextView textDescription;

    public CustomPopAdMessageView(@NonNull Context context) {
        super(context);

        OptionsPreferences prefs = new OptionsPreferences(context);

        // Duration 설정 (milliseconds to seconds, 최소 1초)
        long durationInMs = prefs.getPopPreviewDuration();
        durationInSeconds = Math.max(1, (int) (durationInMs / 1000));

        // 레이아웃 선택 (customAdMessage 스위치 상태에 따라)
        useCustomLayout = prefs.getPopCustomAdMessage();

        if (useCustomLayout) {
            Log.d(TAG, "Using CUSTOM (fancy) layout");
            LayoutInflater.from(context).inflate(R.layout.custom_pop_ad_message_view, this);
        } else {
            Log.d(TAG, "Using DEFAULT (simple) layout");
            LayoutInflater.from(context).inflate(R.layout.default_pop_ad_message_view, this);
        }

        this.textTitle = findViewById(R.id.textTitle);
        this.textDescription = findViewById(R.id.textDescription);

        Log.d(TAG, "CustomPopAdMessageView created! useCustomLayout=" + useCustomLayout + ", duration=" + durationInSeconds + "s");
    }

    @Override
    public void updateView(int reward, int remainSeconds) {
        Log.d(TAG, "updateView() - reward: " + reward + ", remainSeconds: " + remainSeconds + ", useCustomLayout: " + useCustomLayout);

        if (textTitle != null && textDescription != null) {
            if (useCustomLayout) {
                // 화려한 메시지 (이모지 포함)
                String rewardText = "🎁 " + reward + " 포인트 획득!";
                textTitle.setText(rewardText);
            } else {
                // 기본 메시지 (심플)
                String rewardText = reward + " 포인트 획득!";
                textTitle.setText(rewardText);
            }

            // 카운트다운 메시지
            String countdownText = remainSeconds + "초 후 자동으로 닫힙니다";
            textDescription.setText(countdownText);
        } else {
            Log.e(TAG, "ERROR: textTitle or textDescription is null!");
        }
    }

    /**
     * 현재 표시 중인 메시지 텍스트를 반환합니다
     *
     * **반환값**:
     * - textTitle이 있으면: 제목 텍스트 반환
     * - null이면: 빈 문자열 반환
     *
     * @return 메시지 텍스트 (non-null)
     */
    @NonNull
    @Override
    public String getMessage() {
        return textTitle != null ? textTitle.getText().toString() : "";
    }

    /**
     * 메시지 표시 시간을 초 단위로 반환합니다
     *
     * **계산 방식**:
     * - OptionsPreferences에서 밀리초 값 읽기
     * - 1000으로 나누어 초로 변환
     * - 최소 1초 보장
     *
     * **사용처**:
     * - SDK가 자동 닫힘 타이머 설정
     * - 카운트다운 애니메이션
     *
     * @return 표시 시간 (초)
     */
    @Override
    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    /**
     * 뷰를 업데이트합니다 (하위 호환성)
     *
     * reward 파라미터 없는 구버전 메서드입니다.
     * 부모 클래스를 호출하여 하위 호환성을 유지합니다.
     *
     * @param remainSeconds 남은 시간 (초)
     */
    @Override
    public void updateView(int remainSeconds) {
        // 부모 클래스 호출 (하위 호환성)
        super.updateView(remainSeconds);
    }
}

