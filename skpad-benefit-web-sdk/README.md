# SKPAdBenefit WEB SDK 연동 가이드

## 목차

- [SKPAdBenefit WEB SDK 연동 가이드](#skpadbenefit-web-sdk-연동-가이드)
- [설치](#설치)
- [SDK 초기화](#sdk-초기화)
  - [SKPAdBenefit 초기화](#skpadbenefit-초기화)
- [광고 요청 및 처리](#광고-요청-및-처리)
  - [Step 1 광고 지면 결정](#step-1-광고-지면-결정)
  - [Step 2 Benefit 로그인 상태 확인](#step-2-Benefit-로그인-상태-확인)
  - [Step 3 YOUR_UNIT_ID 로 광고 요청](#step-3-your_unit_id-로-광고-요청)
  - [Step 4 받아온 광고를 사용하기 위한 등록](#step-4-받아온-광고를-사용하기-위한-등록)
  - [Step 5 광고 레이아웃 구성하기](#step-5-광고-레이아웃-구성하기)
  - [CTA View 주의 사항](#cta-view-주의-사항)
- [심화 기능](#심화-기능)
  - [웹페이지에서 사용자의 프로필 정보 설정](#웹페이지에서-사용자의-프로필-정보-설정)
  - [복수 개의 광고 로드하기](#복수-개의-광고-로드하기)
  - [SDK가 감지한 플랫폼 정보 체크하기](#sdk가-감지한-플랫폼-정보-체크하기)
  - [Placement View에서 광고 관련 코드 제거하기](#placement-view에서-광고-관련-코드-제거하기)
  - [clickable 클래스 이름 변경하기](#clickable-클래스-이름-변경하기)
  - [skp-mediaview 클래스 이름 변경하기](#skp-mediaview-클래스-이름-변경하기)

---


## SKPAdBenefit WEB SDK 연동 가이드
SKPAdBenefit WEB SDK는 광고를 웹페이지에 자연스럽게 녹아들 수 있게 자유롭게 디자인할 수 있고, 원하는 지면에 배치할 수 있으며, 리워드를 지급함으로써 매출을 극대화할 수 있는 웹 광고 SDK입니다.  
본 연동 가이드는 이러한 SKPAdBenefit WEB SDK를 웹페이지에 적용하는 방법에 대해 설명합니다.

### ※ 주의사항
- SKPAdBenefit WEB SDK는 현재 데스크탑/모바일의 일반 브라우저에서는 동작하지 않으며, 안드로이드/아이폰 앱에 포함된 웹뷰를 통해서 웹페이지를 보여주는 경우에 동작합니다. 이때, 안드로이드/아이폰 앱에는 SKPAdBenefit SDK가 적용되어 있어야 합니다.
- 안드로이드의 경우 TLS 1.2가 지원되지 않는 OS 4.4.2 미만의 버전에서는 Web SDK가 작동하지 않으며, 아이폰의 경우 WKWebView만 지원합니다.
- SDK를 앱에 연동하기 전, 아래 사항을 먼저 준비해야 합니다.
  > - 광고가 표시될 웹페이지를 웹뷰를 통해 보여주는 매체사의 안드로이드/아이폰 앱
  > - PlanetAD 매니저를 통해 전달받은 OS 별 APP_ID 및 UNIT_ID
  > - 매체사의 안드로이드/ 아이폰 앱에 웹 SDK 지원을 위한 연동
  > - PlanetAD 서버로부터 포인트 적립 요청을 받을 수 있는 매체사 API 서버
  > - Postback 수신 url 세팅 후 SKPAd 담당 매니저에게 전달

## 설치
웹페이지에 웹 SDK를 삽입해야 합니다.
- 개발: [https://adpf-js.dev.planetad.co.kr/sdk/benefit/skpad-benefit-sdk/1.0.5/skpad-benefit-sdk.js](https://adpf-js.dev.planetad.co.kr/sdk/benefit/skpad-benefit-sdk/1.0.5/skpad-benefit-sdk.js)
- 알파: [https://adpf-js.alp.planetad.co.kr/sdk/benefit/skpad-benefit-sdk/1.0.5/skpad-benefit-sdk.js](https://adpf-js.alp.planetad.co.kr/sdk/benefit/skpad-benefit-sdk/1.0.5/skpad-benefit-sdk.js)
- 상용: [https://adpf-js.prd.planetad.co.kr/sdk/benefit/skpad-benefit-sdk/1.0.5/skpad-benefit-sdk.js](https://adpf-js.prd.planetad.co.kr/sdk/benefit/skpad-benefit-sdk/1.0.5/skpad-benefit-sdk.js)

```javascript
<script src="https://adpf-js.dev.planetad.co.kr/sdk/benefit/skpad-benefit-sdk/1.0.5/skpad-benefit-sdk.js"></script>
```

- SDK 스크립트의 로드가 완료되면 전역 변수로 SKPAdBenefit 객체가 생성됩니다. 이 객체가 생성되었는지 체크하는 것으로, SDK 초기화 코드를 실행할지 여부를 결정할 수 있습니다.

```javascript
(function() {
    if (window.SKPAdBenefit) {
        // SDK 설정
    }
})();
```

## SDK 초기화
초기화 과정에는 OS 별 앱을 구분하기 위해 APP_ID를 설정합니다. 이 코드는 광고 요청 전, 반드시 1회만 호출해야 합니다. 이후 모든 광고 요청 및 동작은 지정된 APP_ID 로 이루어지게 됩니다.  
자바 스크립트는 페이지가 바뀌거나 새롭게 로드되는 경우와 같은, 페이지 전환 시 이전 환경이 유지되지 않습니다. 따라서 광고가 연동된 각각의 모든 웹페이지에서 초기화 코드를 호출해야 합니다.

### SKPAdBenefit 초기화
광고를 요청하기 전, SDK를 초기화하는 코드를 호출해야 합니다. 아래의 예제 코드를 이용하여 플랫폼 별로 다른 APP ID를 적용할 수 있습니다.

- *YOUR_OS_APP_ID* 부분에 위 단계에서 준비한 APP_ID를 삽입

```javascript
function getAppId() {
    const ANDROID_APP_ID = '0123456789';
    const IOS_APP_ID = '9876543210';

    switch(SKPAdBenefit.platform) {
        case 'android':
            return ANDROID_APP_ID;
        case 'ios':
            return IOS_APP_ID;
        default:
            // not supported platform
    }
}

SKPAdBenefit.init({
    appId: getAppId()
});
```
  

## 광고 요청 및 처리
지면은 광고를 보여줄 영역을 이야기합니다. 한 웹페이지에는 여러 개의 지면이 존재할 수 있습니다. 각각의 지면은 UNIT_ID로 구분합니다. 다음은 지면의 위치를 결정하고, 해당 지면에 광고를 보여주는 과정입니다.

### Step 1 광고 지면 결정
먼저 광고를 보여줄 영역에 대한 문서 객체 모델(DOM)을 정합니다.  
예를 들어, 3개의 포스트가 배열된 목록에서 첫 번째와 두 번째 포스트 사이에 광고를 보여주려 하는 경우, 두 번째 li블럭이 광고 지면이 됩니다. JS 코드에서 접근하기 편하도록 placement1을 ID로 부여하고, 앞으로 광고가 보일 이 문서 객체 모델은 광고 지면이라는 의미의 **placementView**라 정의합니다.


```html
<li class="post native-ad" id="placement1" style="display: none">
  <div class="skp-mediaview clickable">
  </div>
  <div class="writer">
    <div class="icon-wrapper clickable">
      <img class="icon">
    </div>
    <div class="info">
      <h5 class="name clickable"></h5>
    </div>
  </div>
  <div class="body">
  </div>
  <div class="button-wrapper">
    <button class="cta clickable"></button>
  </div>
</li>
```
  
### Step 2 Benefit 로그인 상태 확인

- 광고 요청에 앞서, 사용자의 정보(ID, 성별, 나이)등으로 Benefit 서버에 로그인을 해야 합니다. 아래 두 가지 방법으로 가능합니다.
  - AOS SDK 또는 iOS SDK에서 SKPAdBenefit에 UserProfile을 먼저 설정한 뒤, 웹페이지를 열면 자바스크립트에서 정보를 가져와 Benefit 서버에 로그인합니다.
  - 웹에서 로그인을 구현하는 경우, SKPAdBenefit 객체에 직접 UserProfile을 설정합니다. (해당 가이드 '심화기능' - '웹페이지에서 사용자의 프로필 정보 설정' 참고)
- 다음의 API를 사용할 경우, 로그인이 완료되었을 때 특정 동작(예: 광고 요청)을 실행합니다.

```javascript
SKPAdBenefit.ensureAuthenticated
    .then(function() => {
        // Benefit 로그인 완료. 광고 요청 가능
    }).catch(function(e) => {
        // 에러 발생. 광고 요청 불가능
    });
```
  
### Step 3 YOUR_UNIT_ID 로 광고 요청
- 준비 사항의 UNIT_ID를 loadAd()에 전달하여 광고를 요청합니다. 이 때, 지원하는 플랫폼에 맞는 UNIT_ID를 설정해야 합니다.
  - *YOUR_OS_UNIT_ID* 부분에 준비한 UNIT_ID를 삽입
  - 광고를 요청하는 플랫폼에 대해 UNIT_ID를 설정하지 않은 경우, 광고를 요청하면 INVALID_PARAMETERS: Check your UNIT ID 라는 에러 메시지가 나오며 광고를 로드하지 않습니다.
    - unitId.android 값이 없으면 안드로이드 웹뷰에서 해당 웹페이지를 열어도 광고를 표시하지 않습니다.
    - unitId.ios 값이 없으면 iOS 웹뷰에서 해당 웹페이지를 열어도 광고를 표시하지 않습니다.
- 성공 시에는 1개의 NativeAd 객체를 얻을 수 있고, 실패 시에는 에러의 원인에 대한 메시지를 포함한 에러를 얻을 수 있습니다.

```javascript
SKPAdBenefit.ensureAuthenticated
.then(() => {
    return SKPAdBenefit.loadAd({
        unitId: {
            android: 'YOUR_ANDROID_UNIT_ID', // 안드로이드 앱에서는 광고를 요청하지 않는 경우 생략 가능
            ios: 'YOUR_IOS_UNIT_ID', // 아이폰 앱에서는 광고를 요청하지 않는 경우 생략 가능
        }
    });
}).then((nativeAd) => {
    console.log('An ad is loaded!');
    populateAd(nativeAd); // 광고 표시
}).catch((e) => {
    if (e.message === 'EMPTY_RESPONSE') {
        console.log('NO FILL');
    } else {
        console.error('An error is detected while loading ad: ' + e.message);
    }
    hideAd();
});
```
  
### Step 4 받아온 광고를 사용하기 위한 등록
loadAd()를 통해 얻은 NativeAd 객체를 사용하는 경우, SDK에 NativeAd 객체를 관리하는데 필요한 추가 정보를 등록해야 합니다.

```javascript
SKPAdBenefit.registerNativeAd(nativeAd, placementView, adListener);
```

- nativeAd: 앞으로 사용하는 NativeAd 객체
- placementView: 광고를 표시할 영역의 [문서 객체 모델(DOM)](https://developer.mozilla.org/ko/docs/Web/API/Document_Object_Model/%EC%86%8C%EA%B0%9C)
- adListener: 광고의 여러 상태에 맞게 정의된 콜백들의 객체

위 API를 사용하기 위한 파라미터 등은 아래의 코드에서 확인할 수 있습니다.
```javascript
function populateAd(nativeAd) {
  const adListener = {
    onImpressed: function(placementView, nativeAd) {
      console.log('ON IMPRESSED: The ad is impressed.');
    },
    onClicked: function(placementView, nativeAd) {
      console.log('ON CLICKED: The ad is clicked.');
    },
    onRewardRequested: function(placementView, nativeAd) {
      console.log('ON REWARD REQUESTED: Reward is requested.');
    },
    onRewarded: function(placementView, nativeAd, result) {
      console.log('ON REWARDED: The result of Reward: ' + result);
    },
    onParticipated: function(placementView, nativeAd) {
      console.log('ON PARTICIPATED: The ad is set to particiated.');
      updateCtaView(element.getElementsByClassName('cta')[0], nativeAd);  // 아래에서 설명
    },
    onVideoError: function(placementView, nativeAd, errorCode, errorMessage) {
      console.error('ON VIDEO ERROR: An error is detected: ' + errorCode + 'n' + errorMessage);
    },
  };

  const placementView = document.getElementById('placement1');
  SKPAdBenefit.registerNativeAd(nativeAd, placementView, adListener);
  renderAd(placementView, nativeAd);  // 광고 구성요소를 DOM에 추가하여 화면에 표시
}
```
  
PlacementDOM에 대한 추가 설명
- PlacementView의 자식 중에 clickable 클래스를 가진 모든 문서 객체 모델은 클릭 시 랜딩 페이지를 열도록 설정됩니다.
- PlacementView의 자식 중에 skp-mediaview 클래스를 가진 첫 번째 문서 객체 모델은 이미지와 비디오 플레이어를 표시하는 영역인 MediaView로 설정됩니다.
- registerNativeAd()에 넘긴 PlacementView가 이미 광고를 표시하고 있었던 경우에는 이전의 설정을 모두 초기화하고 새로운 광고에 맞도록 설정됩니다.


### Step 5 광고 레이아웃 구성하기
광고 레이아웃을 구성하는 정보와 UI에 대해서는 아래의 표를 참고하세요.

|구성 요소|설명|
|---|---|
|MediaView 필수|• 광고 소재의 종류에 따라 이미지나 동영상 플레이어가 삽입되는 영역입니다.<br />• 사이즈: 1200 x 627 (px) (광고 소재의 종횡비는 방드시 유지해야 합니다.)<br />• 여백을 추가할 수 있습니다. 자세한 내용은 버즈빌 매니저에게 문의하세요.<br />• SDK는 PlacementView의 자식 중에서 skp-mediaview 클래스를 가진 첫 번째 문서 객체 모델을 찾아 MediaView로 설정합니다.<br />• MediaView에 설정한 아래의 CSS 속성은, 편의를 위해 이미지나 비디오 플레이어가 자동으로 상속 받아 적용합니다.<br />&emsp;&emsp;• border-radius<br />&emsp;&emsp;• height<br />&emsp;&emsp;• max-width<br />&emsp;&emsp;• width|
|title 필수|광고의 제목입니다. 최대 10자까지 권장하며, 필요에 따라 글자 수에 상관 없이 일정 부분은 생략 부호도 대체할 수 있습니다.|
|description 필수|광고에 대한 상세 설명입니다. 최대 40자까지 권장하며, 필요에 따라 글자 수에 상관 없이 일정 부분은 생략 부호도 대체할 수 있습니다.|
|CTA view 필수|광고의 액션 시 지급되는 포인트와 액션을 유도하는 문구입니다. 최대 7자까지 권장하며, 필요에 따라 글자 수에 상관 없이 일정 부분은 생략 부호도 대체할 수 있습니다.|
|iconUrl 권장|• 광고주 아이콘 이미지입니다. 아이콘의 종횡비는 반드시 유지해야 합니다.<br />• 사이즈: 80 x 80 (px)|

광고 객체(NativeAd)는 native-ad를 클래스로 지정한 태그의 하위에 아래 구조에 맞게 구성해야 합니다.
다음은 PlacementView DOM의 예시입니다.


```html
<li class="post native-ad" id="placement1" style="display: none">
  <div class="skp-mediaview clickable">
  </div>
  <div class="info">
    <div class="icon-wrapper clickable">
      <img class="icon">
    </div>
    <h5 class="title clickable"></h5>
  </div>
  <div class="description">
  </div>
  <div class="button-wrapper">
    <button class="cta clickable"></button>
  </div>
</li>
```

다음은 광고를 화면에 표시하거나 숨기는 JavaScript 코드 예시입니다.

```javascript
function renderAd(placementView, nativeAd) {
  placementView.style.display = '';
  placementView.getElementsByClassName('icon')[0].setAttribute('src', nativeAd.iconUrl);
  placementView.getElementsByClassName('title')[0].innerHTML = nativeAd.title;
  placementView.getElementsByClassName('description')[0].innerHTML = nativeAd.description;
  updateCtaView(placementView.getElementsByClassName('cta')[0], nativeAd);
}

function hideAd() {
  const placementView = document.getElementById('placement1');
  placementView.style.display = 'none';
}
```
  

### CTA View 주의 사항

CTA View는 참여를 유도하기 위한 버튼입니다. 현재 리워드 상태와 참여 유도 문구를 표시할 수 있습니다. 여러 조건에 따라 디자인을 변경할 수 있습니다.  
또한 디자인 커스터마이징은 SDK에서 제공하지 않으므로 직접 구현해야 합니다. 디자인을 자체 구현할 수 있는 경우는 다음과 같습니다.

1. 광고 자체에 리워드가 없는 경우
  1.1 적립을 받았으나 재적립 주기가 돌아오지 않은 상태로 광고가 노출된 경우 (리워드가 부여되지 않은 경우)
  1.2   원래 리워드가 없는 광고인 경우
2. 현재 지급 가능한 리워드가 있는 경우
3. 사용자가 광고에 참여하여 참여 완료 상태인 경우

- 1번, 2번의 경우, 광고를 처음 화면에 표시할 때 nativeAd.reward > 0인지 확인하여 표시합니다.
- 3번의 경우 onParticipated 이벤트를 받았을 때 nativeAd.particiated가 참인지 여부를 체크하여 CtaView를 업데이트 해야 합니다. (아래 샘플 코드의 updateCtaView라는 함수의 구현을 참고) 이 함수는 처음 광고를 보여줄 때, 그리고 onParticipated 이벤트를 받았을 때 실행됩니다.

```javascript
function updateCtaView(ctaView, nativeAd) {
    var ctaTextHeader = '';
    if (nativeAd.reward) {
        if (nativeAd.participated) {
            // CtaView의 디자인을 '이미 참여하여 리워드 지급 불가능 상태'로 표시
        } else {
            // CtaView의 디자인을 '참여 시 리워드 지급 가능 상태'로 표시
            ctaTextHeader = '+' + nativeAd.reward + ' ';
        }
    } else {
        // CtaView의 디자인을 '지급 가능한 리워드 없음' 상태로 표시
    }

    ctaView.innerText = ctaTextHeader + nativeAd.callToAction;
}
```
  

## 심화 기능

### 웹페이지에서 사용자의 프로필 정보 설정

다음은 유저의 로그인 정보를 웹페이지에서 알지만 앱에서 알 수 없는 경우, 웹페이지에서 사용자의 프로필 정보를 설정하는 방법입니다.  
유저가 매체사 앱에 로그인한 시점에 아래와 같이 UserProfile 을 세팅합니다. 단, Web SDK가 초기화된 후에 setUserProfile이 호출되어야 합니다.  
User ID와 타게팅 정보 (성별, 연령)는 원활한 서비스 운영을 위해 제공해야 할 필수 항목입니다. 해당 값을 입력하는 setUserProfile 함수를 호출하지 않으면 광고가 제공되지 않습니다.

- **userId**: 매체사 서비스 유저를 unique하게 구분할 수 있는 식별값. 유저를 중복 없이 구별할 수 있으면 매체사 서비스의 사용자 계정이 아니어도 가능합니다.

동일 유저에 대해 복수개의 User ID를 연동하거나 혹은 User ID 값이 변경될 가능성이 있을 경우, 버즈빌 담당 운영 매니저와 논의해야 합니다.

- 예시: 매체사 앱 삭제 후 재설치 시 User ID 값이 변경되는 경우
- **gender**
  - SKPAdBenefit.Gender.MALE: 남성
  - SKPAdBenefit.Gender.FEMALE: 여성
- **birthYear**: 출생년도

```javascript
SKPAdBenefit.init(config); // setUserProfile보다 먼저 호출되어야 함

SKPAdBenefit.setUserProfile({
    userId: 'Your_Service_User_ID',
    gender: SKPAdBenefit.Gender.FEMALE,
    birthYear: 1980
});
```
  

### 복수 개의 광고 로드하기

loadAds method를 사용하여 여러 개의 광고를 로드할 수 있습니다. 성공 시에는 NativeAd 객체 여러 개가 담긴 배열을 돌려주고 객체의 수는 count 값보다 작을 수 있습니다. 실패 시에는 loadAd와 동일한 에러를 돌려줍니다.

```javascript
SKPAdBenefit.ensureAuthenticated
.then(() => {
    return SKPAdBenefit.loadAds({
        unitId: {
            android: 'YOUR_UNIT_ID_ANDROID', // 안드로이드 앱에서는 광고를 요청하지 않는 경우 생략 가능
            ios: 'YOUR_UNIT_ID_IOS', // 아이폰 앱에서는 광고를 요청하지 않는 경우 생략 가능
        },
        count: 3
    });
}).then((nativeAds) => {
    console.log(nativeAds.length + ' ads are loaded!');
}).catch((e) => {
    if (e.message === 'EMPTY_RESPONSE') {
        console.log('NO FILL');
    } else {
        console.error('An error is detected while loading ad: ' + e.message);
    }
});
```
  

### SDK가 감지한 플랫폼 정보 체크하기

웹 SDK는 자동으로 현재 실행되고 있는 환경이 안드로이드 또는 아이폰 웹뷰를 통한 것인지, 아니면 순수 웹 브라우저를 통해 접속한 것인지 체크합니다. 이 정보를 얻고 싶은 경우 다음과 같이 사용할 수 있습니다.

```javascript
switch(SKPAdBenefit.platform) {
    case SKPAdBenefit.Platform.android:
        // 안드로이드 관련 설정
        break;
    case SKPAdBenefit.Platform.ios:
        // 아이폰 관련 설정
        break;
    case SKPAdBenefit.Platform.web:
        // 웹 관련 설정
        break;
    default:
        break;
}
```
  
SKPAdBenefit SDK가 연동된 안드로이드/아이폰 앱의 웹뷰로 페이지를 연 경우에만 해당 플랫폼으로 인식합니다. 안드로이드나 아이폰에서 SDK를 연동하지 않은 경우, 웹뷰를 통해서 페이지를 열어도 플랫폼으로 인식하지 않습니다. 

### Placement View에서 광고 관련 코드 제거하기

registerNativeAd(nativeAd, placementView, adListener)의 두번째 인자로 전달한 Placement View의 SDK는 다음과 같은 추가적인 작업을 합니다.

1.  광고가 화면에 보이는지 여부를 자동으로 체크합니다.
2.  clickable을 클래스로 등록한 자식 문서 객체 모델이 있는 경우, 클릭하여 랜딩 페이지를 열 수 있도록 설정합니다.

Placement View로 전달한 문서 객체 모델에서 더 이상 광고를 표시하고 싶지 않은 경우, 위의 추가된 작업을 해제해야 합니다. 해제를 위해 다음의 코드를 호출합니다.

```javascript
SKPAdBenefit.unregisterNativeAd(placementView);
```

또는, NativeAd 객체를 전달하여 이 광고를 표시하는 모든 문서 객체 모델을 찾아서 해제합니다.

```javascript
SKPAdBenefit.unregisterNativeAd(nativeAd);
```

### clickable 클래스 이름 변경하기

SDK는 기본적으로 clickable 이라는 클래스 이름을 가진 요소가 있으며, 이를 클릭할 경우 랜딩 페이지를 열 수 있게 설정합니다. 이 클래스 이름을 변경하고 싶을 경우, 다음과 같이 SDK 초기화 시 사용하는 config에 clickableClass 항목을 추가해야 합니다.

```javascript
SKPAdBenefit.init({
    appId: 'YOUR_APP_ID',
    clickableClass: 'YOUR_CLICKABLE_CLASS_NAME'
});
```
  
위와 같이 설정한 뒤, clickable로 설정하고 싶은 문서 객체 모델(DOM)의 class에 YOUR_CLICKABLE_CLASS_NAME을 추가하면 SDK는 해당 DOM을 자동으로 찾아 랜딩 페이지를 열 수 있게 설정합니다.  
클래스 이름 커스터마이징 설정은 해당 웹페이지에 전역적으로 설정되며, 한 웹페이지 내의 광고 지면에 각각 다르게 설정할 수 없습니다.

### skp-mediaview 클래스 이름 변경하기

SDK는 기본적으로 skp-mediaview 이라는 클래스 이름을 가진 요소를 MediaView로 설정합니다. 이 클래스 이름을 변경하고 싶을 경우, 다음과 같이 SDK 초기화시 사용하는 config에 mediaViewClass 항목을 추가해야 합니다.

```javascript
SKPAdBenefit.init({
    appId: 'YOUR_APP_ID',
    mediaViewClass: 'YOUR_MEDIAVIEW_CLASS_NAME'
});
```

  
위와 같이 설정한 뒤, MediaView로 설정하고 싶은 문서 객체 모델(DOM)의 class에 YOUR_MEDIAVIEW_CLASS_NAME을 추가하면 SDK는 해당 DOM을 자동으로 찾아 (광고에 따라) 이미지 또는 비디오 플레이어를 삽입합니다.  
클래스 이름 커스터마이징 설정은 해당 웹페이지에 전역적으로 설정되며, 한 웹페이지 내의 광고 지면에 각각 다르게 설정할 수 없습니다.





