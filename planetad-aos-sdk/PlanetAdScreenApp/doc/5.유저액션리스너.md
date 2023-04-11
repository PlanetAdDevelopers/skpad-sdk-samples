## User Action Listener

SKPAdScreen 상에서 유저가 취하는 액션에 따라 해당 액션을 트래킹하거나 포인트 적립 시도 등의 알림을 줄 수 있습니다.<br>
하위 항목은 아래와 같습니다.<br>

### 임프레션 및 클릭 이벤트 트래킹

SKPAdScreen에서 임프레션, 클릭 발생 시 원하는 기능을 구현할 수 있습니다. setOnTrackingListener()를 이용하여 TrackingListener를 설정합니다.

"임프레션", "클릭"이란?<br>


|항목|설명|
| - | - |
임프레션| 잠금화면에 광고 혹은 컨텐츠가 뜬 시점
클릭| 유저가 슬라이더를 좌측으로 스와이프하여 랜딩에 시도한 시점<br>시스템 잠금화면 등으로 인해 아직 브라우저가 열리지 않았어도 호출됨

```java
setOnTrackingListener(new OnTrackingListener() {
 
    @Override
    public void onImpression(Campaign campaign) {
        //Impression 시 원하는 기능 구현
    }
 
    @Override
    public void onClick(Campaign campaign) {
        //Click 시 원하는 기능 구현
    }
 
});
```

### 적립 시도 성공/실패 알림
<p>SKPAdScreen에서 유저가 얻은 포인트에 대해 적립 시도가 발생했다는 알림을 줄 수 있습니다.</p>
<p>본 리스너는 적립 시도에 대한 리스너이기 때문에 퍼블리셔 서버에서 실제 적립 처리를 했는지와는 별개이므로, 실제 적립이 완료된 뒤에 알림을 주고자 할 경우 퍼블리셔가 직접 구현해야 합니다.</p>
본 리스너를 사용할 경우 아래와 같은 토스트 메시지를 띄울 수 있습니다. (point = N으로 가정) 광고 페이지를 보면 N포인트를 적립받을 수 있습니다.<br>



|코드 & 호출 위치| Response|
| - | - |
```SKPAdScreen.getInstance().setOnPointListener(OnPointListener listener)```<br><br><li>interface OnPointListener : 포인트 적립 시도 성공 여부를 확인할 수 있는 interface</li><li>반드시 SKPAdScreen.init() 호출 이후 호출해야함</li>|<li>onSuccess(PointType type, int points) : 포인트 적립 시도가 성공적으로 발생했을 때</li><li>onFail(PointType type) : 유저의 네트워크 에러로 현재 적립 시도가 불가능할 때</li><ul><li>PointType : 해당 포인트가 발생한 원인</li><ul><li>UNLOCK : 오른쪽 스와이프 - 잠금해제</li><li>LANDING : 왼쪽 스와이프 - 페이지 랜딩</li></ul></ul>

#### 주의사항
- 알림으로 뜨는 포인트는 즉시 적립 가능한 포인트만을 의미합니다. 회원가입, 앱 실행형 등 액션을 마쳐야 적립이 이루어지는 광고의 경우 알림을 주지 않습니다.
- 이러한 상황에 대해서도 알림을 구현하려고 하는 경우에는 OnTrackingListener의 onClick(Campaign campaign) 메소드를 이용해서 처리할 수 있습니다. (위 "임프레션 및 클릭 이벤트 트래킹"을 참고하세요.)
- onClick() 의 파라미터로 전달되는 캠페인에 대해 campaign.getActionPoints()를 통해 액션형 포인트를 얻은 후, 이 값이 0보다 크다면 해당 캠페인은 액션형 캠페인이므로 이 정보를 통해 알림을 직접 구현합니다.

```java
SKPAdScreen.getInstance().setOnPointListener(new OnPointListener() {
 
    @Override
    public void onSuccess(PointType type, int points) {
        // 적립 요청 성공 메세지
        Toast.makeText(getApplicationContext(), points + " p 적립 요청이 완료되었습니다.", Toast.LENGTH_LONG).show();
    }
 
    @Override
    public void onFail(PointType type) {
        // 포인트 적립 실패 메세지
        Toast.makeText(getApplicationContext(), "네트워크 문제로 적립되지 않았습니다", Toast.LENGTH_LONG).show();
    }
 
});
```