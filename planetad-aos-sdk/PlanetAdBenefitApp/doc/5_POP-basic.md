## POP-기본설정

### 개요
![POP basic](./resources/benefit_pop_basic.png)

Pop은 스크린 최상단에 뜨는 UI를 통해 사용자를 광고 지면으로 유도합니다. Pop을 활성화하면 화면을 껐다 켤 때마다 팝이 화면에 보이게 됩니다.<br>
Android 12에 적용되는 오버레이의 터치 이벤트 차단에 대응하기 위해 팝(Pop) 버튼에 투명도를 적용하여 안정적인 앱 동작을 지원합니다.

### 준비사항

[Feed 지면 기본 설정](./1_Feed-basic.md) 완료<br>
Pop 지면에 사용할 Unit ID (이하 YOUR_POP_UNIT_ID)

### Pop 준비
YOUR_POP_UNIT_ID와 Feed 지면에서 사용한 Unit id([Feed 지면 기본 설정](./1_Feed-basic.md)에서의 YOUR_FEED_UNIT_ID) 를 동일한 값으로 하게 설정하는 경우, FeedConfig에 optInFeatureList를 설정하여 간편히 Pop 지면을 도입할 수 있습니다.<br>
optInFeatureList 를 사용하면 FeedConfig의 설정 중의 일부가 Pop 지면에도 적용됩니다.<br> Pop 지면에서 Feed와 다른 Unit id 를 사용하기, Feed 지면과 다른 설정을 사용하기 를 위해서는 [PopConfig 설정](./6_POP-advanced.md)을 참고하여 PopConfig를 사용해야 합니다.<br>
다음은 FeedConfig를 설정하는 예시입니다.

```
FeedConfig feedConfig = new FeedConfig.Builder(getApplicationContext(), "YOUR_POP_UNIT_ID")
        .optInFeatureList(Collections.singletonList(OptInFeature.Pop))
        .build();
final SKPAdBenefitConfig skpAdBenefitConfig = new SKPAdBenefitConfig.Builder(context)
        .setFeedConfig(feedConfig)
        .build();
SKPAdBenefit.init(this, skpAdBenefitConfig);
```


Pop이 준비되면 Feed 지면에 Pop 활성화 버튼이 노출됩니다. 자세한 사항은 [Pop 활성화 버튼](./6_POP-advanced.md)에서 확인할 수 있습니다.

### Pop 초기화 및 활성화
![POP permission](./resources/benefit_pop_permission.png)

마시멜로(Android API 23) 이상 버전에서 Pop을 실행시키기 위해서 다른 앱 위에 그리기 권한이 필요합니다. 원활한 권한 획득을 위해 SKP Ad Android SDK는 사용자가 권한을 활성화하도록 유도하는 기능을 제공하고 있습니다.<br>
다음 가이드에 따라 다른 앱위에 그리기 권한을 받고 Pop 을 활성화 할 수 있습니다.<br><br>

Activity에 SKPAdPop 클래스를 멤버 변수로 추가합니다.
```
private SKPAdPop skpAdPop;
```

Pop 실행 전, 다른 앱 위에 그리기 권한을 얻어야 합니다. requestPermissionWithDialog를 호출하여 유저에게 다른 앱 위에 그리기 권한을 부여하도록 유도할 수 있습니다.
```
public static final int REQUEST_CODE_SHOW_POP = 1024;
 
public void showPopOrRequestPermissionWithDialog() {
    // 권한 확인
    if (SKPAdPop.hasPermission(context) || Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        // Pop 실행
        skpAdPop.preloadAndShowPop();
    } else {
        // 권한 요청
        SKPAdPop.requestPermissionWithDialog((Activity) context,
            new PopOverlayPermissionConfig.Builder(R.string.pop_name)
                .settingsIntent(OverlayPermission.createIntentToRequestOverlayPermission(context))
                .requestCode(REQUEST_CODE_SHOW_POP)
                .build()
        );
    }
}
```

다른 앱 위에 그리기 권한이 부여되면 자동으로 Activity로 돌아오며, Intent를 통해 결과를 확인합니다. 권한 획득이 확인되면 preloadAndShowPop을 호출하여 Pop을 실행합니다.

```

import static com.skplanet.lib.settingsmonitor.SettingsMonitor.KEY_SETTINGS_REQUEST_CODE;
import static com.skplanet.lib.settingsmonitor.SettingsMonitor.KEY_SETTINGS_RESULT;
 
public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         
        if (getIntent().getBooleanExtra(KEY_SETTINGS_RESULT, false)
                && getIntent().getIntExtra(KEY_SETTINGS_REQUEST_CODE, 0) == REQUEST_CODE_SHOW_POP) {
            // 권한 획득 확인 후 pop 실행
            skpAdPop.preloadAndShowPop();
        }
    }
}
```

### Pop 비활성화
skpAdPop.removePop(Context) 을 호출하여 Pop을 비활성화 할 수 있습니다. Pop이 비활성화되면 Service Notification이 사라집니다.<br>
다음은 Pop을 비활성화하는 예시입니다.
```
skpAdPop.removePop(context);
```
