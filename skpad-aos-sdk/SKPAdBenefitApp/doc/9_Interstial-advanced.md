## Interstial-고급설정

### 개요
이 문서에서 가이드 하는 내용은 PlanetAD Android SDK의 Interstitial 지면 연동의 기능을 설명하고 각 기능을 사용하는 방법을 안내합니다.

### 광고 개수 설정
![Interstitail_multi](./resources/insterstital_ad_count.png)

바텀 시트 형태의 Interstitial 지면은 복수 개의 광고를 표시할 수 있습니다. 

다음은 3개의 광고를 할당받는 예시입니다. 

```
InterstitialAdConfig interstitialAdConfig = new InterstitialAdConfig.Builder()
    .adCount(3)  // 1 ~ 5 
    .build();

interstitialAdHandler.show(context, interstitialAdConfig);
```

### 종료에 대한 콜백
Interstitial 지면이 종료되는 이벤트를 수신할 수 있습니다. 필요에 따라 이용하여 종료 시점에 원하는 기능을 추가할 수 있습니다. 

다음은 Interstitial 지면이 종료되는 이벤트를 수신하는 예시입니다.

```
interstitialAdHandler.show(MainActivity.this, interstitialAdConfig, new InterstitialAdHandler.OnInterstitialAdEventListener() {
    
    // ...
    
    @Override
    public void onFinish() {
      // 인터스티셜 종료시
    }
}); 
```

### 광고 요청 결과에 대한 콜백
광고 할당 요청에 따른 결과를 수신할 수 있습니다. 

다음은 광고 요청 결과를 수신하는 리스너를 추가한 예시입니다.

```
interstitialAdHandler.show(MainActivity.this, interstitialAdConfig, new InterstitialAdHandler.OnInterstitialAdEventListener() {

    // ...
    
    @Override
    public void onAdLoaded() {
        // 로드 성공시
    }
    
    @Override
    public void onAdLoadFailed(AdError error) {
        // 로드 실패시. error를 통해 로드 실패 이유를 알 수 있음
    }
});
```

