## Web Android SDK 연동 가이드

### 개요
이 가이드는 Android 앱 내의 WebView에서 광고를 표시하기 위한 PlanetAD Benefit Web Android용 SDK 연동 방법을 안내합니다.

#### 1단계 연동용 ID 발급받기

SKP AD  Android용 SDK를 연동하려면 반드시 앱의 고유 식별자인 App ID와 광고 지면의 고유 식별자 Unit ID가 필요합니다. 연동용 ID를 발급받으려면 SKP 광고 담당자에게 연락하세요.

|ID 유형|설명|
|-|-|
App ID|SKP AD Android용 SDK를 연동하는 앱 별로 부여하는 고유 식별자입니다.
Unit ID|앱 내에 광고 지면별로 부여하는 고유 식별자입니다.

#### 2단계 SDK 설치하기
SKP Ad Android용 SDK를 설치하려면 다음의 절차를 따르세요.
1. 프로젝트 레벨의 build.gradle 파일에 SKP Ad SDK 저장소를 추가하세요.
```
// 프로젝트 레벨의 build.gradle
 
allprojects {
    repositories {
        maven { url "https://asia-northeast3-maven.pkg.dev/planetad-379102/planetad" } // SKP 저장소
    }
}
```
2. 모듈 레벨의 build.gradle 파일에 SKP Ad SDK 라이브러리를 추가하세요.

```
// 모듈 레벨의 build.gradle
 
dependencies {
    implementation ("com.skplanet.sdk.ad:skpad-benefit:1.0.0.8-SNAPSHOT") { changing = true }  // SKP AD Benefit SDK 라이브러리
}
```

3. 모듈 레벨의 build.gradle 파일에 compileSdkVersion과 targetSdkVersion을 31로 업데이트하세요.

```
android {
    compileSdkVersion 31
 
    defaultConfig {
        targetSdkVersion 31
    }
}
```

#### 3 단계: App ID 설정하기
AndroidManifest.xml 파일에서 다음과 같이 <meta-data> 요소를 추가하고, app-pub-{YOUR_APP_ID}의 {YOUR_APP_ID}를 SKP 광고 담당자로부터 발급받은 App ID로 교체하세요.<br>

발급받은 App ID가 123456789123 이라면 app-pub-123456789123가 되어야 합니다.
```
<manifest>
    <application>
        <!-- SKPAd SDK App id -->
        <meta-data
            android:name="com.skplanet.APP_KEY"
            android:value="app-pub-000000000000" />
    </application>
</manifest>
```

#### 4 단계: SDK 초기화하기
Application의 onCreate()에서 다음의 코드를 추가하여 SKP Ad Android용 SDK를 초기화하세요.

```
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
                 
        // SKPAdBenefit 초기화
        final SKPAdBenefitConfig skpAdBenefitConfig = new SKPAdBenefitConfig.Builder(context)
            .build();
        SKPAdBenefit.init(this, skpAdBenefitConfig);
    }
}
```

#### 5단계: 사용자 프로필 등록하기
광고 할당을 요청하려면 사용자 프로필을 등록해야 합니다. 사용자 프로필을 구성하는 항목은 아래 표를 참고하세요.

|사용자 프로필|설명|
|-|-|
userId|매체사 앱에서 사용하는 사용자 식별자입니다. 서비스 도중 변하지 않는 고정 값이어야 하며, 광고 할당을 위해서 필수로 전달해야합니다.<br>앱을 삭제 후 재설치하여 사용자의 ID 값이 변경되거나 다른 사유로 인해 고정 ID를 사용하지 못하는 경우 SKP 광고 담당자에게 문의하세요.
gender| 사용자의 성별입니다. 사용자 맞춤형 광고를 제공하는 데에 활용됩니다.<li>남성: UserProfile.Gender.MALE</li><li>여성: UserProfile.Gender.FEMALE</li>
birthYear|사용자의 출생연도입니다. 사용자 맞춤형 광고를 제공하는 데에 활용됩니다.

사용자가 로그인하는 시점에 다음의 코드를 추가하여 SDK에 사용자 프로필을 등록하세요.<br>
원활한 서비스 운영을 위해 사용자 프로필 등록은 반드시 호출해야합니다. SKPAdBenefit.setUserProfile() 메소드를 호출하지 않으면 광고가 제공되지 않습니다.<br>
사용자의 로그인 정보를 웹 페이지에서 알 수 있지만 앱에서 알 수 없는 경우, 웹 페이지에서 사용자 프로필을 설정하는 기능을 지원하고 있습니다.<br>

```
// 사용자 정보를 등록하는 코드입니다.
final UserProfile.Builder builder = new UserProfile.Builder(SKPAdBenefit.getUserProfile());
final UserProfile userProfile = builder
        .userId("USER_ID") // 사용자 식별자값
        .gender(UserProfile.Gender.MALE) // 사용자의 성별
        .birthYear(2000) // 출생연도
        .build();
SKPAdBenefit.setUserProfile(userProfile);
```

사용자가 앱에서 로그아웃하는 시점에 다음과 같이 사용자 프로필 정보를 삭제하세요.
```
// SDK에 등록한 사용자 프로필을 삭제하는 코드입니다.
SKPAdBenefit.setUserProfile(null);
```



#### 6 단계: 광고를 표시할 WebView 설정하기
웹 SDK와 소통할 수 있도록 광고를 표시하려고 하는 웹뷰에 다음 코드를 추가하세요.
```
WebView webview = (WebView) findViewById(R.id.webView);
 
final SKPAdBenefitJavascriptInterface javascriptInterface = new SKPAdBenefitJavascriptInterface(webView);
webView.getSettings().setJavaScriptEnabled(true); // JS를 사용하여 광고를 로드하기 때문에 필수임
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    // 롤리팝부터 Mixed Content 에러 막기 위함
    webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
}
webView.addJavascriptInterface(javascriptInterface, SKPAdBenefitJavascriptInterface.INTERFACE_NAME);
```
 

#### 7 단계: Benefit JS SDK가 삽입된 웹 페이지 로드하기
Benefit JS SDK가 삽입된 웹 페이지를 열 경우, Android 코드에서 설정한 UserProfile 정보 등을 Web SDK에서 자동으로 받아 광고를 로드합니다.
```
webView.loadUrl(MY_WEB_PAGE);
```

#### 8 단계: 앱 빌드하기
Planet AD Android SDK를 사용하기 위한 모든 설정이 완료되었습니다. 앱을 빌드하고 정상적으로 실행되는지 확인하세요.

