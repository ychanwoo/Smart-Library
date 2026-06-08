# Smart Library

Java 기반 스마트 도서관 좌석·도서 대여 관리 시뮬레이터입니다.

이 프로젝트는 Java 프로그래밍 기말 과제 요구사항인 상속, 추상 클래스, 인터페이스, 컬렉션, 예외 처리, 멀티스레드, `synchronized` 동기화를 콘솔 기반 시뮬레이터 안에서 종합적으로 보여주기 위해 작성되었습니다.

## 주요 기능

- 사용자 등록 및 조회
- 사용자 유형별 대여 한도 관리
  - 학생: 3권
  - 교수: 10권
  - 일반 회원: 2권
- 도서 등록, 조회, 검색
- 도서 대여 및 반납
- 좌석 조회, 예약, 예약 취소
- 멀티스레드 기반 동시 도서 대여 테스트
- 멀티스레드 기반 동시 좌석 예약 테스트
- `synchronized`를 이용한 공유 자원 충돌 방지
- 도서관 현황판
- 사용자별 이용 현황 조회

## 과제 요구사항 충족표

| 요구사항 | 구현 위치 |
| --- | --- |
| 사용자 클래스 상속 구조 | `User`, `Student`, `Professor`, `GeneralUser` |
| 추상 클래스 | `abstract class User` |
| 도서 대여 인터페이스 | `Borrowable` |
| 좌석 예약 인터페이스 | `Reservable` |
| 사용자 등록 및 조회 | `UserService` |
| 도서 등록, 조회, 검색, 대여, 반납 | `LibraryService`, `Book` |
| 좌석 조회, 예약, 취소 | `SeatService`, `Seat` |
| 멀티스레드 도서 대여 테스트 | `BorrowTask`, 메뉴 11번 |
| 멀티스레드 좌석 예약 테스트 | `SeatReservationTask`, 메뉴 12번 |
| 동시 접근 충돌 방지 | `Book`, `Seat`의 `synchronized` 메서드 |
| 추가 완성도 기능 | 메뉴 9번 도서관 현황판, 메뉴 10번 사용자별 이용 현황 |

## 프로젝트 구조

```text
src/library
├── Main.java
├── exception
│   └── LibraryException.java
├── interfaces
│   ├── Borrowable.java
│   └── Reservable.java
├── model
│   ├── Book.java
│   ├── GeneralUser.java
│   ├── Professor.java
│   ├── Seat.java
│   ├── Student.java
│   └── User.java
├── service
│   ├── LibraryService.java
│   ├── SeatService.java
│   └── UserService.java
└── task
    ├── BorrowTask.java
    └── SeatReservationTask.java
```

## VSCode 터미널 실행 방법

프로젝트 루트에서 다음 명령어를 실행합니다.

```bash
javac -d out $(find src -name "*.java")
java -cp out library.Main
```

## Eclipse 실행 방법

1. Eclipse에서 `File > Import > Existing Projects into Workspace`를 선택합니다.
2. 프로젝트 폴더 `/Users/ychanwoo/Projects/smart-library`를 선택합니다.
3. `smart-library` 프로젝트가 감지되면 `Finish`를 누릅니다.
4. `src/library/Main.java`를 열고 `Run As > Java Application`으로 실행합니다.

## 콘솔 메뉴

```text
1. 사용자 목록 조회
2. 도서 목록 조회
3. 도서 검색
4. 도서 대여
5. 도서 반납
6. 좌석 목록 조회
7. 좌석 예약
8. 좌석 예약 취소
9. 도서관 현황판 보기
10. 사용자별 이용 현황 보기
11. 자동 시연: 여러 명이 같은 책을 동시에 대여 시도
12. 자동 시연: 여러 명이 같은 좌석을 동시에 예약 시도
0. 종료
```

11번과 12번은 일반적인 입력 기능이 아니라, 과제 요구사항인 멀티스레드와 동기화 처리를 보여주기 위한 자동 시연 메뉴입니다. 여러 테스트 사용자가 같은 자원을 동시에 요청하고, 프로그램은 성공 1명과 실패 4명을 표로 정리해 보여줍니다.

9번은 전체 도서 수, 대여 가능 도서 수, 예약 가능 좌석 수를 한눈에 보여주는 관리자용 현황판입니다. 10번은 특정 사용자의 대여 도서와 예약 좌석을 조회하는 마이페이지 형태의 기능입니다.

## 보고서

결과보고서 초안은 `docs/report.md`에 있습니다.
