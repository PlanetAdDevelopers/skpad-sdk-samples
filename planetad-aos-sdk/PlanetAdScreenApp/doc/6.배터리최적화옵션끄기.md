## 배터리 최적화 옵션 끄기

<p>안드로이드 P 정책에 따라, 기기의 배터리 최적화 과정에서 SKPAdScreen 앱의 동작이 제한되어 잠금 화면이 뜨지 않을 수 있습니다. 안내 메시지를 띄워서 유저가 직접 배터리 최적화 대상에서 SKPAdScreen 앱을 제외하도록 유도할 수 있습니다.</p>

### 안내 메세지 보여주는 옵션 설정

|API|호출위치|Parameter|주의|
|-|-|-|-|
showBatteryGuideIfNeeded(Activity mainActivity, View view)<br><br>안내 메시지가 보일 액티비티와 뷰를 파라미터로 전달합니다.| MainActivity의 onCreate()| <li>mainActivity: 안내 메시지가 보일 메인 액티비티</li><li>view: Content view</li>|<li>액티비티의 테마가 AppCompat 테마일 경우 Snackbar가 나오고</li><li>다른 테마일 경우 Dialog가 나오게 되며 두 번째 파라미터인 view는 사용되지 않습니다.</li>

```java
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    ...
    SKPAdScreen.getInstance().showBatteryGuideIfNeeded(this, findViewById(android.R.id.content));
    ...
    }
}
```