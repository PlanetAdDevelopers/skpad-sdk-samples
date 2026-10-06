package com.skplanet.app.skpadbenefitsample.ui.nativead;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.skplanet.app.skpadbenefitsample.Constants;
import com.skplanet.app.skpadbenefitsample.R;
import com.skplanet.skpad.benefit.core.ad.AdError;
import com.skplanet.skpad.benefit.core.models.Ad;
import com.skplanet.skpad.benefit.core.models.Creative;
import com.skplanet.skpad.benefit.presentation.guide.AdInfoView;
import com.skplanet.skpad.benefit.presentation.guide.InquiryView;
import com.skplanet.skpad.benefit.presentation.media.CtaPresenter;
import com.skplanet.skpad.benefit.presentation.media.CtaView;
import com.skplanet.skpad.benefit.presentation.media.MediaView;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAd;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAdLoader;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAdView;
import com.skplanet.skpad.benefit.presentation.reward.RewardResult;
import com.skplanet.skpad.benefit.presentation.video.VideoErrorStatus;
import com.skplanet.skpad.benefit.presentation.video.VideoEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 네이티브 광고를 표시하고 테스트하는 Fragment
 *
 * NativeAdLoader로 광고를 로드하고 NativeAdView에 바인딩합니다.
 * 노출, 클릭, 리워드, 참여, 동영상 이벤트를 Toast로 표시합니다.
 */
public class NativeAdFragment extends Fragment {

    /** 현재 로드된 광고 객체 (재바인딩용) */
    @Nullable
    private NativeAd currentNativeAd;

    /** CTA 버튼 프레젠터 (클릭 후 상태 업데이트) */
    @Nullable
    private CtaPresenter ctaPresenter;

    @Nullable private TextView tvStatus;
    @Nullable private TextView tvError;
    @Nullable private Button btnReload;
    @Nullable private NativeAdView nativeAdView;
    @Nullable private MediaView mediaView;
    @Nullable private TextView titleTextView;
    @Nullable private TextView descriptionTextView;
    @Nullable private ImageView iconImageView;
    @Nullable private CtaView ctaView;
    @Nullable private AdInfoView adInfoView;
    @Nullable private InquiryView inquiryView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_native_ad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvStatus = view.findViewById(R.id.tv_status);
        tvError = view.findViewById(R.id.tv_error);
        btnReload = view.findViewById(R.id.btn_reload);
        nativeAdView = view.findViewById(R.id.native_ad_view);
        mediaView = view.findViewById(R.id.ad_media_view);
        titleTextView = view.findViewById(R.id.ad_title_text);
        descriptionTextView = view.findViewById(R.id.ad_description_text);
        iconImageView = view.findViewById(R.id.ad_icon_image);
        ctaView = view.findViewById(R.id.ad_cta_view);
        adInfoView = view.findViewById(R.id.ad_info_view);
        inquiryView = view.findViewById(R.id.ad_inquiry_view);

        if (ctaView != null) {
            ctaPresenter = new CtaPresenter(ctaView);
        }

        if (btnReload != null) {
            btnReload.setOnClickListener(v -> loadAd());
        }

        loadAd();
    }

    /** 네이티브 광고를 로드합니다. */
    private void loadAd() {
        final String unitId = Constants.NATIVEAD_UNIT_ID;

        if (tvStatus != null) tvStatus.setText("광고 로딩 중... (Unit: " + unitId + ")");
        if (tvError != null) tvError.setVisibility(View.GONE);
        if (btnReload != null) btnReload.setVisibility(View.GONE);
        if (nativeAdView != null) nativeAdView.setVisibility(View.INVISIBLE);

        NativeAdLoader loader = new NativeAdLoader(unitId);
        loader.loadAd(new NativeAdLoader.OnAdLoadedListener() {
            @Override
            public void onLoadError(@NonNull AdError error) {
                if (!isAdded()) return;
                requireActivity().runOnUiThread(() -> showError("광고 로드 실패: " + error.getErrorType()));
            }

            @Override
            public void onAdLoaded(@NonNull NativeAd nativeAd) {
                if (!isAdded()) return;
                requireActivity().runOnUiThread(() -> {
                    currentNativeAd = nativeAd;
                    bindNativeAd(nativeAd, unitId);
                });
            }
        });
    }

    /** 로드된 광고를 NativeAdView에 바인딩합니다. */
    private void bindNativeAd(NativeAd nativeAd, String unitId) {
        Ad ad = nativeAd.getAd();
        NativeAdView navView = nativeAdView;
        MediaView mView = mediaView;
        TextView titleTv = titleTextView;
        TextView descTv = descriptionTextView;
        ImageView iconIv = iconImageView;
        if (navView == null || mView == null || titleTv == null || descTv == null || iconIv == null) {
            return;
        }

        // 미디어/콘텐츠 설정
        mView.setCreative(ad.getCreative());
        titleTv.setText(ad.getTitle());
        descTv.setText(ad.getDescription());

        if (getContext() != null) {
            Glide.with(getContext()).load(ad.getIconUrl()).into(iconIv);
        }

        // 이미지 타입이면 텍스트 요소 숨기기
        Creative creative = ad.getCreative();
        if (creative != null && Creative.Type.IMAGE == creative.getType()) {
            titleTv.setVisibility(View.GONE);
            iconIv.setVisibility(View.GONE);
            descTv.setVisibility(View.GONE);
        } else {
            titleTv.setVisibility(View.VISIBLE);
            iconIv.setVisibility(View.VISIBLE);
            descTv.setVisibility(View.VISIBLE);
        }

        // AdInfoView / InquiryView 연결
        navView.setAdInfoView(adInfoView);
        navView.setInquiryView(inquiryView);

        // 클릭 가능한 뷰 설정
        List<View> clickableViews = new ArrayList<>();
        if (ctaView != null) clickableViews.add(ctaView);
        clickableViews.add(mView);
        clickableViews.add(titleTv);
        clickableViews.add(iconIv);
        clickableViews.add(descTv);
        navView.setMediaView(mView);
        navView.setClickableViews(clickableViews);
        navView.setNativeAd(nativeAd);

        // CTA 바인딩
        if (ctaPresenter != null) ctaPresenter.bind(nativeAd);

        // 이벤트 리스너 등록
        mView.setVideoEventListener(createVideoEventListener());
        navView.addOnNativeAdEventListener(createNativeAdEventListener());

        // 광고 노출
        navView.setVisibility(View.VISIBLE);
        if (tvStatus != null) tvStatus.setText("광고 로드 완료 (" + unitId + ")");
        if (tvError != null) tvError.setVisibility(View.GONE);
        if (btnReload != null) btnReload.setVisibility(View.VISIBLE);

        if (creative != null && Creative.Type.HTML.equals(creative.getType())) {
            mView.setBackgroundColorListener(navView::setBackgroundColor);
        }
    }

    private void showError(String message) {
        if (tvStatus != null) tvStatus.setText("오류");
        if (tvError != null) {
            tvError.setText(message);
            tvError.setVisibility(View.VISIBLE);
        }
        if (nativeAdView != null) nativeAdView.setVisibility(View.INVISIBLE);
        if (btnReload != null) btnReload.setVisibility(View.VISIBLE);
    }

    private VideoEventListener createVideoEventListener() {
        return new VideoEventListener() {
            @Override
            public void onVideoStarted() {
                toast("영상 시작");
            }

            @Override
            public void onResume() {
            }

            @Override
            public void onPause() {
            }

            @Override
            public void onReplay() {
                toast("영상 재재생");
            }

            @Override
            public void onLanding() {
                toast("랜딩");
            }

            @Override
            public void onError(@NonNull VideoErrorStatus videoErrorStatus, @Nullable String errorMessage) {
                toast("영상 오류: " + errorMessage);
            }

            @Override
            public void onVideoEnded() {
                toast("영상 종료");
            }
        };
    }

    private NativeAdView.OnNativeAdEventListener createNativeAdEventListener() {
        return new NativeAdView.OnNativeAdEventListener() {
            @Override
            public void onImpressed(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
                toast("광고 노출");
            }

            @Override
            public void onClicked(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
                toast("광고 클릭");

                if (nativeAd.getAd().isStayAd()) {
                    Toast.makeText(view.getContext(), "체류형 광고입니다." + nativeAd.getAd().getRewardDelay() + "초만큼 머물러 주세요", Toast.LENGTH_SHORT).show();
                }

                if (ctaPresenter != null) ctaPresenter.bind(nativeAd);
            }

            @Override
            public void onRewardRequested(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
                toast("리워드 요청");
            }

            @Override
            public void onRewarded(@NonNull NativeAdView view, @NonNull NativeAd nativeAd, @Nullable RewardResult result) {
                if (result == RewardResult.SUCCESS) {
                    toast(nativeAd.getAd().getDisplayReward() + nativeAd.getAd().getDisplayRewardName() + "가 적립되었습니다.");
                } else if (result == RewardResult.TOO_SHORT_TO_PARTICIPATE) {
                    toast("너무 빨리 돌아 오셨어요" + nativeAd.getAd().getRewardDelay() + "초만큼 머물러 주세요");
                }
            }

            @Override
            public void onParticipated(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
                toast("참여 완료");
                if (ctaPresenter != null) ctaPresenter.bind(nativeAd);
            }
        };
    }

    private void toast(String msg) {
        if (isAdded()) {
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (btnReload != null) btnReload.setOnClickListener(null);

        ctaPresenter = null;
        currentNativeAd = null;

        tvStatus = null;
        tvError = null;
        btnReload = null;
        nativeAdView = null;
        mediaView = null;
        titleTextView = null;
        descriptionTextView = null;
        iconImageView = null;
        ctaView = null;
        adInfoView = null;
        inquiryView = null;
    }
}
