package com.skplanet.app.skpadbenefitsample.ui.banner;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.skplanet.app.skpadbenefitsample.Constants;
import com.skplanet.app.skpadbenefitsample.R;
import com.skplanet.skpad.benefit.core.ad.AdError;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAd;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAdBannerView;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAdLoader;
import com.skplanet.skpad.benefit.presentation.nativead.NativeAdView;
import com.skplanet.skpad.benefit.presentation.reward.RewardResult;

/**
 * NativeAdBannerView(배너형 네이티브 광고)를 표시하는 Fragment
 *
 * 광고 로드 후 NativeAdBannerView를 코드로 생성하여 컨테이너 맨 아래에 붙이고,
 * 광고 정보 표시, 포인트 배경/텍스트 색상, 미디어 위치, 텍스트 색상 옵션을 적용합니다.
 */
public class NativeAdBannerFragment extends Fragment {

    @Nullable private TextView tvStatus;
    @Nullable private TextView tvError;
    @Nullable private Button btnReload;
    @Nullable private NativeAdBannerView nativeAdBannerView;
    @Nullable private LinearLayout contentRoot;

    @Nullable private SwitchCompat swAdInfo;
    @Nullable private SwitchCompat swPointColor;
    @Nullable private SwitchCompat swPointTextColor;
    @Nullable private SwitchCompat swMediaPosition;
    @Nullable private SwitchCompat swTextColor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_native_ad_banner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvStatus = view.findViewById(R.id.tv_status);
        tvError = view.findViewById(R.id.tv_error);
        btnReload = view.findViewById(R.id.btn_reload);

        swAdInfo = view.findViewById(R.id.sw_ad_info);
        swPointColor = view.findViewById(R.id.sw_point_color);
        swPointTextColor = view.findViewById(R.id.sw_point_text_color);
        swMediaPosition = view.findViewById(R.id.sw_media_position);
        swTextColor = view.findViewById(R.id.sw_image_media_text_color);

        if (btnReload != null) {
            btnReload.setOnClickListener(v -> loadAd());
        }

        loadAd();
    }

    /** 배너 광고를 로드합니다. */
    private void loadAd() {
        final String unitId = Constants.BANNER_UNIT_ID;

        if (tvStatus != null) tvStatus.setText("광고 로딩 중... (Unit: " + unitId + ")");
        if (tvError != null) tvError.setVisibility(View.GONE);
        if (btnReload != null) btnReload.setVisibility(View.GONE);

        View root = getView();
        contentRoot = root != null ? root.findViewById(R.id.native_ad_banner_container) : null;

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
                requireActivity().runOnUiThread(() -> bindNativeAd(nativeAd, unitId));
            }
        });
    }

    /** 기존 배너를 제거하고 새 NativeAdBannerView를 컨테이너 맨 아래에 추가합니다. */
    private void attachNativeAdBannerViewAtBottom() {
        if (contentRoot == null) {
            return;
        }

        if (nativeAdBannerView != null) {
            contentRoot.removeView(nativeAdBannerView);
        }

        NativeAdBannerView banner = new NativeAdBannerView(requireContext());
        banner.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        banner.setVisibility(View.INVISIBLE);

        // 맨 아래 삽입
        contentRoot.addView(banner);
        nativeAdBannerView = banner;
    }

    private void bindNativeAd(NativeAd nativeAd, String unitId) {
        attachNativeAdBannerViewAtBottom();

        NativeAdBannerView bannerView = nativeAdBannerView;
        if (bannerView == null) return;

        bannerView.setAdInfoVisible(swAdInfo != null && swAdInfo.isChecked());

        if (swPointColor != null && swPointColor.isChecked()) {
            bannerView.setPointBulletColor(Color.RED);
        }
        if (swPointTextColor != null && swPointTextColor.isChecked()) {
            bannerView.setPointTextColor(Color.BLUE);
        }

        if (swMediaPosition != null && !swMediaPosition.isChecked()) {
            bannerView.setMediaPosition(NativeAdBannerView.MediaPosition.LEFT);
        } else {
            bannerView.setMediaPosition(NativeAdBannerView.MediaPosition.RIGHT);
        }

        if (swTextColor != null && swTextColor.isChecked()) {
            bannerView.setTextColor(Color.CYAN);
        }

        bannerView.setNativeAd(nativeAd);

        bannerView.addOnNativeAdEventListener(createNativeAdEventListener());

        // 광고 노출
        bannerView.setVisibility(View.VISIBLE);
        if (tvStatus != null) tvStatus.setText("광고 로드 완료 (" + unitId + ")");
        if (tvError != null) tvError.setVisibility(View.GONE);
        if (btnReload != null) btnReload.setVisibility(View.VISIBLE);
    }

    private void showError(String message) {
        if (tvStatus != null) tvStatus.setText("오류");
        if (tvError != null) {
            tvError.setText(message);
            tvError.setVisibility(View.VISIBLE);
        }
        if (nativeAdBannerView != null) nativeAdBannerView.setVisibility(View.INVISIBLE);
        if (btnReload != null) btnReload.setVisibility(View.VISIBLE);
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

        tvStatus = null;
        tvError = null;
        btnReload = null;
        nativeAdBannerView = null;
        contentRoot = null;
    }
}
