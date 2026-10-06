package com.skplanet.app.skpadbenefitsample.ui.pop;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.skplanet.skpad.benefit.pop.DefaultPopHeaderViewAdapter;
import com.skplanet.skpad.benefit.pop.domain.model.CustomPreviewMessage;
import com.skplanet.app.skpadbenefitsample.R;

/**
 * POP 헤더 뷰 커스터마이징 어댑터
 *
 * <p>POP에서 Feed를 열었을 때 상단에 표시되는 헤더 영역을 커스터마이징합니다.
 * 서버에서 설정한 Custom Preview Message도 지원합니다.</p>
 *
 * <h2>주요 기능:</h2>
 * <ul>
 *   <li><b>Feed 헤더</b>: 적립 가능한 포인트 정보 표시</li>
 *   <li><b>Custom Preview Message</b>: 서버 설정 시 특별 메시지 표시</li>
 *   <li><b>화려한 디자인</b>: 아이콘, 배지, CTA 버튼 포함</li>
 *   <li><b>Deeplink 지원</b>: Preview Message 클릭 시 URL 열기</li>
 * </ul>
 *
 * <h2>레이아웃:</h2>
 * <ul>
 *   <li>Feed 헤더: custom_pop_feed_header.xml</li>
 *   <li>Preview Message: custom_pop_header_preview_message.xml</li>
 * </ul>
 *
 * <h2>UI 요소:</h2>
 * <ul>
 *   <li>🎁 아이콘: 리워드 표시</li>
 *   <li>포인트 정보: "{포인트} 포인트 획득 가능!"</li>
 *   <li>배지: "NEW" 또는 "HOT" 표시</li>
 *   <li>CTA 버튼: Preview Message의 랜딩 URL로 이동</li>
 * </ul>
 *
 * @see DefaultPopHeaderViewAdapter 부모 어댑터 클래스
 * @see CustomPreviewMessage 커스텀 미리보기 메시지 모델
 */
public class CustomPopHeaderViewAdapter extends DefaultPopHeaderViewAdapter {

    /** 로깅용 태그 */
    private static final String TAG = "CustomPopHeaderView";

    /**
     * 어댑터 생성자
     * <p>
     * 생성 시 로그를 출력하여 커스텀 어댑터가 적용되었음을 확인합니다.
     */
    public CustomPopHeaderViewAdapter() {
        super();
        Log.d(TAG, "🎯 CustomPopHeaderViewAdapter CREATED!");
    }

    /**
     * Custom Preview Message 레이아웃을 생성합니다
     *
     * <p>서버에서 Custom Preview Message를 설정한 경우 Feed 상단에 표시됩니다.</p>
     *
     * <p><b>표시 내용</b>:</p>
     * <ul>
     *   <li>메시지: 서버 설정 또는 기본 메시지</li>
     *   <li>아이콘: 서버에서 제공한 이미지 URL</li>
     *   <li>CTA 버튼: 랜딩 URL이 있을 때만 표시</li>
     * </ul>
     *
     * <p><b>클릭 동작</b>:</p>
     * <ul>
     *   <li>랜딩 URL이 있으면: Deeplink로 화면 이동</li>
     *   <li>랜딩 URL이 없으면: 클릭 불가</li>
     * </ul>
     *
     * @param context Context for inflating views
     * @param parent 부모 ViewGroup
     * @param customPreviewMessage 서버에서 받은 커스텀 메시지 정보
     * @return 생성된 Preview Message View (null 가능)
     */
    @Nullable
    @Override
    protected View getCustomPreviewMessageLayout(@NonNull Context context,
                                                 @Nullable ViewGroup parent,
                                                 @NonNull CustomPreviewMessage customPreviewMessage) {
        Log.d(TAG, "Creating custom preview message layout");

        // 1. 커스텀 레이아웃 inflate
        View viewCustomPreviewMessage = LayoutInflater.from(context)
                .inflate(R.layout.custom_pop_header_preview_message, parent, false);

        // 2. View 컴포넌트 참조
        final TextView textTitle = viewCustomPreviewMessage.findViewById(R.id.textCustomPreviewMessageTitle);
        final ImageView imageIcon = viewCustomPreviewMessage.findViewById(R.id.imageCustomPreviewMessageIcon);
        final TextView textCta = viewCustomPreviewMessage.findViewById(R.id.textCustomPreviewMessageCta);

        // 3. 메시지 텍스트 설정 (서버 메시지 또는 기본값)
        String message = customPreviewMessage.getMessage();
        textTitle.setText(!TextUtils.isEmpty(message) ? message : "🎁 특별한 리워드가 도착했어요!");

        // 4. 아이콘 이미지 설정 (URL에서 로드)
        String iconUrl = customPreviewMessage.getIconUrl();
        if (iconUrl != null && !iconUrl.isEmpty()) {
            imageIcon.setVisibility(View.VISIBLE);
            // 부모 클래스의 displayIcon 메서드 사용 (protected/public 메서드)
            displayIcon(imageIcon, iconUrl);
        } else {
            imageIcon.setVisibility(View.GONE);
        }

        // 5. CTA 버튼 및 클릭 리스너 설정
        if (!TextUtils.isEmpty(customPreviewMessage.getLandingUrl())) {
            // 랜딩 URL이 있으면 CTA 버튼 표시 및 클릭 가능
            textCta.setVisibility(View.VISIBLE);
            viewCustomPreviewMessage.setOnClickListener(v -> {
                Log.d(TAG, "Custom header clicked, opening: " + customPreviewMessage.getLandingUrl());
                startDeeplinkActivity(context, customPreviewMessage.getLandingUrl());
            });
        } else {
            // 랜딩 URL이 없으면 CTA 버튼 숨김
            textCta.setVisibility(View.GONE);
            viewCustomPreviewMessage.setOnClickListener(null);
        }

        return viewCustomPreviewMessage;
    }

    /**
     * POP에서 Feed를 열었을 때 상단 헤더 뷰를 생성합니다
     *
     * <p><b>레이아웃</b>: custom_pop_feed_header.xml</p>
     * <p><b>내용</b>: 적립 가능한 포인트 정보 표시</p>
     *
     * @param context 뷰를 생성할 Context
     * @param parent 부모 ViewGroup
     * @return 생성된 헤더 View
     */
    @NonNull
    @Override
    public View onCreateView(@NonNull Context context, @NonNull ViewGroup parent) {
        Log.d(TAG, "🎯 onCreateView CALLED! Creating custom feed header view for POP");
        View view = LayoutInflater.from(context).inflate(R.layout.custom_pop_feed_header, parent, false);
        Log.d(TAG, "🎯 onCreateView DONE! View created: " + view);
        return view;
    }

    /**
     * 헤더 뷰에 리워드 정보를 바인딩합니다
     *
     * <p><b>표시 로직</b>:</p>
     * <ul>
     *   <li>리워드 > 0: 헤더 표시 ("🎁 {포인트} 포인트 획득 가능!")</li>
     *   <li>리워드 = 0: 헤더 숨김 (GONE)</li>
     * </ul>
     *
     * <p><b>UI 컴포넌트</b>:</p>
     * <ul>
     *   <li>textHeaderReward: 리워드 텍스트</li>
     *   <li>textHeaderBadge: "NEW" 배지 표시</li>
     * </ul>
     *
     * @param view 헤더 View (onCreateView에서 생성됨)
     * @param reward 적립 가능한 총 포인트
     */
    @Override
    public void onBindView(@NonNull View view, int reward) {
        Log.d(TAG, "🎯 onBindView CALLED! reward: " + reward + ", view: " + view);

        // 1. View 컴포넌트 참조
        TextView textReward = view.findViewById(R.id.textHeaderReward);
        TextView textBadge = view.findViewById(R.id.textHeaderBadge);

        Log.d(TAG, "🎯 textReward: " + textReward + ", textBadge: " + textBadge);

        if (textReward != null && textBadge != null) {
            if (reward > 0) {
                // 2-1. 리워드가 있는 경우: 헤더 표시
                view.setVisibility(View.VISIBLE);

                // 리워드 텍스트 설정
                String rewardText = "🎁 " + reward + " 포인트 획득 가능!";
                textReward.setText(rewardText);

                // 배지 표시
                textBadge.setVisibility(View.VISIBLE);

                Log.d(TAG, "🎯 Custom feed header DISPLAYED with reward: " + reward);
            } else {
                // 2-2. 리워드가 없는 경우: 헤더 숨김
                view.setVisibility(View.GONE);
                Log.d(TAG, "🎯 Custom feed header HIDDEN (no reward)");
            }
        } else {
            // 3. View를 찾지 못한 경우: 에러 로그 및 디버그 정보 출력
            Log.e(TAG, "🎯 ERROR: Custom header views NOT FOUND! textReward=" + textReward + ", textBadge=" + textBadge);
            Log.e(TAG, "🎯 View class: " + view.getClass().getName());

            // 뷰 계층 구조 출력 (디버깅용)
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                Log.d(TAG, "🎯 ViewGroup has " + vg.getChildCount() + " children");
            }
        }
    }

    /**
     * 아이콘 이미지를 로드하고 표시합니다
     *
     * <p>부모 클래스의 displayIcon 메서드를 래핑하여 null 체크와 visibility 처리를 함께 수행합니다.</p>
     *
     * <p><b>동작</b>:</p>
     * <ol>
     *   <li>iconUrl이 null 또는 빈 문자열이면: ImageView 숨김</li>
     *   <li>iconUrl이 유효하면: ImageView 표시하고 이미지 로드</li>
     * </ol>
     *
     * @param imageView 아이콘을 표시할 ImageView
     * @param iconUrl 이미지 URL (null 가능)
     */
    @SuppressWarnings("RestrictedApi")  // displayIcon은 @VisibleForTesting이지만 상속 클래스에서 사용 가능
    private void loadAndDisplayIcon(@NonNull ImageView imageView, @Nullable String iconUrl) {
        if (iconUrl != null && !iconUrl.isEmpty()) {
            imageView.setVisibility(View.VISIBLE);
            // 부모 클래스의 displayIcon 메서드 호출
            // @VisibleForTesting이지만 public이므로 상속받은 클래스에서 사용 가능
            displayIcon(imageView, iconUrl);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }
}
