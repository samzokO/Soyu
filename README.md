# 소유

## 1. 소유 소개

- **프로젝트 진행 기간 : 24.01.08(월) ~ 24.02.16(금)**
- **도메인 : 웹 IoT**
- **주제 : 비대면 중고거래 플랫폼 <소유>**
- **프로젝트 인원 : 6인 (엄진식, 손주현, 최준영, 이재신, 이호진, 이성현)**

![member](https://github.com/jaesin463/PlayGround/assets/117919180/3f56b849-f348-4392-af9a-10c6f070c81a)

## 2. 개요

### 물건을 확인하고 거래하고 싶은 당신을 위한 서비스<소유>

> 중고거래의 경우 보통 대면거래를 통해 물건을 직접 확인하고 살 수 있는 장점이 있지만, 정확한 시간과 장소에 대한 약속이 필요하며 범죄 노출과 같은 이유로 비대면 중고거래를 선호하는 사람이 늘고있습니다.

> 하지만 문고리 거래와 같은 비대면 방식 거래 또한 주소 노출의 우려가 있고 물건의 분실 우려가 있기 때문에, **중고거래에 특화된 비대면 중고거래 시스템**에 대해 기획하게 됐습니다.


## 3. 기술 스택

### Back-End / DB / Infra
- 스프링부트 / JPA / MySql / JWT / FCM
- Amazone EC2 / docker / Jenkins  / NGINX / Stomp

### Front-End
- React / Framer Motion / SockJS / Style-Component / React-Router-Dom

### IoT
- Arduino

![stack](https://github.com/jaesin463/PlayGround/assets/117919180/b86eb2b8-f456-4ea1-ba0c-c58773f35a1a)

## 4. 기능 소개

### ① 로그인 및 회원가입

![Untitled](https://github.com/jaesin463/PlayGround/assets/117919180/f4d124f3-c8bd-42ca-8102-cdb5cd902c5e)

- 유저 기능은 **네이버 소셜 회원가입**을 구현하였습니다.
- 회원 가입시 네이버에서 회원의 정보를 가져와 저장합니다.
- **JWT Refresh Token은 DB에 저장**합니다.

### ② 채팅

![Untitled](https://github.com/jaesin463/PlayGround/assets/117919180/4266dd0a-e902-45ef-9b70-60cbad257b2d)

- 물품 상세 정보에서 **채팅방을 생성**하여 판매자와 채팅을 나눌 수 있습니다.
- **거래 약속하기 버튼**을 통해서 판매자와 비대면 거래를 할 수 있게 됩니다.

### ③ 거래 약속

![Untitled](https://github.com/jaesin463/PlayGround/assets/117919180/e136e1bd-b84c-4ac2-8489-49ecdb683ca7)

- 거래 약속하기 버튼을 누른 후 보관하고 싶은 스테이션을 선택합니다.
- 스테이션 선택 후 **보관함을 선택하면 예약**이 완료됩니다.

### ④ 보관함 코드 확인

![Untitled](https://github.com/jaesin463/PlayGround/assets/117919180/705ff8e1-75b7-458a-b857-eec8da2ee8fc)

- 오프라인 판매코드 확인하기를 통해 보관함에 입력할 **판매코드**를 확인할 수 있습니다.
- 판매자가 키오스크에 판매코드를 입력하면 구매자는 이후에 **구매코드**를 구매내역에서 확인할 수 있습니다.

### ⑤ 키오스크 화면

![Untitled](https://github.com/jaesin463/PlayGround/assets/117919180/753c6201-cf55-4c28-a69b-ed91b44cf613)

- **판매**
    - 판매자가 판매코드를 입력합니다.
    - 판매코드를 입력하면 예약한 보관함이 **20초**간 열리게 됩니다.
        
        ![Untitled](https://github.com/jaesin463/PlayGround/assets/117919180/37620ae6-ec56-41b2-9f45-745ac52d0b7c)
        
    - 판매자는 물품을 보관함에 넣습니다.

- **구매**
    - 구매자가 구매코드를 입력합니다.
    - 보관함의 **유리창이 투명상태**가 되며 구매자는 물품의 상태를 눈으로 확인할 수 있습니다.
    
    ![Untitled](https://github.com/jaesin463/PlayGround/assets/117919180/7da521db-24cc-49d5-8de7-f49431a0ffb4)
    
    - 구매 버튼을 누르면 소유 서비스의 회사 계좌가 나타나게 됩니다.
    
    ![Untitled](https://github.com/jaesin463/PlayGround/assets/117919180/fc8469b7-cfab-48a9-9bf0-5a890a86c4bb)
    
    - **반려 버튼**을 누르면 아이템의 **거래 예약이 파기**됩니다.
    

## 5. DB 설계
![soyuDB](https://github.com/jaesin463/PlayGround/assets/117919180/849fac9d-e342-479d-8fe9-0997a2253bd9)

## 6. 디자인 시스템 구축
![디자인시스템](https://github.com/jaesin463/PlayGround/assets/117919180/eb4df413-6001-4543-9c84-2b530a9cb980)

## 7. 폴더 구조
- [Front-Dir](산출물/front_dir_tree)
- [Back-Dir](산출물/back_dir_tree)
