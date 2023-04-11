## 일시적 비활성화
SKP AD Screen을 일시적으로 비활성화하기 위해 사용할 수 있는 기능입니다.

유저가 잠금화면을 비활성화하려고 하는 순간 특정 시간 뒤에 자동으로 다시 활성화시킬 수 있는 선택지를 제공함으로써 유저 수가 감소하는 것을 방어 할 수 있습니다.


|분류|항목|코드|호출 위치|세부내용|
| - | - | - | - | - |
일시적으로 잠금화면 을 비활성화하는 기 능|특정 시간 동안 비활성화|```SKPAdScreen. getInstance().snooze(int snoozeForSecs)```|<p>SKP AD Screen이 활성화된 상태에서 비활성화하려 할 때 옵션으로 제공 </p><p>일시적 비활성화 후에 SKPAdScreen.getInstance(). activate() 를 호출하면 바로 다시 잠금화면이 보이게 됩 니다.</p>|<p><li>snoozeForSecs 초 동안 SKP AD Screen이 잠금화면에 나타 나지 않음</li><li>snoozeForSecs 후에 다시 자동 으로 SKP AD Screen이 보이게 됨</li>
||재부팅될 때까지 비활성화|```SKPAdScreen.getInstance().deactivateUntilReboot()```||<li>재부팅될 때까지 SKP AD Screen이 잠금화면에 나타나지 않음</li><li>재부팅 후에 다시 자동으로 SKP AD Screen이 보이게 됨</li>
일시적 비활성화 상 태 확인 기능|특정 시간 동안 쉬는 상태인지 확 인|```SKPAdScreen.getInstance().isSnoozed()```|활성화 여부 확인하는 지점|현재 SKP AD Screen이 snoozeForSecs 초 동안 쉬는 상태인 지 확인|
||재부팅 전까지 쉬 는 상태인지 확인|```SKPAdScreen.getInstance().isDeactivatedUntilReboot()```||현재 SKP AD Screen이 재부팅 전까 지 쉬는 상태인지 확인


#### SKP AD Screen이 보여지는 조건
- SKPAdScreen.getInstance().isActivated() && !SKPAdScreen.getInstance().isSnoozed() && !SKPAdScreen.getInstance(). isDeactivatedUntilReboot()
