## 맞춤형 광고를 위한 가이드 라인과 VOC(문의하기) 사용하기

### 액션형 광고에 대한 유저 VOC (문의하기) 사용하기
액션형 광고의 경우 종종 리워드 미적립을 이유로 유저가 문의(VOC)를 보내기도 합니다.

이러한 유저 VOC에 대한 접수 및 처리를 자동화 하기 위해 SDK에서는 미리 만들어 놓은 웹 페이지를 제공하고 있습니다. 이 문의하기 페이지는 연동되어 있는 앱을 기준으로 조회하기 때문에, 유닛별로 구현할 필요가 없으며, VOC의 위치를 강제하지 않습니다.

- 아래의 단계를 통해 해당 기능을 사용하실 수 있습니다.

1. VOC 페이지 로드를 위한 유저 진입 Icon/ Tab을 디자인 합니다.

2. 1번의 Icon/Tab이 클릭될 때 코드에서 SKPAdBenefit.getInstance().showInquiryPage(Context context, @Nullable final String unitId) 호출합니다.

- Interstitial, Feed 타입의 경우, 문의하기에 사용하는 기본 제공 아이콘을 사용하실 수 있습니다.

   - Interstitial - InterstitialAdConfig에 showInquiryButton(true) 설정.

   - Feed - Toolbar를 Customize 하지 않고 Default Toolbar를 사용.

- Native Type일 경우에는, 문의하기를 위해 제공되는 View를 사용하실 수 있습니다.

```
<com.skplanet.skpad.benefit.presentation.nativead.NativeAdView
    android:id="@+id/native_ad_view"
    ...생략... >
     
    // InquiryView 는 NativeAdView의 하위 컴포넌트로 구현해야합니다.
   
    <com.skplanet.skpad.benefit.presentation.guide.InquiryView
        android:id="@+id/inquiryButton"
        ...생략... />
 
    ...생략...
</com.skplanet.skpad.benefit.presentation.nativead.NativeAdView>
```

```
public void populateAd(final NativeAd nativeAd) {
    final NativeAdView view = findViewById(R.id.your_native_ad_view);
 
    final Ad ad = nativeAd.getAd();
 
    final MediaView mediaView = view.findViewById(R.id.mediaView);
    final TextView titleTextView = view.findViewById(R.id.textTitle);
    final ImageView iconView = view.findViewById(R.id.imageIcon);
    final TextView descriptionTextView = view.findViewById(R.id.textDescription);
    final CtaView ctaView = view.findViewById(R.id.ctaView);
    final CtaPresenter ctaPresenter = new CtaPresenter(ctaView); // CtaView should not be null
    ctaPresenter.bind(nativeAd);
 

    ...생략...
    // NativeAdView에 InquiryView를 설정해주어야 합니다.
    view.setInquiryView(view.findViewById(R.id.inquiryButton));

    view.setAdInfoView(view.findViewById(R.id.information));
    view.setMediaView(mediaView);
    view.setClickableViews(clickableViews);
    view.setNativeAd(nativeAd);
    ...생략...

}
```
