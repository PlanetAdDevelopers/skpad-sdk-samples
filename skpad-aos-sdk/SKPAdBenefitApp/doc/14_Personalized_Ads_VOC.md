## 맞춤형 광고를 위한 가이드 라인과 VOC(문의하기) 사용하기

### 맞춤형 광고를 위한 가이드 라인 추가하기
Planet AD는 맞춤형 광고를 제공하며, 그에 해당하는 사항을 필수적으로 사용자에게 고지해야합니다.

이러한 맞춤형 광고 고지를 위해 미리 만들어진 UI를 제공하고 있으며, 해당 UI로 진입하기 위해 아래와 같은 방법을 제공하고 있습니다.

- AdInfoView 추가하기

   - SDK에서는 AdInfoView를 제공하며, APP에서는 해당 View를 광고 영역에 추가함으로서 맞춤형 광고에 대한 고지 기능을 제공할 수 있습니다.
```
<com.skplanet.skpad.benefit.presentation.nativead.NativeAdView
    android:id="@+id/native_ad_view"
    ...생략... >
     
    // AdInfoView 는 NativeAdView의 하위 컴포넌트로 구현해야합니다.
    <com.skplanet.skpad.benefit.presentation.guide.AdInfoView
        android:id="@+id/information"
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
    final AdInfoView adInfoView = interstitialView.findViewById(R.id.information);
    final InquiryView inquiryView = interstitialView.findViewById(R.id.inquiryButton);

    final CtaPresenter ctaPresenter = new CtaPresenter(ctaView); // CtaView should not be null
    ctaPresenter.bind(nativeAd);
 

    ...생략...
    // NativeAdView에 InquiryView를 설정해주어야 합니다.
    view.setAdInfoView(adInfoView);


    view.setMediaView(mediaView);
    view.setClickableViews(clickableViews);
    view.setNativeAd(nativeAd);
    ...생략...

}
```

- 맞춤형 광고를 

문의하기 버튼을 커스텀으로 만들어서 사용하는 경우, 버튼 클릭 시 아래와 같이 호출해 주시면 됩니다.  

### 액션형 광고에 대한 유저 VOC (문의하기) 사용하기
액션형 광고의 경우 종종 리워드 미적립을 이유로 유저가 문의(VOC)를 보내기도 합니다.

이러한 유저 VOC에 대한 접수 및 처리를 자동화 하기 위해 SDK에서는 미리 만들어 놓은 웹 페이지를 제공하고 있습니다. 이 문의하기 페이지는 연동되어 있는 앱을 기준으로 조회하기 때문에, 유닛별로 구현할 필요가 없으며, VOC의 위치를 강제하지 않습니다.

- 아래의 단계를 통해 해당 기능을 사용하실 수 있습니다.

   1. VOC 페이지 로드를 위한 유저 진입 Icon/ Tab을 디자인 합니다.
   2. 1번의 Icon/Tab이 클릭될 때 코드에서 `SKPAdBenefit.getInstance().showInquiryPage()` 호출합니다.

- Interstitial, Feed 타입의 경우, 문의하기에 사용하는 기본 제공 아이콘을 사용하실 수 있습니다.

   - Interstitial - `InterstitialAdConfig`에 `showInquiryButton(true)` 설정.
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
    final AdInfoView adInfoView = interstitialView.findViewById(R.id.information);
    final InquiryView inquiryView = interstitialView.findViewById(R.id.inquiryButton);

    final CtaPresenter ctaPresenter = new CtaPresenter(ctaView); // CtaView should not be null
    ctaPresenter.bind(nativeAd);
 

    ...생략...
    // NativeAdView에 InquiryView를 설정해주어야 합니다.
    view.setInquiryView(inquiryView);


    view.setMediaView(mediaView);
    view.setClickableViews(clickableViews);
    view.setNativeAd(nativeAd);
    ...생략...

}
```
