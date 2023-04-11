## Interstial-Customizing

### 개요
본 가이드에서는 PlanetAD Android SDK에서 제공하는 Interstitial 지면 UI의 구성을 지키며 디자인을 변경하기 위한 방법을 안내합니다. 

### Interstitial 지면 UI 커스터마이징

![Interstitail_area_guide](./resources/insterstital_area_guide.png)

Interstitial 지면 UI는 2가지를 설정할 수 있습니다.

Cta 버튼 UI는 테마 적용으로 변경할 수 있습니다.

타이틀 및 색상 및 아이콘은 InterstitialAdConfig을 설정하여 변경할 수 있습니다. 

다음은 타이틀 및 색상 및 아이콘을 변경하는 예시입니다.

```
InterstitialAdConfig interstitialAdConfig = 
        new InterstitialAdConfig.Builder()
                .topIcon(R.drawable.your_drawable)
                .titleText("지금 바로 참여하고 포인트 받기")
                .textColor(android.R.color.your_color)
                .layoutBackgroundColor(R.color.your_color)
                .build();

final InterstitialAdHandler interstitialAdHandler = new InterstitialAdHandlerFactory()
        .create("YOUR_INTERSTITIAL_UNIT_ID", InterstitialAdHandler.Type.Dialog);
interstitialAdHandler.show(context, interstitialAdConfig);
```
