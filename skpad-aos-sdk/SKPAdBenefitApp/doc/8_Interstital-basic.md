## Interstitial-기본설정

### 개요
![Interstitail_Basic](./resources/insterstital_default.png)

Interstitial 지면은 PlanetAd Android SDK에서 제공하는 UI를 사용해 앱을 완전히 덮으면서 노출됩니다. <br>
제공하는 UI로 쉽게 연동할 수 있으며, 광고 지면이 앱을 덮고 있어서 앱 UI 와의 조합을 고려하지 않고 노출하기 용이합니다. 


### 준비 사항
- [시작하기](./0_benefit_begin.md) 적용 완료
- Interstitial 지면에 사용할 Unit ID (이하 YOUR_INTERSTITIAL_UNIT_ID)

### 광고 표시
Interstitial 지면을 표시합니다. PlanetAD Android SDK의 Interstitial 지면은 다이얼로그와 바텀시트의 UI를 제공합니다.<br>
다이얼로그와 바텀시트 각각 InterstitialAdHandler.Type.Dialog 또는 InterstitialAdHandler.Type.BottomSheet으로 설정할 수 있습니다.<br>

다음은 다이얼로그형태의 Interstitial 지면을 표시하는 예시입니다. 

```
final InterstitialAdHandler interstitialAdHandler = new InterstitialAdHandlerFactory()
        .create("YOUR_INTERSTITIAL_UNIT_ID", InterstitialAdHandler.Type.Dialog);
interstitialAdHandler.show(context);
```