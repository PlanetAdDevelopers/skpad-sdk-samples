## 광고/컨텐츠 설정

SKP Ad Screen스크린에 노출되는 광고와 컨텐츠에 대한 세부 설정을 선택할 수 있는 기능들입니다. 기능에 따라 유저가 개별 설정 가능하도록 UI를 오픈할 수 있는 경우도 있으니 자세한 내용은 각 기능 설명을 확인해주세요.<br><br>
하위 항목은 아래와 같습니다.

### 광고 컨텐츠 노출 비율
잠금화면 상하단 스크롤 시 화면에 나오는 광고와 컨텐츠의 비율을 조절할 수 있는 기능입니다. 단, 광고가 나올 순서에 노출 가능한 광고가 없을 경우 Backfill로 컨텐츠가 노출됩니다.<br>
유저에게 UI를 오픈하여 직접 설정값을 선택하도록 할 수도 있습니다.

||항목|코드 & 호출 위치|세부내용|
| :- | - | - | - |
|1|설정값 선택|```SKPAdScreen.getInstance().setSettings(SKPAdScreen.Settings.PAGE_DISPLAY_RATIO, String page_display_ratio)```|<p>page_display_ratio : 설정할 광고:컨텐츠 비율. 아래 값 들 중 선택 가능</p><p>"1\:1" "1\:2" "1\:3"|
|2|설정된 값 확인|```SKPAdScreen.getInstance().getSettings(SKPAdScreen.Settings.PAGE_DISPLAY_RATIO)```|<p>세팅된 String page\_display\_ratio 값을 리턴</p><p>위 1번의 setSettings() 로 설정한 값이 없을 경우 null 리턴</p>|

###  동영상 광고 자동재생
잠금화면 상하단 스크롤 시 화면에 나오는 광고와 컨텐츠의 비율을 조절할 수 있는 기능입니다. 단, 광고가 나올 순서에 노출 가능한 광고가 없을 경우 Backfill로 컨텐츠가 노출됩니다.<br>
유저에게 UI를 오픈하여 직접 설정값을 선택하도록 할 수도 있습니다.

||항목|코드 & 호출 위치|세부내용|
| :- | - | - | - |
|1|설정값 선택|<p>1. 인앱, 잠금화면 상 등에 자동재생 여 부 설정 가능한 UI 구현</p><p>2. SKPAdScreen.getInstance(). setAutoplayType(enum AutoplayType) 호출</p>|<p>setAutoplayType에 자동재생 설정값을 파라미터로 전달해야 함</p><p>SKPAdScreen.AutoplayType.ENABLED : 항상 자동재생 SKPAdScreen.AutoplayType.ON\_WIFI : 와이파이에서만 자동재생 SKPAdScreen.AutoplayType.DISABLED : 자동재생하지 않음</p>|
|2|설정된 값 확인|SKPAdScreen.getInstance(). getAutoplayType()|<p>세팅된 AutoplayType 값을 리턴!</p><p>위 1번의 setAutoplayType()로 설정한 값이 없을 경우 null 리턴</p>|
3|재생되고 있는 동영상의 현재 설정값 표시| onVideoAutoplay(AutoplayState autoplayState) |재생중인 동영상의 현재 설정값이 autoplayState 파라미터를 통해서 들어옴 <li>해당 값에 따라 이용자에게 메세지를 표시</li><br>protected void onVideoAutoplay(AutoplayState autoplayState) {<br>`    `int messageResourceId;<br>`    `switch (autoplayState) {<br>` ` ` `case AutoPlay:<br>` ` ` ` ` ` ` ` messageResourceId = R.string.autoplay\_enabled\_always;`        `<br>` ` ` ` ` ` ` ` break;<br>` ` ` ` case AutoPlayOnWIFI:<br>` ` ` ` ` ` ` ` messageResourceId = R.string.autoplay\_enabled\_on\_wifi;<br>` ` ` ` ` ` ` ` break;<br>` ` ` ` case NotAllowed:<br>` ` ` ` ` ` ` ` messageResourceId = R.string.autoplay\_disabled;<br>` ` ` ` ` ` ` ` break;<br>` ` ` ` default:<br>` ` ` ` ` ` ` ` return;<br>`    `}<br><br>` `final Snackbar snackbar = Snackbar.make(findViewById (android.R.id.content), messageResourceId, Snackbar. LENGTH\_SHORT);<br>...<br>`    `snackbar.show();<br>}<br>

