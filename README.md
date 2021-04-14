# Spring Armeria Example

이 애플리케이션은 인프런 김영한님의 인프런 강의를 바탕으로 만들었습니다.

## OSIV

Open Session In View: 하이버네이트
Open EntityManager In View: JPA
(관례상 OSIV라 한다.)

### OSIV TRUE
JPA는 언제 데이터 베이스 커넥션을 가져올까? 
- 기본적으로 데이터베이스 트랜잭션을 시작할 때 그 때 JPA가 데이터베이스 커넥션을 가져온다.

그렇다면 언제 데이터베이스에 트랜잭션을 반납할까?
- Open Session In View가 커져있으면 Controller 로직이 끝나야지만 트랜잭션을 반환한다.
- 즉, API는 응답을 전달할 때까지 View는 렌더링이 될때까지 영속성 컨텍스트가 유지된다.

치명적인 단점이 있다. 무엇일까?
- 너무 오랫동안 커넥션을 가지고 있기 때문에 실시간 트래픽이 중요한 애플리케이션에서는 커넥션이 모자랄 수 있다.
- 외부 대기 시간만큼 커넥션 리소스를 반환하지 못한다.

### OSIV OFF
OSIV 옵션을 끄면 **@Transational**이 있는 함수에서만 데이터베이스 커넥션을 유지한다.
- 지연 로딩에 대한 트랜잭션 처리를 안에서 처리해야 한다.
- 단순한 방법으로 QueryService를 만든다.

복잡성을 줄이기 위해서 하는 방법은 Command와 Query를 분리하는 것이다.
