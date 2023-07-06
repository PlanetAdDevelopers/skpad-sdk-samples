## Native 기본설정

### 개요
본 가이드는 PlanetAD Android SDK의 Native 지면을 연동하는 방법은 안내합니다. Native 지면은 광고 지면 레이아웃을 직접 구성한 후, SKP 서버로부터 광고 데이터를 할당받아 광고 지면에 표시합니다.

![Native AD Overview](./resources/A_03.png)

### 준비사항
- [2. 시작하기](./0_benefit_begin.md) 적용 완료
- Native 지면에 사용할 Unit ID (이하 YOUR_NATIVE_UNIT_ID)

### 광고 레이아웃 구성
Native 지면은 광고 레이아웃을 자유롭게 구성하여 노출하는 지면입니다. Activity 또는 Fragment 레이아웃 내에 아래 구조에 맞게 Native 광고 레이아웃을 구성합니다.



|항목|설명|비고|
|-|-|-|
`필수` 광고 제목|광고의 제목|<li>최대 10자</li><li>생략 부호로 일정 길이 이상은 생략 가능</li>
`필수`광고 소재|이미지, 동영상 등 광고 소재|<li>com.skplanet.skpad.benefit.presentation.media.MediaView 사용 필수</li><li>종횡비 유지 필수</li><li>여백 추가 가능</li><li>이미지 사이즈 1200x627 [px]</li>
`필수` 광고 설명|광고에 대한 상세 설명|<li>생략 부호로 일정 길이 이상은 생략 가능</li><li>최대 40자</li>
`필수` 광고주 아이콘|광고주 아이콘 이미지|<li>종횡비 유지 필수</li><li>이미지 사이즈 80x80 [px]</li>
`필수` CTA 버튼|광고의 참여를 유도하는 버튼|<li>com.skplanet.skpad.benefit.presentation.media.CtaView 사용 필수</li><li>최대 7자</li><li>생략 부호로 일정 길이 이상은 생략 가능</li>
`필수` 맞춤형 광고 정보 버튼|광고 표시 이유에 대한 가이드 기능을 제공|<li>com.skplanet.skpad.benefit.presentation.guide.AdInfoView 사용 필수</li><li>icon Image 교체 가능</li><li>필수 추가 필요</li>
`권장` 문의하기 버튼|VOC대응을 위한 Planet AD Report Web Page로 이동하는 기능 제공|<li>com.skplanet.skpad.benefit.presentation.guide.InquiryView 사용 필수</li><li>icon Image 교체 가능</li>
`권장` 광고 알림 문구|Sponsored view|예시) "광고", "ad", "스폰서", "Sponsored"


광고 레이아웃의 최상위 컴포넌트는 NatvieAdView이며, 상기의 컴포넌트는 NativeAdView의 하위 컴포넌트로 구현해야 합니다.
다음은 NativeAdView의 레이아웃 예시입니다.

```
<com.skplanet.skpad.benefit.presentation.nativead.NativeAdView
    android:id="@+id/native_ad_view"
    ...생략... >
     
    // AdInfoView, InquiryView, MediaView와 CtaView는 NativeAdView의 하위 컴포넌트로 구현해야합니다.
   
    <com.skplanet.skpad.benefit.presentation.media.MediaView
        android:id="@+id/mediaView"
        ...생략... />
    <TextView
        android:id="@+id/textTitle"
        ...생략... />
    <TextView
        android:id="@+id/textDescription"
        ...생략... />
    <ImageView
        android:id="@+id/imageIcon"
        ...생략... />
    <com.skplanet.skpad.benefit.presentation.media.CtaView
        android:id="@+id/ctaView"
        ...생략... />
     <com.skplanet.skpad.benefit.presentation.guide.AdInfoView
        android:id="@+id/information" ... />

    <com.skplanet.skpad.benefit.presentation.guide.InquiryView
        android:id="@+id/inquiryButton" ... />

    ...생략...
</com.skplanet.skpad.benefit.presentation.nativead.NativeAdView>
```

Native 지면은 Activity 또는 Fragment에 구현해야 합니다. 만일 이와 다르게 구현해야 한다면 PlanetAD 매니저에 문의하시기 바랍니다.

### 광고 할당 요청

<p>광고를 표시하기 위해 광고 할당을 요청합니다.<p>

다음은 광고 할당 요청 예시입니다. populateAd()에 대해서는 아래 광고 표시를 참고하시기 바랍니다.

```
final NativeAdLoader loader = new NativeAdLoader("YOUR_NATIVE_UNIT_ID");
loader.loadAd(new NativeAdLoader.OnAdLoadedListener() {
    @Override
    public void onAdLoaded(@NonNull NativeAd nativeAd) {
        populateAd(nativeAd); // 아래 광고 표시 참조
    }
 
    @Override
    public void onLoadError(@NonNull AdError adError) {
        // 할당된 광고가 없으면 호출됩니다.
        Log.e(TAG, "Failed to load a native ad.", adError);
    }
});
```

### 광고 표시
할당받은 광고 데이터를 직접 구현한 광고 레이아웃(your_native_ad_view)에 추가합니다.

다음은 광고를 표시하는 예시입니다. (아래 예시에서는 ImageLoader 라이브러리를 사용하였습니다.)
```
public void populateAd(final NativeAd nativeAd) {
    final NativeAdView view = findViewById(R.id.your_native_ad_view);
 
    final Ad ad = nativeAd.getAd();
 
    final MediaView mediaView = view.findViewById(R.id.mediaView);
    final TextView titleTextView = view.findViewById(R.id.textTitle);
    final ImageView iconView = view.findViewById(R.id.imageIcon);
    final TextView descriptionTextView = view.findViewById(R.id.textDescription);
    final CtaView ctaView = view.findViewById(R.id.ctaView);
    final AdInfoView adInfoView = view.findViewById(R.id.information)
    final InquiryView inquiryView = view.findViewById(R.id.inquiryButton)

    final CtaPresenter ctaPresenter = new CtaPresenter(ctaView); // CtaView should not be null
    ctaPresenter.bind(nativeAd);
 
    if (mediaView != null) {
        mediaView.setCreative(ad.getCreative());
        mediaView.setVideoEventListener(new VideoEventListener() {
            // Override and implement methods
            // 고급 설정 참조
        });
    }
 
    if (titleTextView != null) {
        titleTextView.setText(ad.getTitle());
    }
 
    if (iconView != null) {
        ImageLoader.getInstance().displayImage(ad.getIconUrl(), iconView);
    }
 
    if (descriptionTextView != null) {
        descriptionTextView.setText(ad.getDescription());
    }
 
    // clickableViews에 추가된 UI 컴포넌트를 클릭하면 광고 페이지로 이동합니다.
    final List<View> clickableViews = new ArrayList<>();
    clickableViews.add(ctaView);
    clickableViews.add(mediaView);
    clickableViews.add(titleTextView);
    clickableViews.add(descriptionTextView);
 
    // Advanced : 광고 View를 Scale 해서 사용하는 경우에 한하여 적용
    // view.setScaleValue(SCALE_X_VALUE, SCALE_Y_VALUE);
 
    // 광고 콜백 이벤트를 수신할 수 있습니다.
    // view.setNativeAd 이전에 호출해야 합니다.
    // 중복하여 호출하면 2개 이상의 리스너가 등록됩니다.
     
    NativeAdView.OnNativeAdEventListener nativeAdEventListener = new NativeAdView.OnNativeAdEventListener() {
        @Override
        public void onImpressed(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
   
        }
 
        @Override
        public void onClicked(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
            ctaPresenter.bind(nativeAd);
            // 기획에 따른 추가적인 UI 처리
        }
 
        @Override
        public void onRewardRequested(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
            // 기획에 따라 리워드 로딩 이미지를 보여주는 등의 처리
        }
 
        @Override
        public void onRewarded(@NonNull NativeAdView view, @NonNull NativeAd nativeAd, @Nullable RewardResult rewardResult) {
            // 리워드 적립의 결과 (RewardResult) SUCCESS, ALREADY_PARTICIPATED, MISSING_REWARD 등에 따라서 적절한 유저 커뮤니케이션 처리
        }
 
        @Override
        public void onParticipated(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
            ctaPresenter.bind(nativeAd);
            // 기획에 따른 추가적인 UI 처리
        }
    };
    // 중복하여 addOnNativeAdEventListener를 호출하면 2개 이상의 리스너가 등록됩니다.
    // 하나의 리스너만 등록하기 위해서는 아래와 같이 리스너를 해제하거나, addOnNativeAdEventListener를 한번만 호출하기 위한 로직을 추가해야 합니다.
    view.removeOnNativeAdEventListener(nativeAdEventListener);
     
    view.addOnNativeAdEventListener(nativeAdEventListener);
     
    view.setClickableViews(clickableViews);
    view.setMediaView(mediaView);
    view.setNativeAd(nativeAd);


    view.setAdInfoView(adInfoView);
    view.setInquiryView(inquiryView);
}
```
광고의 상태별 콜백에 대해 커스터마이즈를 하는 경우에 [광고 노출/클릭/참여와 관련한 콜백 변화](./13_Event_Callback.md) 문서 참고하여 콜백의 정의 및 동작을 파악할 수 있습니다.

