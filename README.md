# SSAFY 공통 프로젝트 B311

프로젝트 기간 : 24.01.08 - 24.02.16, 6 weeks

주제 : IoT 기반 비대면 중고거래 및 판매대 시스템

## 커밋 컨벤션

커밋 컨벤션은 프론트, 백엔드 모두 통일함을 기본으로 합니다.

지라 이슈와 커밋을 연동해 사용할 예정입니다.

### 커밋 메시지 구조

> **#\<지라 이슈번호> 타입: \<파일명> 간단 핵심 설명**
>
> 변경 사항에 대한 자세한 설명

### 타입의 종류

- **build**: 시스템 또는 외부 종속성에 영향을 미치는 변경사항 (npm, gulp, yarn 레벨)
- **ci**: ci구성파일 및 스크립트 변경
- **chore**: 패키지 매니저 설정할 경우, 코드 수정 없이 설정을 변경(pom.xml 등)
- **docs**: documentation 변경([README.md](http://readme.md/) 등)
- **design**: UI 디자인 변경 (css 등)
- **feat**: 새로운 기능
- **fix**: 버그 수정
- **perf**: 성능 개선
- **refactor**: 버그를 수정하거나 기능을 추가하지 않는 코드 변경, 리팩토링
- **style**: 코드 의미에 영향을 주지 않는 변경사항 ( white space, formatting, colons )
- **test**: 누락된 테스트 추가 또는 기존 테스트 수정
- **revert**: 작업 되돌리기
- **remove:** 파일 삭제

# 프론트엔드 코드 컨벤션

기본 코드 컨벤션은 아래와 같습니다.

1. ES6 기반 문법 사용하기
2. 상수는 UPPER_SNAKE_CASE, 그 외엔 camelCase 사용하기
3. Boolean 변수 선언 시 is 접두사 붙이기
4. JSX 파일 생성 시 PascalCase 사용하기
5. JSX 확장자 남용하지 않기

더불어, 아래 컨벤션 가이드를 기반으로 코드 컨벤션을 준수할 예정입니다.

글로벌이 아닌 **레포지토리 eslint와 prettier**를 사용해 프론트엔드 팀원 간 동일한 코드 컨벤션을 유지할 계획입니다.

아래 컨벤션 가이드는 모두 Airbnb사의 가이드입니다.

- [Airbnb JS Convention](https://github.com/tipjs/javascript-style-guide)

- [Airbnb React Convention](https://github.com/airbnb/javascript/tree/master/react)

- [Airbnb CSS-in-JS Convention](https://github.com/airbnb/javascript/tree/master/css-in-javascript)

# 백엔드 코드 컨벤션

구글의 자바 컨벤션을 기반으로 코드 컨벤션을 준수할 예정입니다.

- [Google Java Convention](https://github.com/JunHoPark93/google-java-styleguide?tab=readme-ov-file)

### 인코딩(ENCODING)
- 기본 UTF-8

### 선언 ★
- static import에만 와일드 카드(*)를 허용한다.  
  (클래스를 import할 때는 와일드 카드없이 모든 클래스명을 다 쓴다.)
- 클래스/메서드/멤버변수의 제한자는 아래의 순서로 쓴다.  
  (https://docs.oracle.com/javase/specs/jls/se7/html/jls-18.html 참조)
- 어노테이션 선언 후 새 줄을 사용한다. 단, 파라미터가 없는 어노테이션은 같은 줄에 선언할 수 있다.
- 문장이 끝나는 ; 뒤에는 새 줄을 삽입한다.
- 하나의 선언문에는 하나의 변수만 작성한다.
- 배열 선언에 오는 대괄호([])는 타입의 바로 뒤에 붙인다.
- long형의 숫자에는 마지막에 대문자'L'을 붙인다.

## 📝DB 네이밍 컨벤션

**1. 테이블 및 기타 관계는 단수형을 사용한다**

**2. 테이블명 설정시 예약어, 약어는 피한다**

**3. 테이블명은 소문자로 작성한다**
두 개 이상의 단어를 조합해서 쓸 때는 스네이크케이스로 쓴다.
한 단어일때는 소문자로 적는다.


**4. 필드명은 스네이크 케이스 사용한다**
> 테이블 - 몸무게 : weight


**5. 기본키 필드는 `접두어_no` 형식을 사용한다**
예약고유번호 : reservation_device_no


**6. 외래키 필드이름은 참조한 테이블의 기본키 필드명을 사용한다**
후기게시판(post) 에서 이용자(member) 참조
-> 외래키 명 : member_no


## DB 명명 규칙의 중요성

#### ☑️ 이름은 오래간다.

데이터 구조는 일반적으로 어플리케이션 코드보다 훨씬 지속력이 높아 **영향력이 오래간다.**

#### ☑️ 이름은 계약이다.

한번 컬럼이나 테이블 이름을 정해 놓으면 개발 단계에서는 그 이름을 그대로 사용하기 때문에
**만약 컬럼과 테이블의 이름이 변경된다면 의존하고 있던 코드에서도 수정**이 일어나야 한다.

#### ☑️ 개발자 환경의 차이.

이름이 잘 정의된 테이블, 컬럼이 있다면 **개발자 본인과 다른 개발자들도 DB구조를 이해하는데 적은 시간이 소요된다.**


## 프로젝트 사용 도구

실제 프로덕트를 제작할때 사용하는 기술 스택은 곧 업로드 될 예정입니다.

- 형상 관리 : Git, GitLab
- 일정 관리 : Jira
- 문서 관리 : notion
- 소통 : mattermost
    - Gitlab, Jira 변경 사항을 mattermost에 연동해 사용하고 있습니다.
- 코드 분석 및 품질 관리 : Eslint, SonarCube, Gerrit, Scavenger


# 팀원 역할

![팀원사진](https://github.com/MyungAe/A-Meal-of-Today/assets/78721036/6d337e6a-3a24-4fd4-b45a-b1133e223b64)

- **엄진식** : 팀장, FE
- **이호진** : IoT, FE
- **이재신** : FE
- **이성현** : IoT, BE
- **손주현** : BE 리더
- **최준영** : BE
