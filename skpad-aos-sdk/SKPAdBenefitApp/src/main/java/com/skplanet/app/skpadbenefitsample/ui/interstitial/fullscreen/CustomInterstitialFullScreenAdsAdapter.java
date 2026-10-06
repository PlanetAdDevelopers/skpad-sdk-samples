package com.skplanet.app.skpadbenefitsample.ui.interstitial.fullscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.skplanet.skpad.benefit.core.models.Ad;
import com.skplanet.skpad.benefit.core.models.Creative;
import com.skplanet.skpad.benefit.presentation.feed.ad.AdsAdapter;
import com.skplanet.skpad.benefit.presentation.interstitial.fullscreen.InterstitialAdFullScreenCtaPresenter;
import com.skplanet.skpad.benefit.presentation.interstitial.fullscreen.InterstitialAdFullScreenCtaView;
import com.skplanet.skpad.benefit.presentation.media.MediaView;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAd;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAdView;
import com.skplanet.skpad.benefit.presentation.reward.RewardResult;
import com.skplanet.app.skpadbenefitsample.R;

import java.util.ArrayList;

/**
 * 전체화면 Interstitial 광고용 커스텀 어댑터
 *
 * <p>전체화면 인터스티셜 광고를 커스텀 레이아웃으로 표시합니다.
 * 💜 이모지로 로그를 표시하여 커스텀 어댑터 적용을 시각적으로 확인할 수 있습니다.</p>
 *
 * <h2>주요 기능:</h2>
 * <ul>
 *   <li><b>커스텀 레이아웃</b>: custom_view_interstitial_fullscreen_ad.xml 사용</li>
 *   <li><b>미디어 최적화</b>: 세로 모드, Scale Type 설정</li>
 *   <li><b>HTML 지원</b>: HTML 크리에이티브 배경색 동적 적용</li>
 *   <li><b>CTA 관리</b>: FullScreen 전용 CTA Presenter 사용</li>
 *   <li><b>이벤트 로깅</b>: 모든 광고 이벤트 상세 로깅</li>
 * </ul>
 *
 * <h2>설정되는 MediaView 옵션:</h2>
 * <ul>
 *   <li>Vertical: true (세로 모드)</li>
 *   <li>Image ScaleType: FIT (화면에 맞춤)</li>
 *   <li>Video ScaleType: FIT</li>
 *   <li>HTML ScaleType: FIT</li>
 * </ul>
 *
 * @see AdsAdapter 부모 어댑터 클래스
 * @see InterstitialAdFullScreenCtaPresenter CTA 처리 Presenter
 * @see MediaView 미디어(이미지/비디오/HTML) 뷰
 */
public class CustomInterstitialFullScreenAdsAdapter extends AdsAdapter<AdsAdapter.NativeAdViewHolder> {

    /** 로깅용 태그 (💜 이모지로 Fullscreen 구분) */
    private static final String TAG = "CustomFullScreen";

    /**
     * ViewHolder 생성 및 커스텀 레이아웃 inflate
     *
     * <p><b>레이아웃</b>: custom_view_interstitial_fullscreen_ad.xml</p>
     * <p><b>로그</b>: "💜💜💜 Fullscreen 커스텀 어댑터 적용됨!" 출력</p>
     *
     * @param parent 부모 ViewGroup
     * @param viewType 뷰 타입
     * @return 생성된 NativeAdViewHolder
     */
    @NonNull
    @Override
    public NativeAdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "💜💜💜 CustomInterstitialFullScreenAdsAdapter - Fullscreen 커스텀 어댑터 적용됨! 💜💜💜");
        final LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final NativeAdView nativeAdView = (NativeAdView) inflater.inflate(R.layout.custom_view_interstitial_fullscreen_ad, parent, false);
        return new NativeAdViewHolder(nativeAdView);
    }

    /**
     * ViewHolder에 전체화면 광고 데이터를 바인딩합니다
     *
     * <p><b>바인딩 순서 (중요!)</b>:</p>
     * <ol>
     *   <li>부모 클래스 바인딩 호출</li>
     *   <li>커스텀 라벨 업데이트 (위치 번호 표시)</li>
     *   <li>MediaView 설정 (세로 모드, ScaleType)</li>
     *   <li>Creative 데이터 바인딩</li>
     *   <li>CTA 버튼 바인딩</li>
     *   <li>클릭 가능 뷰 등록</li>
     *   <li>이벤트 리스너 등록</li>
     *   <li>HTML 크리에이티브인 경우 배경색 처리</li>
     * </ol>
     *
     * <p><b>MediaView 설정</b>:</p>
     * <ul>
     *   <li>setVertical(true): 세로 모드 강제</li>
     *   <li>ImageScaleType: FIT (화면에 맞춤)</li>
     *   <li>VideoScaleType: FIT</li>
     *   <li>HTMLScaleType: FIT</li>
     * </ul>
     *
     * <p><b>특별 처리</b>:</p>
     * <ul>
     *   <li>HTML 크리에이티브: 배경색 자동 적용</li>
     *   <li>모든 이벤트 발생 시 CTA 상태 갱신</li>
     * </ul>
     *
     * @param holder 바인딩할 ViewHolder
     * @param nativeAd 광고 데이터
     */
    @Override
    public void onBindViewHolder(@NonNull NativeAdViewHolder holder, @SuppressLint("RecyclerView") @Nullable NativeAd nativeAd) {
        super.onBindViewHolder(holder, nativeAd);

        Log.d(TAG, "💜 CustomInterstitialFullScreenAdsAdapter - Fullscreen 광고 데이터 바인딩");

        final NativeAdView view = (NativeAdView) holder.itemView;

        // 커스텀 라벨 업데이트
        TextView customLabel = view.findViewById(R.id.custom_ad_title_view);
        if (customLabel != null) {
            customLabel.setText("💜 CUSTOM FULLSCREEN #" + holder.getBindingAdapterPosition());
        }

        MediaView mediaView = view.findViewById(R.id.custom_ad_media_view);
        InterstitialAdFullScreenCtaView ctaView = view.findViewById(R.id.custom_ad_cta_view);

        // 필수 셋팅 순서 유지 필요
        mediaView.setVertical(true);
        // Image의 경우 화면에 표현되는 Scale 정의
        mediaView.setImageScaleType(MediaView.MediaScaleType.FIT);
        mediaView.setVideoScaleType(MediaView.MediaScaleType.FIT);
        mediaView.setHTMLScaleType(MediaView.MediaScaleType.FIT);

        Ad ad = nativeAd.getAd();
        mediaView.setCreative(ad.getCreative());

        // CTA 처리 로직
        InterstitialAdFullScreenCtaPresenter ctaPresenter = new InterstitialAdFullScreenCtaPresenter(ctaView);
        ctaPresenter.bind(nativeAd);

        // 화면 클릭 View Setting
        ArrayList<android.view.View> clickableViews = new ArrayList<>();
        if (mediaView != null) {
            clickableViews.add(mediaView);
        }
        if (ctaView != null) {
            clickableViews.add(ctaView);
        }
        view.setClickableViews(clickableViews);

        view.addOnNativeAdEventListener(new NativeAdView.OnNativeAdEventListener() {
            @Override
            public void onImpressed(@NonNull NativeAdView nativeAdView, @NonNull NativeAd nativeAd) {
                Log.d(TAG, "💜 Fullscreen 광고 노출됨");
                ctaPresenter.bind(nativeAd);
            }

            @Override
            public void onClicked(@NonNull NativeAdView nativeAdView, @NonNull NativeAd nativeAd) {
                Log.d(TAG, "💜 Fullscreen 광고 클릭됨");
                ctaPresenter.bind(nativeAd);
            }

            @Override
            public void onRewardRequested(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
                Log.d(TAG, "💜 Fullscreen 리워드 요청됨");
                ctaPresenter.bind(nativeAd);
            }

            @Override
            public void onRewarded(@NonNull NativeAdView view, @NonNull NativeAd nativeAd, RewardResult nativeAdRewardResult) {
                Log.d(TAG, "💜 Fullscreen 리워드 지급됨");
                ctaPresenter.bind(nativeAd);
            }

            @Override
            public void onParticipated(@NonNull NativeAdView nativeAdView, @NonNull NativeAd nativeAd) {
                Log.d(TAG, "💜 Fullscreen 광고 참여됨");
                ctaPresenter.bind(nativeAd);
            }
        });

        // HTML의 경우 컨텐츠에 맞춰 화면 구성
        if (ad != null && ad.getCreative() != null && Creative.Type.HTML.equals(ad.getCreative().type)) {
            mediaView.setBackgroundColorListener(view::setBackgroundColor);
        }

        view.setMediaView(mediaView);
        view.setNativeAd(nativeAd);
    }
}
