package com.skplanet.app.skpadbenefitsample.ui.pop;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.skplanet.skpad.benefit.pop.feedback.DefaultPopFeedbackHandler;
import com.skplanet.resource.SKPAdSnackbarKt;

/**
 * 커스텀 POP 피드백 핸들러
 *
 * POP 광고에서 리워드를 획득했을 때 사용자에게 표시되는 피드백 메시지를
 * 커스터마이징합니다. 화려한 이모지와 메시지로 사용자 경험을 향상시킵니다.
 *
 * ## 주요 기능:
 * - **Feed 오픈 리워드**: Feed 진입 시 포인트 적립 알림
 * - **광고 참여 리워드**: 광고 참여 완료 시 포인트 적립 알림
 * - **Snackbar 우선**: 가능하면 Snackbar 사용
 * - **Toast 대체**: Snackbar 불가 시 Toast 사용
 * - **커스텀 디자인**: SKPAdSnackbar로 화려한 디자인
 *
 * ## 메시지 스타일:
 * - **Feed 오픈**: "🎁 Feed 오픈! +{포인트} 포인트 적립! (Custom)"
 * - **광고 리워드**: "🎉 축하합니다! {포인트} 포인트 획득! ✨"
 *
 * ## Snackbar vs Toast:
 * ### Snackbar (우선)
 * - 화면 하단에 표시
 * - 액션 버튼 추가 가능
 * - 자동으로 사라짐
 * - 더 나은 UX
 *
 * ### Toast (대체)
 * - View 영역 밖에서도 표시 가능
 * - 간단한 메시지
 * - Snackbar 불가 시 사용
 *
 * @see DefaultPopFeedbackHandler 부모 핸들러
 * @see SKPAdSnackbarKt 커스텀 Snackbar 유틸리티
 */
public class CustomPopFeedbackHandler extends DefaultPopFeedbackHandler {

    /** 로깅용 태그 */
    private static final String TAG = "CustomPopFeedback";

    /**
     * Feed 오픈 리워드 알림을 표시합니다 (Deprecated)
     *
     * **메시지**: "🎁 Feed 오픈! +{포인트} 포인트 적립! (Custom)"
     *
     * **표시 방식**:
     * - canUseSnackbar == true: 커스텀 Snackbar 표시
     * - canUseSnackbar == false: 로그만 기록 (피드백 생략)
     *
     * **Deprecated 이유**: 구버전 SDK 호환성 유지
     *
     * @param context Context
     * @param view Snackbar를 표시할 View
     * @param canUseSnackbar Snackbar 사용 가능 여부
     * @param reward 획득한 리워드 포인트
     */
    @Override
    public void notifyFeedLaunchReward(@NonNull Context context, @NonNull View view, boolean canUseSnackbar, int reward) {
        Log.d(TAG, "notifyFeedLaunchReward called! reward: " + reward + ", canUseSnackbar: " + canUseSnackbar);
        String message = "🎁 Feed 오픈! +" + reward + " 포인트 적립! (Custom)";

        if (canUseSnackbar) {
            showCustomSnackbar(context, view, message);
        } else {
            Log.d(TAG, "Cannot use snackbar, skipping feedback");
        }
    }

    /**
     * 광고 참여 리워드 알림을 표시합니다
     *
     * **메시지**: "🎉 축하합니다! {포인트} 포인트 획득! ✨"
     *
     * **표시 방식**:
     * - canUseSnackbar == true: 커스텀 Snackbar 표시
     * - canUseSnackbar == false: Toast 표시
     *
     * **호출 시점**:
     * - 사용자가 광고를 클릭하고 참여 완료
     * - 서버에서 리워드 지급 확인
     * - SDK가 이 메서드 호출
     *
     * @param context Context
     * @param view Snackbar를 표시할 View
     * @param canUseSnackbar Snackbar 사용 가능 여부
     * @param reward 획득한 리워드 포인트
     */
    @Override
    public void notifyNativeAdReward(@NonNull Context context, @NonNull View view, boolean canUseSnackbar, int reward) {
        Log.d(TAG, "🎉 notifyNativeAdReward called! reward: " + reward + ", canUseSnackbar: " + canUseSnackbar);
        String message = "🎉 축하합니다! " + reward + " 포인트 획득! ✨";

        if (canUseSnackbar) {
            Log.d(TAG, "Showing custom snackbar with message: " + message);
            showCustomSnackbar(context, view, message);
        } else {
            Log.d(TAG, "Showing custom toast with message: " + message);
            showCustomToast(context, message);
        }
    }

    /**
     * 커스텀 Snackbar를 화려한 스타일로 표시합니다
     *
     * **스타일 적용**:
     * - SKPAdSnackbarKt.materialStyle() 사용
     * - Material Design 스타일
     * - 커스텀 색상 및 애니메이션
     *
     * **액션 버튼**:
     * - 텍스트: "확인 👍"
     * - 클릭 시: 로그 기록
     * - Snackbar 자동 닫힘
     *
     * **에러 처리**:
     * - try-catch로 안전하게 처리
     * - 실패 시 에러 로그 기록
     * - 앱 크래시 방지
     *
     * @param context Context
     * @param view Snackbar를 표시할 View (anchor)
     * @param message 표시할 메시지 텍스트
     */
    private void showCustomSnackbar(@NonNull Context context, @NonNull View view, String message) {
        Log.d(TAG, "showCustomSnackbar called");
        try {
            Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
            SKPAdSnackbarKt.materialStyle(snackbar, context);
            snackbar.setAction("확인 👍", v -> Log.d(TAG, "Snackbar action clicked"));
            snackbar.show();
            Log.d(TAG, "Snackbar shown successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error showing snackbar: " + e.getMessage(), e);
        }
    }

    /**
     * 커스텀 Toast를 표시합니다
     *
     * **사용 시점**:
     * - Snackbar를 사용할 수 없는 경우
     * - View 영역이 없는 경우
     *
     * **표시 시간**: LENGTH_LONG (약 3.5초)
     *
     * **로깅**: Toast 표시 전후 로그 기록
     *
     * @param context Context
     * @param message 표시할 메시지 텍스트
     */
    private void showCustomToast(@NonNull Context context, String message) {
        Log.d(TAG, "showCustomToast called");
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        Log.d(TAG, "Toast shown successfully");
    }
}
