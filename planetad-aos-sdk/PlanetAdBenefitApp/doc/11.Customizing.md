## 커스터마이징

### 테마 적용

![Theme 적용](./resources/benefit_theme.png)

PlanetAD Benefit에서 제공하는 광고지면의 CTA 테마를 변경할 수 있습니다. 이 단계는 선택사항이긴 하지만 원하는 색상, 아이콘을 적용하는 단계이므로 적용하는 것을 권장합니다. 앱에서 사용중인 application theme에 PlanetAD에서 제공하는 attribute를 정의하여 간편하게 PlanetAD Benefit SDK의 CTA UI를 커스터마이징 할 수 있습니다. 

|attribute (type)|child attribute (type)|커스터마이징 되는 UI|
|-|-|-|
|skpadRewardIcon (reference)|N/A|<li>[All] cta view 리워드 아이콘</li><li>[Feed] profile banner 리워드 아이콘</li><li>[Feed] pop opt-in 버튼 아이콘</li>|
|skpadCtaViewStyle(reference)|<li>skpadCtaBackground - (reference) Cta 배경 설정</li><li>skpadCtaParticipatedIcon - (reference) 광고 참여 후 아이콘 설정</li><li>skpadCtaTextColor - (color\|reference) Cta 텍스트 색상 </li><li>skpadCtaTextSize - (dimension) Cta 텍스트 크기</li>|[All] cta view|
|<li>skpadColorPrimaryDark(reference\|color)</li><li>skpadColorPrimary (reference\|color)</li><li>skpadColorPrimaryLight(reference\|color)</li><li>skpadColorPrimaryLighter (reference\|color)</li><li>skpadColorPrimaryLightest (reference\|color)</li>|N/A|<li>[Feed] Tab UI, Filter UI, Pop FAB 배경색 등</li><li>[Interstitial] Feed 진입 경로 텍스트 색상</li><li>[Pop] Pop 아이콘 배경색, Toolbar UI, 다른 앱 위에 그리기 권한 다이얼로그 UI 등</li>


### PrimaryColor 변경
PlanetAD SDK 에서 제공하는 UI 중 Dialog의 버튼 색상, Bottom sheet UI의 확인 버튼을 포함하한 일부 UI 의 색상을 아래와 같이 Theme 에서 설정할 수 있습니다. 

```
<!-- Base application theme. -->
<style name="YourAppTheme" parent="Theme.AppCompat.DayNight.DarkActionBar">
    <item name="skpadColorPrimaryDark">@color/samplePrimaryDark</item>
    <item name="skpadColorPrimary">@color/samplePrimary</item>
    <item name="skpadColorPrimaryLight">@color/samplePrimaryLight</item>
    <item name="skpadColorPrimaryLighter">@color/samplePrimaryLighter</item>
    <item name="skpadColorPrimaryLightest">@color/samplePrimaryLightest</item>
</style>
```

### 리워드 아이콘
리워드를 표시하는 아이콘 추가를 위해 application theme에 skpadRewardIcon 를 추가합니다.
```
<!-- Base application theme. -->
<style name="YourAppTheme" parent="Theme.AppCompat.DayNight.DarkActionBar">
    <!-- 생략 -->
    <item name="skpadRewardIcon">@drawable/your_reward_icon</item>
</style>
```

### CTA 변경
광고의 CTA의 색상, 아이콘 등을 변경하기 위해 application theme에 skpadCtaViewStyle 를 추가합니다.

```
<!-- Base application theme. -->
<style name="YourAppTheme" parent="Theme.AppCompat.DayNight.DarkActionBar">
    <!-- 생략 -->
    <item name="skpadCtaViewStyle">@style/YourCtaViewStyle</item>
</style>

<style name="YourCtaViewStyle">
    <item name="skpadCtaBackground">@drawable/your_background</item>
    <item name="skpadCtaParticipatedIcon">@drawable/your_participated_icon</item>
    <item name="skpadCtaRewardIcon">@drawable/your_reward_icon</item>
    <item name="skpadCtaTextColor">@color/your_text_color</item>
    <item name="skpadCtaTextSize">14sp</item>
</style>
```

Cta 배경 예시

```
<!-- your_background.xml -->
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_pressed="true">
        <shape>
            <solid android:color="@color/colorPrimaryDark"/>
            <corners android:radius="4dp"/>
        </shape>
    </item>
    <item android:state_enabled="false">
        <shape>
            <solid android:color="@color/gray"/>
            <corners android:radius="4dp"/>
        </shape>
    </item>
    <item>
        <shape>
            <solid android:color="@color/colorPrimary"/>
            <corners android:radius="4dp"/>
        </shape>
    </item>
</selector>
```


### 커스텀 런처 설정

#### Custom In-App Browser 사용법
- 현재 Custom Launcher 를 사용하지 않을 경우 아래 가이드는 아무 영향이 없습니다.
- Custome Launcher 사용할 예정이거나, 1.X 버전에서 이미 사용하고 있을 경우, 아래의 항목을 필수로 적용해야 합니다.

Custom launcher 사용시, 광고를 클릭했을 때 랜딩되는 In-App Browser를 Customize 할 수 있습니다. 예를 들어, 광고 랜딩 페이지 로드 등을 매체사가 지정하는 Class에서 구현할 수 있습니다.

##### 구현시 주의사항 :

- SKPAdBrowser에서 제공하는 Fragment를 사용하여 In-App Browser를 구현해야 합니다. 사용하지 않을 경우, 일부 광고(액션형 광고, 체류 리워드 광고)가 제대로 동작하지 않을 수 있습니다.
- Launcher에서 제공하는 LandingInfo 의 URL을 임의로 변경해서 사용하면 안됩니다. 이 경우 웹 페이지가 제대로 로드되지 않을 수 있습니다.

1. CustomBrowserActivity 를 구현합니다.
```
public class CustomBrowserActivity extends AppCompatActivity {
    public static final String KEY_URL = "com.sample.KEY_URL";
    private SKPAdBrowserFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_browser);

        // URL을 KEY로 하여 WebView를 가지고있는 Fragment를 받아와 사용합니다.
        Intent intent = getIntent();
        fragment = SKPAdBrowser.getInstance(this).getFragment(intent.getStringExtra(KEY_URL));
        getSupportFragmentManager().beginTransaction().replace(R.id.browserContainer, fragment).commit();
        final SKPAdWebView webView = fragment.getWebView();

        // Browser의 이벤트를 받을 수 있습니다. DeepLink가 열렸을 경우, Browser를 닫아주어야 빈 페이지가 보여지는 현상을 방지할 수 있습니다.
        SKPAdBrowser.getInstance(this).setOnBrowserEventListener(new SKPAdBrowser.OnBrowserEventListener() {
        
        // 기존에 사용하던 isDeepLink 를 아래로 대체
            @Override
            public void onDeepLinkOpened() {
                finish();
            }
        });
    }

    // Optional - BackButton을 눌렀을때 뒤로가기 기능
    @Override
    public void onBackPressed() {
    final SKPAdWebView webView = fragment.getWebView();
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
```

2. activity_custom_browser.xml
```
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <FrameLayout
        android:id="@+id/browserContainer"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</FrameLayout>
```

3. Launcher 를 구현
```
public class MyLauncher implements Launcher {

    @Override
    public void launch(@NonNull Context context, @NonNull LaunchInfo launchInfo) {
        launch(context, launchInfo, null);
    }

    @Override
    public void launch(@NonNull final Context context, @NonNull final LaunchInfo launchInfo, @Nullable final LauncherEventListener listener) {
        launch(context, launchInfo, listener, null);
    }

    @Override
    public void launch(@NonNull final Context context, @NonNull final LaunchInfo launchInfo, @Nullable final LauncherEventListener listener, @Nullable List<Class<? extends SKPAdJavascriptInterface>> javascriptInterfaces) {
                 
        // Custom Browser 실행
        final Intent intent = new Intent(context, CustomBrowserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(CustomBrowserActivity.KEY_URL, launchInfo.getUri().toString()); // URI는 변경 하면 안 됨
        context.startActivity(intent);
    }
}
```
4. SKPAdBenefit.init 호출 이후에 생성한 Launcher를 세팅
```
SKPAdBenefit.setLauncher(new MyLauncher());
```

##### Custom launcher 사용시 Article의 sourceUrl 사용법
```
public class MyLauncher implements Launcher {
    @Override
    public void launch(@NonNull final Context context, @NonNull final LaunchInfo launchInfo, @Nullable final LauncherEventListener listener, @Nullable List<Class<? extends SKPAdJavascriptInterface>> javascriptInterfaces) {
        if (launchInfo.getArticle() != null) {
            String sourceUrl = launchInfo.getArticle().getSourceUrl();
        }
    }
}
```
컨텐츠의 경우 url scheme에 따라 랜딩 방식을 다르게 처리하고 싶다면 (ex. 앱 안에서 브라우저 오픈 없이 다른 화면으로 이동되는 컨텐츠) 다음과 같은 방법으로 NativeArticle 객체의 sourceUrl을 가져와 분기 처리를 할 수 있습니다.

#####  Custom launcher 사용시 광고 또는 컨텐츠인지 미리 판단하고 싶을 경우
```
public class MyLauncher implements Launcher {
    ...
    @Override
    public void launch(@NonNull final Context context, @NonNull final LaunchInfo launchInfo, @Nullable final LauncherEventListener listener, @Nullable List<Class<? extends SKPAdJavascriptInterface>> javascriptInterfaces) {
        
        // 광고 또는 컨텐츠인지 미리 판단하고 싶을 경우, 다음을 이용하여 확인
        if (launchInfo.getAd() != null) {
            // 광고
        } else if (launchInfo.getArticle() != null) {
            // 컨텐츠
        } 
          
        ...// Custom Browser 실행
    }
}
```
