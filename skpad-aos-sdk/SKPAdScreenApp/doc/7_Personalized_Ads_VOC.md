## 맞춤형 광고를 위한 가이드 라인과 VOC(문의하기) 사용하기

### 맞춤형 광고를 위한 가이드 라인 추가하기
Planet AD는 개인별 맞춤형 광고를 제공하며, 그에 해당하는 사항을 필수적으로 사용자에게 고지해야합니다.

이러한 맞춤형 광고 고지를 위해 미리 만들어진 웹페이지를 제공하고 있으며, 이 맞춤형 광고 고지 페이지로 진입하기 위한 방법은 아래와 같습니다.

- 광고・컨텐츠・컨텐츠채널 피드백 메뉴를 이용하는 경우는 아래의 문서에 기술되어 잇습니다.
    - [광고・컨텐츠・컨텐츠채널 피드백](./3_advanced.md)

- 커스텀으로 만들어서 사용하는 경우, 아래의 단계를 통해 해당 기능을 사용하실 수 있습니다.
   1. 맞춤형 광고 고지 웹페이지 진입을 위한 유저 진입 Icon/ Tab을 디자인 합니다.
   2. 1번의 Icon/Tab이 클릭될 때 코드에서 `SKPAdScreen.showAdInfoPage()` 호출합니다.



### 유저 VOC (문의하기) 사용하기
종종 리워드 미적립을 이유로 유저가 문의(VOC)를 보내기도 합니다.

이러한 유저 VOC에 대한 접수 및 처리를 자동화 하기 위해 SDK에서는 미리 만들어 놓은 웹 페이지를 제공하고 있습니다. 이 문의하기 페이지는 연동되어 있는 앱을 기준으로 조회하기 때문에, 유닛별로 구현할 필요가 없으며, VOC의 위치를 강제하지 않습니다.

- 광고・컨텐츠・컨텐츠채널 피드백 메뉴를 이용하는 경우는 아래의 문서에 기술되어 잇습니다.
    - [광고・컨텐츠・컨텐츠채널 피드백](./3_advanced.md)


- 커스텀으로 만들어서 사용하는 경우, 아래의 단계를 통해 해당 기능을 사용하실 수 있습니다.
   1. VOC 페이지 로드를 위한 유저 진입 Icon/ Tab을 디자인 합니다.
   2. 1번의 Icon/Tab이 클릭될 때 코드에서 `SKPAdScreen.showInquiryPage()` 호출합니다.
