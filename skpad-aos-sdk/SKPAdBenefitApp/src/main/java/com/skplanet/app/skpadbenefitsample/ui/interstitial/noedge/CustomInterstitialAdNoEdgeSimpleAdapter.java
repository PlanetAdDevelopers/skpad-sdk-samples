package com.skplanet.app.skpadbenefitsample.ui.interstitial.noedge;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.skplanet.skpad.benefit.presentation.feed.ad.AdsAdapter;
import com.skplanet.skpad.benefit.presentation.guide.AdInfoView;
import com.skplanet.skpad.benefit.presentation.interstitial.fullscreen.noedge.InterstitialAdVideoPlayerOverlayView;
import com.skplanet.skpad.benefit.presentation.media.MediaView;
import com.skplanet.skpad.benefit.presentation.media.PointBulletPresenter;
import com.skplanet.skpad.benefit.presentation.media.PointBulletView;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAd;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAdView;
import com.skplanet.skpad.benefit.presentation.reward.RewardResult;
import com.skplanet.app.skpadbenefitsample.R;

/**
 * No-Edge Simple 스타일 커스텀 광고 어댑터
 *
 * <p>CustomAdapter 스위치만 ON일 때 사용되는 어댑터입니다.
 * 기본 NoEdge UI에 커스터마이징을 적용한 심플한 형태의 어댑터입니다.</p>
 *
 * <h2>특징:</h2>
 * <ul>
 *   <li><b>로그 아이콘</b>: 📱 (Simple 어댑터 구분용)</li>
 *   <li><b>레이아웃</b>: custom_skpad_view_interstitial_ad_no_edge_simple.xml</li>
 *   <li><b>MediaView 최적화</b>: BLUR 배경, FIT 스케일, 중앙정렬</li>
 *   <li><b>PointBullet</b>: 좌측 상단 포인트 배지 표시</li>
 *   <li><b>비디오 오버레이</b>: 전용 오버레이 뷰 사용</li>
 * </ul>
 *
 * <h2>MediaView 설정:</h2>
 * <ul>
 *   <li>ScaleType: FIT (화면에 맞춤)</li>
 *   <li>LayoutGravity: CENTER (중앙정렬)</li>
 *   <li>BGType: BLUR (블러 처리된 배경)</li>
 *   <li>Fullscreen: 불가 (false)</li>
 * </ul>
 *
 * <h2>UI 컴포넌트:</h2>
 * <ul>
 *   <li>MediaView: 광고 이미지/비디오</li>
 *   <li>PointBullet: 포인트 표시 배지</li>
 *   <li>Title: 광고 제목 (없으면 숨김)</li>
 *   <li>Description: 광고 설명 (없으면 숨김)</li>
 *   <li>AdInfoView: 광고 정보 표시</li>
 * </ul>
 *
 * @see AdsAdapter 부모 어댑터 클래스
 * @see PointBulletPresenter 포인트 배지 Presenter
 * @see InterstitialAdVideoPlayerOverlayView 비디오 오버레이 뷰
 */
public class CustomInterstitialAdNoEdgeSimpleAdapter extends AdsAdapter<AdsAdapter.NativeAdViewHolder> {

    /** 로깅용 태그 (📱 이모지로 Simple 구분) */
    private static final String TAG = "CustomNoEdgeSimple";

    /**
     * ViewHolder 생성 및 Simple 레이아웃 inflate
     *
     * <p><b>레이아웃</b>: custom_skpad_view_interstitial_ad_no_edge_simple.xml</p>
     * <p><b>로그</b>: "📱📱📱 Simple 어댑터 적용됨!" 출력</p>
     *
     * @param parent 부모 ViewGroup
     * @param viewType 뷰 타입
     * @return 생성된 NativeAdViewHolder
     */
    @NonNull
    @Override
    public NativeAdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "📱📱📱 CustomInterstitialAdNoEdgeSimpleAdapter.onCreateViewHolder() - Simple 어댑터 적용됨! 📱📱📱");
        
        final LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final NativeAdView interstitialNativeAdView = (NativeAdView) inflater.inflate(
                R.layout.custom_skpad_view_interstitial_ad_no_edge_simple, 
                parent, 
                false
        );
        return new NativeAdViewHolder(interstitialNativeAdView);
    }

    /**
     * ViewHolder에 NoEdge Simple 광고 데이터를 바인딩합니다
     *
     * <p><b>바인딩 순서</b>:</p>
     * <ol>
     *   <li>부모 클래스 바인딩</li>
     *   <li>커스텀 라벨 업데이트</li>
     *   <li>비디오 오버레이 뷰 설정</li>
     *   <li>MediaView 설정 (Scale, Gravity, BG)</li>
     *   <li>Creative 데이터 바인딩</li>
     *   <li>PointBullet 바인딩</li>
     *   <li>Title/Description 바인딩 (조건부)</li>
     *   <li>이벤트 리스너 등록</li>
     * </ol>
     *
     * <p><b>MediaView 설정</b>:</p>
     * <ul>
     *   <li>VideoOverlay: 전용 오버레이 뷰</li>
     *   <li>Fullscreen: 불가 (false)</li>
     *   <li>ScaleType: FIT</li>
     *   <li>Gravity: CENTER</li>
     *   <li>BGType: BLUR (블러 배경)</li>
     * </ul>
     *
     * <p><b>조건부 표시</b>:</p>
     * <ul>
     *   <li>Title: 비어있으면 숨김 (GONE)</li>
     *   <li>Description: 비어있으면 숨김 (GONE)</li>
     * </ul>
     *
     * @param holder 바인딩할 ViewHolder
     * @param nativeAd 광고 데이터
     */
    @Override
    public void onBindViewHolder(@NonNull NativeAdViewHolder holder, NativeAd nativeAd) {
        super.onBindViewHolder(holder, nativeAd);
        
        Log.d(TAG, "📱 CustomInterstitialAdNoEdgeSimpleAdapter.onBindViewHolder() - Simple 광고 데이터 바인딩");

        final NativeAdView nativeAdView = (NativeAdView) holder.itemView;
        
        // 커스텀 라벨 업데이트
        TextView customLabel = nativeAdView.findViewById(R.id.custom_label_simple);
        if (customLabel != null) {
            customLabel.setText("📱 CUSTOM SIMPLE #" + holder.getBindingAdapterPosition());
        }
        
        MediaView mediaView = nativeAdView.findViewById(R.id.ad_media_view);
        PointBulletView pointBullet = nativeAdView.findViewById(R.id.ad_point_bullet);
        AdInfoView adInfoView = nativeAdView.findViewById(R.id.ad_information);

        // 비디오 오버레이 뷰 설정
        InterstitialAdVideoPlayerOverlayView overlayView = new InterstitialAdVideoPlayerOverlayView(nativeAdView.getContext());
        mediaView.setVideoPlayerOverlayView(overlayView);

        // 전체화면 허용 안함
        mediaView.setVideoFullscreenAllowed(false);

        // MediaView Scale Type 설정
        mediaView.setScaleType(MediaView.MediaScaleType.FIT);

        // MediaView의 Creative가 비율에 맞춰서 중앙정렬되도록 설정
        mediaView.setLayoutGravity(Gravity.CENTER);

        // Background Type Blur 설정
        mediaView.setBGType(MediaView.BGType.BLUR);

        // MediaView에 광고의 Creative 설정
        mediaView.setCreative(nativeAd.getAd().getCreative());

        // 좌측 상단의 Point Bullet 설정
        final PointBulletPresenter pointBulletPresenter = new PointBulletPresenter(pointBullet);
        pointBulletPresenter.bind(nativeAd);

        // Title 설정
        TextView title = nativeAdView.findViewById(R.id.ad_text_title);
        if (!TextUtils.isEmpty(nativeAd.getAd().getTitle())) {
            title.setVisibility(View.VISIBLE);
            title.setText(nativeAd.getAd().getTitle());
        } else {
            title.setVisibility(View.GONE);
        }

        // Description 설정
        TextView subTitle = nativeAdView.findViewById(R.id.ad_text_description);
        if (!TextUtils.isEmpty(nativeAd.getAd().getDescription())) {
            subTitle.setVisibility(View.VISIBLE);
            subTitle.setText(nativeAd.getAd().getDescription());
        } else {
            subTitle.setVisibility(View.GONE);
        }

        // NativeAdView의 이벤트 리스너 설정
        nativeAdView.addOnNativeAdEventListener(new NativeAdView.OnNativeAdEventListener() {
            @Override
            public void onImpressed(@NonNull NativeAdView view, @NonNull NativeAd ad) {
                Log.d(TAG, "📱 Simple 광고 노출됨");
            }

            @Override
            public void onClicked(@NonNull NativeAdView view, @NonNull NativeAd ad) {
                Log.d(TAG, "📱 Simple 광고 클릭됨");
                pointBulletPresenter.bind(ad);
            }

            @Override
            public void onRewardRequested(@NonNull NativeAdView view, @NonNull NativeAd ad) {
                Log.d(TAG, "📱 Simple 리워드 요청됨");
            }

            @Override
            public void onRewarded(@NonNull NativeAdView view, @NonNull NativeAd ad, RewardResult result) {
                Log.d(TAG, "📱 Simple 리워드 지급됨");
            }

            @Override
            public void onParticipated(@NonNull NativeAdView view, @NonNull NativeAd ad) {
                Log.d(TAG, "📱 Simple 광고 참여됨");
                pointBulletPresenter.bind(ad);
            }
        });

        nativeAdView.setMediaView(mediaView);
        nativeAdView.setNativeAd(nativeAd);
        nativeAdView.setAdInfoView(adInfoView);
    }
}
