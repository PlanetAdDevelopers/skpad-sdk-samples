package com.skplanet.app.skpadbenefitsample.ui.interstitial.bottomsheet;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.viewpager.widget.ViewPager;

/**
 * 최대 높이 제한이 있는 BottomSheet용 ViewPager
 *
 * <p>BottomSheet 내부에서 사용되는 커스텀 ViewPager로, 화면 높이의 60%를
 * 최대 높이로 제한하여 하단 UI 요소(인디케이터, 문의 버튼)가 항상 보이도록 합니다.</p>
 *
 * <h2>주요 기능:</h2>
 * <ul>
 *   <li><b>최대 높이 제한</b>: 화면 높이의 60%로 고정</li>
 *   <li><b>동적 크기 조정</b>: 자식 View 크기에 따라 자동 조정</li>
 *   <li><b>하단 UI 보호</b>: 광고가 너무 커져도 인디케이터와 버튼 보장</li>
 * </ul>
 *
 * <h2>사용 이유:</h2>
 * <ul>
 *   <li>BottomSheet에서 광고가 전체 화면을 차지하는 것 방지</li>
 *   <li>페이지 인디케이터 (dots)가 항상 보이도록 보장</li>
 *   <li>"문의하기" 버튼이 화면 밖으로 밀려나지 않도록 방지</li>
 * </ul>
 *
 * <h2>계산 방식:</h2>
 * <pre>
 * maxHeight = 화면 높이 × 0.6 (60%)
 * finalHeight = min(자식 View 높이, maxHeight)
 * </pre>
 *
 * @see ViewPager 부모 클래스
 */
public class MaxHeightBottomSheetViewPager extends ViewPager {

    /** 최대 높이 (화면 높이의 60%) */
    private final int maxHeight;

    /**
     * Context만으로 ViewPager를 생성합니다
     *
     * @param context Context
     */
    public MaxHeightBottomSheetViewPager(Context context) {
        super(context);
        maxHeight = calculateMaxHeight(context);
    }

    /**
     * Context와 AttributeSet으로 ViewPager를 생성합니다 (XML에서 사용)
     *
     * @param context Context
     * @param attrs XML 속성
     */
    public MaxHeightBottomSheetViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        maxHeight = calculateMaxHeight(context);
    }

    /**
     * 화면 크기를 기반으로 최대 높이를 계산합니다
     *
     * <p><b>계산 로직</b>:</p>
     * <ol>
     *   <li>WindowManager에서 DisplayMetrics 가져오기</li>
     *   <li>화면 높이 (heightPixels) 확인</li>
     *   <li>높이의 60%를 maxHeight로 설정</li>
     * </ol>
     *
     * @param context WindowManager에 접근하기 위한 Context
     * @return 계산된 최대 높이 (픽셀)
     */
    private int calculateMaxHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(dm);
        }
        // 화면 높이의 60%를 최대 높이로 사용
        return (int) (dm.heightPixels * 0.6f);
    }

    /**
     * View의 크기를 측정하고 최대 높이를 적용합니다
     *
     * <p><b>측정 프로세스</b>:</p>
     * <ol>
     *   <li>모든 자식 View의 높이를 측정</li>
     *   <li>가장 큰 자식 View의 높이를 찾기</li>
     *   <li>maxHeight와 비교하여 작은 값을 최종 높이로 설정</li>
     *   <li>최종 높이로 ViewPager 크기 확정</li>
     * </ol>
     *
     * <p><b>목적</b>: 광고가 너무 커지는 것을 방지하고 하단 UI 보호</p>
     *
     * @param widthMeasureSpec 너비 측정 스펙
     * @param heightMeasureSpec 높이 측정 스펙
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int mode = MeasureSpec.getMode(heightMeasureSpec);
        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            // 모든 자식 View의 최대 높이를 찾기
            int childMaxHeight = 0;
            for (int i = 0; i < getChildCount(); i++) {
                final View child = getChildAt(i);
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int h = child.getMeasuredHeight();
                if (h > childMaxHeight) {
                    childMaxHeight = h;
                }
            }

            // maxHeight를 초과하지 않도록 제한
            int finalHeight = Math.min(childMaxHeight, maxHeight);

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

