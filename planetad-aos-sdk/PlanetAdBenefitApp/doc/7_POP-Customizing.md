## POP-Customizing

### 개요

본 가이드에서는 SKP Ad Android SDK에서 제공하는 UI의 구성을 지키며 디자인을 변경하기 위한 방법을 안내합니다.<br>
추가적인 디자인 변경을 원하시는 경우에는 [고급 설정](./6_POP-advanced.md)에서 UI를 자체 구현하는 방법으로 진행할 수 있습니다.

### Pop 배경색 변경
![POP_BACKGROUND](./resources/benefit_pop_background.png)

Pop의 배경색은 [테마 적용](./11_Customizing.md)에 따라 변경할 수 있습니다.

### Pop 아이콘 변경
![POP_ICON](./resources/benefit_pop_icon.png)

PopConfig를 설정하여 Pop 아이콘 이미지를 변경할 수 있습니다.

다음은 Pop 아이콘을 변경하는 예시입니다.
```
PopConfig popConfig = new PopConfig.Builder(context, "YOUR_POP_UNIT_ID")
      .iconResId(R.drawable.your_pop_icon)
      .rewardReadyIconResId(R.drawable.you_pop_icon_reward_ready)
      .build();
```

iconResId: 상태에 따라 평상시 아이콘과 팝 닫기 아이콘을 지정해 주어야 합니다.
```
<!-- your_pop_icon.xml -->
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- 닫기 아이콘 -->
    <item android:drawable="@drawable/icon_pop_selected" android:state_selected="true" />
    <!-- 평상시 팝 아이콘 -->
    <item android:drawable="@drawable/icon_pop"/>
</selector>
```

rewardReadyIconResId: 적립 가능한 포인트가 있을 때 기본 아이콘이 아닌 다른 아이콘을 (예: 동전 아이콘) 유저에게 보여줄 수 있습니다.
```
<!-- you_pop_icon_reward_ready.xml -->
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- 닫기 아이콘 -->
    <item android:drawable="@drawable/icon_pop_selected" android:state_selected="true" />
    <!-- 적립 가능 포인트가 있을 때 팝 아이콘 -->
    <item android:drawable="@drawable/icon_pop_reward_ready"/>
</selector>
```
팝 아이콘 권장 사이즈
- 56x56 dp (mdpi 기준)
- 224x224 px (xxxhdpi까지 지원, 픽셀기준 최대 4배)


### Pop 활성화 버튼
![POP_ACTIVE_BUTTON](./resources/benefit_pop_active_button.png)

Pop 활성화 버튼의 디자인은 아래 가이드에 따라 수정할 수 있습니다.

활성화 버튼의 색상과 아이콘은 [테마 적용](./11_Customizing.md)을 통해 변경할 수 있습니다.

활성화 버튼의 문구는 DefaultOptInAndShowPopButtonHandler의 상속 클래스에서 설정합니다. 상속 클래스를 작성하고 FeedConfig에 설정합니다.

```
public class CustomOptInAndShowPopButtonHandler extends DefaultOptInAndShowPopButtonHandler {
    // 활성화 버튼에 보여지는 문구입니다.
    @Override
    public String getOptInAndShowPopButtonText(Context context) {
        return "YOUR_BUTTON_TEXT";
    }
}
final FeedConfig feedConfig = new FeedConfig.Builder(context, "YOUR_FEED_UNIT_ID")
        .optInAndShowPopButtonHandlerClass(CustomOptInAndShowPopButtonHandler.class)
        .build();
```

### Notification UI 수정
![POP_NOTIFICATION](./resources/benefit_pop_notification.png)


Pop이 활성화되어 있는 동안에 Service Notification이 보입니다.<br>
다음은 PopNotificationConfig를 설정하여 SKP Ad Android SDK에서 제공하는 Notification 의 UI를 변경하는 방법입니다.
```
final PopNotificationConfig popNotificationConfig = new PopNotificationConfig.Builder(getApplicationContext())
      .smallIconResId(R.drawable.your_small_icon) // 흰색 아이콘, Adaptive Icon 이 설정하지 않도록 주의 요망
      .titleResId(R.string.your_pop_notification_title)
      .textResId(R.string.your_pop_notification_text)
      .colorResId(R.color.your_pop_notification_color)
      .notificationId(5000) // 기본값
      .build();
```
```
PopConfig popConfig = new PopConfig.Builder(context, "YOUR_POP_UNIT_ID")
      .popNotificationConfig(popNotificationConfig)
      .build();
```
UI 레이아웃 혹은 Notification 전체를 수정하기 위해서는 [Pop Service Notification 자체 구현](./6_POP-advanced.md)을 참고하시기 바랍니다.

### 스낵바 및 토스트 메시지 커스터마이징
![POP_SNACKBAR](./resources/benefit_pop_snackbar.png)

사용자에게 포인트를 지급할 때, 스낵바 혹은 토스트를 사용해 적립 내역을 표시합니다.<br>
기본 문구는 "광고 적립 포인트 n포인트 적립되었습니다." 입니다.<br>
다음은 DefaultPopFeedbackHandler의 상속 클래스를 구현하여, 문구를 수정한 예제입니다.
```
public class CustomPopFeedbackHandler extends DefaultPopFeedbackHandler {
     
    // 광고 적립에 성공 시, 호출됩니다.
    @Override
    public void notifyNativeAdReward(
            @NotNull Context context,
            @NotNull View view,
            boolean canUseSnackbar, //snackbar 사용 가능 여부
            int reward // 적립된 리워드 양
    ) {
        String message = "Customized feed launch reward message";
 
        if (canUseSnackbar) {
            showSnackbar(message, view); // 구현 필요
        } else {
            showToast(message); // 구현 필요
        }
    }
}
```


