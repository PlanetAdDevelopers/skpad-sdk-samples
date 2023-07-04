## POP-고급설정

### 개요
본 가이드에서는 SKP Android SDK의 Pop 지면의 기능을 설명하고 각 기능을 변경하는 방법을 설명합니다.

![POP_AREA_GUIDE](./resources/benefit_pop_area_guide.png)

### PopConfig 설정

<p>PopConfig를 통해 Pop의 기능을 설정할 수 있습니다.</p>
다음은 PopConfig를 SKPAdBenefitConfig에 추가하는 예시입니다.

```
PopConfig popConfig = new PopConfig.Builder(getApplicationContext(), "YOUR_POP_UNIT_ID")
        .build();
         
final SKPAdBenefitConfig skpAdBenefitConfig = new SKPAdBenefitConfig.Builder(context)
        .setPopConfig(popConfig)
        .build();
         
SKPAdBenefit.init(this, skpAdBenefitConfig);
```

### 콘텐츠 숨기기
Pop 에서는 기본적으로 콘텐츠를 함께 제공하고 있습니다. 콘텐츠는 광고가 아니며 뉴스 기사와 같은 아이템입니다.<br>
만일 콘텐츠를 Pop 에서 보이지 않도록 하기 위해서는 다음과 같이 설정하여 숨길 수 있습니다.

```
PopConfig popConfig = new PopConfig.Builder(getApplicationContext(), "YOUR_POP_UNIT_ID")
        .articlesEnabled(false) // 컨텐츠 비활성화
        .build();
```

### Pop 활성화 버튼

![POP_BUTTON](./resources/benefit_pop_button_guide.png)

Feed 지면에 Pop 지면 활성화 버튼을 표시할 수 있습니다. 사용자는 Pop 지면 활성화 버튼을 통해 자연스럽게 Pop 을 활성화할 수 있습니다.
사용자에게 Pop 활성화 버튼을 표시하는 방법은 다음과 같습니다.

- PopConfig를 설정하지 않고, FeedConfig를 아래와 같이 설정한 경우

```
FeedConfig feedConfig = new FeedConfig.Builder(getApplicationContext(), YOUR_FEED_UNIT_ID)
        .optInFeatureList(Collections.singletonList(OptInFeature.Pop))
        .build();
```
- PopConfig를 설정한 경우
```
PopConfig popConfig = new PopConfig.Builder(getApplicationContext(), "YOUR_POP_UNIT_ID")
        .build();
         
final SKPAdBenefitConfig skpAdBenefitConfig = new SKPAdBenefitConfig.Builder(context)
        .setPopConfig(popConfig) // PopConfig 설정
        .build();
SKPAdBenefit.init(this, skpAdBenefitConfig);
```

위의 조건을 충족하도록 연동했다면, Pop을 활성화하지 않은 사용자에게 Pop 활성화 버튼이 보입니다.<br><br>

Pop 활성화 버튼을 표시하지 않기 위해서는 아래와 같이 설정해야합니다. 위의 표시 가능 조건과 무관하게 Pop 활성화 버튼은 보이지 않습니다.
```
final FeedConfig feedConfig = new FeedConfig.Builder(context, "YOUR_FEED_UNIT_ID")
        .optInAndShowPopButtonHandlerClass(null) // Pop 활성화 버튼 숨김
        .build();
```

### Pop Service Notification 자체 구현
![POP_NOTIFICATION](./resources/benefit_pop_notification.png)

Pop이 정상적으로 동작하기 위해서 Service 를 필요로 합니다. 그래서 Pop이 활성화되어 있는 동안에 Service Notification이 보입니다.

다음은 Service Notification 을 직접 구현하여 SKPAd Android SDK에서 제공하는 Notification 을 대체하는 방법을 안내합니다. Notification의 동작, UI 레이아웃까지 직접 구현하여 수정할 수 있습니다.<br>만일 SKPAd Android SDK에서 제공하는 Notification 을 기반으로 간단한 UI 수정을 원하는 경우는 [Notification UI 수정](./7_POP-Customizing.md)을 참고하시기 바랍니다.

Pop Notification 을 수정하기 위해서는 PopControlService 의 상속 클래스를 구현합니다. 필요에 따라 notificationChannel 을 생성하거나 View 를 등록할 수 있습니다.<br>
 NotificationId는 PopNotificationConfig에서 설정할 수 있습니다. 구현한 상속 클래스는 Manifest에 등록해야 합니다.<br>
PopControlService 은 몇 가지 편리한 기능을 제공합니다. 필요에 따라 사용할 수 있습니다.


|API|설명|
|-|-|
getPopPendingIntent(unitId, context)|Pop 지면으로 진입하는 PendingIntent 를 제공합니다.

아래는 Pop Service Notification 자체 구현하는 예시입니다.
```
public class YourControlService extends PopControlService {
 
    @Override
    protected Notification buildForegroundNotification(@NonNull String unitId, @NonNull PopNotificationConfig popNotificationConfig) {
        // Pop을 표시하는 PendingIntent (원형 아이콘)
        PendingIntent popPendingIntent = getPopPendingIntent(unitId, this);
 
        // 필요에 따라 notificationChannel을 등록합니다.
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannelIfNeeded();
            builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(this);
        }
 
        // Pop Service Notification 에 사용할 View 를 등록합니다.
        RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.view_custom_notification);
        builder.setSmallIcon(popNotificationConfig.getSmallIconResId())
                .setContent(remoteView)
                .setContentIntent(popPendingIntent)
                .setPriority(PRIORITY_LOW)
                .setShowWhen(false);
                .setForegroundServiceBehavior(FOREGROUND_SERVICE_IMMEDIATE);
        if (popNotificationConfig.getColor() != null) {
            builder.setColor(popNotificationConfig.getColor());
        }
        return builder.build();
    }
 
    @TargetApi(Build.VERSION_CODES.O)
    protected void createNotificationChannelIfNeeded() {
        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null) {
            final NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            channel.setShowBadge(false);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
```
```
final PopNotificationConfig popNotificationConfig = new PopNotificationConfig.Builder(getApplicationContext())
                .notificationId(NOTIFICATION_ID)
                .build();
 
final PopConfig popConfig = new PopConfig.Builder(getApplicationContext(), UNIT_ID_POP)
        .popNotificationConfig(popNotificationConfig)
        .controlService(YourControlService.class)
        .build();
 
final SKPAdBenefitConfig skpAdBenefitConfig = new SKPAdBenefitConfig.Builder(getApplicationContext())
        .setPopConfig(popConfig)
        .build();
```
```
// AndroidManifest.xml
 
<application
    ...생략...
     
    <service android:name=".java.YourControlService" />
     
    ...생략...
</application>
```

### 툴바 영역 View 자체 구현

![POP_BASIC_TOOLBAR_GUIDE](./resources/benefit_pop_basic_toolbar_guide.png)

Pop지면의 툴바 영역의 UI를 변경할 수 있습니다. SKP Android SDK가 제공하는 UI을 이용하여 변경하는 방법과 사용하지 않고 변경하는 방법이 있습니다.

#### SDK에서 제공하는 UI를 이용하여 변경
![POP_SDK_TOOLBAR_GUIDE](./resources/benefit_pop_sdk_toolbar_guide.png)

기본으로 제공되는 UI를 이용하여 변경하는 방법입니다. 간단하지만 제약이 있습니다.

DefaultPopToolbarHolder의 상속 클래스는 구현하여 툴바를 변경합니다. SDK에서 제공하는 PopToolbar 를 이용하여 정해진 레이아웃에서 변경합니다. 상속 클래스는 PopConfig의 feedToolbarHolderClass에 설정합니다.

다음은 SKPAd Android SDK에서 제공하는 UI를 이용하여 구현하는 예시입니다.
```
class YourPopToolbarHolder extends DefaultPopToolbarHolder {
    @Override
    public View getView(Activity activity, @NonNull final String unitId) {
        toolbar = new PopToolbar(activity); // PopToolbar 에서 제공하는 기본 Template 사용
        toolbar.setTitle("TemplatePopToolbarHolder"); // 툴바 타이틀 문구를 변경합니다.
        toolbar.setIconResource(R.mipmap.ic_launcher); // 툴바 좌측 아이콘을 변경합니다.
        toolbar.setBackgroundColor(Color.LTGRAY); // 툴바 배경색을 변경합니다.
 
        addInquiryMenuItemView(activity, unitId); // 문의하기 버튼은 이 함수를 통해 간단하게 추가 가능합니다.
        addRightMenuItemView1(activity, unitId); // custom 버튼 추가
        return toolbar;
    }
 
    // custom 버튼 추가는 toolbar.buildPopMenuItemView 를 사용하여 PopMenuImageView 를 생성하고
    // toolbar.addRightMenuButton 를 사용하여 toolbar 에 추가합니다.
    private void addRightMenuItemView1(@NonNull final Activity activity, @NonNull final String unitId) {
        PopMenuImageView menuItemView = toolbar.buildPopMenuItemView(activity, R.mipmap.ic_launcher);
        menuItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInquiry(activity, unitId); // 문의하기 페이지로 연결합니다.
            }
        });
        toolbar.addRightMenuButton(menuItemView);
    }
}
```
```
new PopConfig.Builder(getApplicationContext(), "YOUR_POP_UNIT_ID")
      .feedToolbarHolderClass(YourPopToolbarHolder.class)
      .build();
```

#### Custom View를 직접 구현하여 UI를 변경
![POP_CUSTOM_VIEW_TOOLBAR_GUIDE](./resources/benefit_pop_custom_toolbar_toolbar_guide.png)

DefaultPopToolbarHolder의 상속 클래스는 구현하여 툴바를 변경합니다. SDK에서 제공하는 PopToolbar를 사용하지 않고 직접 구성한 레이아웃을 사용합니다. 그리고 구현한 상속 클래스는 PopConfig에 설정합니다.<br>
다음은 레이아웃을 직접 구성하여 구현하는 예제입니다.

```
public class YourPopToolbarHolder extends DefaultPopToolbarHolder {
    @Override
    public View getView(Activity activity, @NonNull final String unitId) {
        // 직접 구성한 layout 을 사용합니다
        ViewGroup root =  (ViewGroup) activity.getLayoutInflater().inflate(R.layout.your_pop_custom_toolbar_layout, null);
 
        View buttonInquiry = root.findViewById(R.id.yourInquiryButton);
        buttonInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 문의하기 페이지 열기
                showInquiry(activity, unitId);
            }
        });
        return root;
    }
}
```
```
new PopConfig.Builder(getApplicationContext(), "YOUR_POP_UNIT_ID")
      .feedToolbarHolderClass(YourPopToolbarHolder.class)
      .build();
```


### 유틸리티 영역 UI 변경

![POP_UTILITY_GUIDE](./resources/benefit_pop_utility_guide.png)

유틸리티 영역을 활용하여 사용자에게 편리한 기능을 제공합니다.

다음은 유틸리티 영역을 변경하는 예시입니다.

PopUtilityLayoutHandler의 상속 클래스를 구현하고, 구현한 Custom View(your_pop_utility_view)를 추가합니다. 그리고 FeedConfig에 구현한 클래스를 추가합니다.

```
public final class CustomPopUtilityLayoutHandler extends PopUtilityLayoutHandler {
 
    private Context context;
 
    public CustomPopUtilityLayoutHandler(Context context) {
        super(context);
        this.context = context;
    }
 
    @Override
    public void onLayoutCreated(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final FrameLayout layout = (FrameLayout) inflater.inflate(
                R.layout.your_pop_utility_view,
                parent,
                false
        );
        parent.addView(layout);
    }
}
```
```
new PopConfig.Builder(getApplicationContext(), "YOUR_POP_UNIT_ID")
      .popUtilityLayoutHandlerClass(CustomPopUtilityLayoutHandler.class)
      .build();
```

#### 유틸리티 영역 아이콘 디자인 규격
- 추천 이미지 사이즈
  - 24*24 dp (mdpi 기준)
  - 96*96 px (xxxhdpi까지 지원, 픽셀기준 최대 4배)
- 아이콘은 PNG 와 벡터이미지가 모두 가능합니다.
- 컬러 아이콘 사용 가능

### 커스텀 페이지 추가
Pop 지면을 이용하여 원하는 내용을 표시할 수 있습니다. 커스텀 페이지는 툴바와 컨텐츠로 이루어져있습니다.

![POP_CUSTOM_PAGE_GUIDE](./resources/benefit_pop_custom_page.png)

- 툴바에는 타이틀을 설정할 수 있습니다.
- 컨텐츠영역에 원하는 Fragment를 설정할 수 있습니다.

아래 예시 코드에 따라 구현할 수 있습니다.
```
new PopNavigator().launchCustomFragment(
    context,
    new CustomInAppLandingInfo(
        new YourFragment(),
        R.stirng.your_title
    )
);
```

커스텀 페이지는 자유롭게 구현하여 사용할 수 있습니다. 예를 들어, 유틸리티 영역 혹은 툴바 영역에 버튼을 추가하고 원하는 페이지를 보여주기 위해 사용합니다. 

