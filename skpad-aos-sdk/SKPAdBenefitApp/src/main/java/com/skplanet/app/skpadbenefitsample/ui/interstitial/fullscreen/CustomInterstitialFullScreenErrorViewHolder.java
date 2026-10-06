package com.skplanet.app.skpadbenefitsample.ui.interstitial.fullscreen;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skplanet.skpad.benefit.presentation.interstitial.InterstitialErrorViewHolder;
import com.skplanet.app.skpadbenefitsample.R;

import androidx.annotation.NonNull;

/**
 * 전체화면 Interstitial 광고용 커스텀 에러 뷰 홀더
 *
 * <p>광고 로드 실패 시 표시되는 에러 화면을 커스터마이징합니다.</p>
 *
 * <h2>표시 내용:</h2>
 * <ul>
 *   <li><b>에러 이미지</b>: skpad_error_creative_state 아이콘</li>
 *   <li><b>에러 제목</b>: "FullScreen 에러: 광고가 없습니다."</li>
 *   <li><b>에러 설명</b>: "할당된 광고가 없습니다!"</li>
 * </ul>
 *
 * <h2>사용 시나리오:</h2>
 * <ul>
 *   <li>네트워크 에러로 광고 로드 실패</li>
 *   <li>할당된 광고 캠페인 없음 (NO_FILL)</li>
 *   <li>테스트 모드: 강제로 에러 뷰 표시 (swErrorView ON)</li>
 * </ul>
 *
 * <h2>레이아웃:</h2>
 * <p>R.layout.custom_view_interstitial_fullscreen_error</p>
 *
 * @see InterstitialErrorViewHolder 부모 에러 뷰 홀더
 */
public class CustomInterstitialFullScreenErrorViewHolder extends InterstitialErrorViewHolder {

    /**
     * 커스텀 에러 뷰를 생성하고 반환합니다
     *
     * <p><b>생성 프로세스</b>:</p>
     * <ol>
     *   <li>커스텀 에러 레이아웃 inflate</li>
     *   <li>에러 이미지 설정</li>
     *   <li>에러 제목 텍스트 설정</li>
     *   <li>에러 설명 텍스트 설정</li>
     *   <li>완성된 뷰 반환</li>
     * </ol>
     *
     * <p><b>커스터마이징 포인트</b>:</p>
     * <ul>
     *   <li>에러 메시지 텍스트 변경 가능</li>
     *   <li>에러 아이콘 이미지 변경 가능</li>
     *   <li>레이아웃 디자인 완전 커스터마이징 가능</li>
     * </ul>
     *
     * @param activity 에러 뷰를 inflate할 Activity
     * @return 커스터마이징된 에러 View
     */
    @NonNull
    @Override
    public View getErrorView(@NonNull Activity activity) {
        // 1. 커스텀 에러 레이아웃 inflate
        View errorView = activity.getLayoutInflater().inflate(R.layout.custom_view_interstitial_fullscreen_error, null, false);

        // 2. View 컴포넌트 참조
        final ImageView errorImageView = errorView.findViewById(R.id.interstitialErrorImageView);
        final TextView errorTitle = errorView.findViewById(R.id.interstitialErrorTitle);
        final TextView errorDescription = errorView.findViewById(R.id.interstitialErrorDescription);

        // 3. 에러 UI 설정
        errorImageView.setImageResource(com.skplanet.skpad.benefit.presentation.interstitial.R.drawable.skpad_error_creative_state);
        errorTitle.setText("FullScreen 에러: 광고가 없습니다.");
        errorDescription.setText("할당된 광고가 없습니다!");

        return errorView;
    }
}

