# DB Replication & Kafka Alert Service

## **⚙️ 프로젝트 개발 환경**

1. 통합개발환경(IDE) : InteliJ
2. JDK 버전 : JDK 17
3. 스프링 버전 : 5.2.7
4. 데이터 베이스 : Mysql, redis
5. 빌드 툴 : Gravy

## **⚒️ 프로젝트 기술 스택**

- 프론트엔드
    
    HTML, CSS, JS, React
    
- 백엔드
    
    Java, Spring Boot Framework, Kafka, SSE 
    
- 데이터베이스
    
    Mysql, redis, JPA

## **📜 구현 기능**

- Db replication
- Oauth2
- Oauth2 ; authorization, authentication
- jwt
- 대기열 구현 with 레디스
- 카프카 이용한 알림
- SSE 푸쉬
- 스웨거

<br>

![스크린샷 2024-08-14 오후 3 30 03](https://github.com/user-attachments/assets/cf14c037-65bd-4a36-86ab-f472dd16f887)

<br>

## **✨ DB Replication **
- source와 replica로 나누어 DB 구성 (윤리적 문제로 master, slave에서 변경됨)
- transaction을 통해서 readOnly 값을 할당하여 db 분배를 할 수도 있지만, annotation을 사용하여 db 지정을 해줌.
  ### 어노테이션을 통한 db 지정을 했을 때의 장점
  - select도 몰리면 많은 부하를 일으킬 수 있으며
  - 현업에서는 replica DB가 하나가 아닐 확률이 큼.
  - 이때 매서드 별로 db를 지정하게 되면 db 부하 컨트롤이 용이하다.

<br>

### velog

[로컬에서 NginX - RoadBalancing 테스트 해보기 & 인텔리제이 & 스트링부트](https://velog.io/@twoweekhee/감자도-이해하는-로컬에서-NginX-RoadBalancing-테스트-해보기)

[[Docker 이용 Mysql (8이상)] 💾 DB Replication 구축 <Source, Replica>](https://velog.io/@twoweekhee/Docker-이용-Mysql-8이상-DB-Replication-구축-Source-Replica)

[👾DB replication 오류 잡기](https://velog.io/@twoweekhee/DB-replication-%EC%98%A4%EB%A5%98-%EC%9E%A1%EA%B8%B0)

<br>

### 프론트 페이지 + kafka consumer application
프론트는 잘 못해서 구조가 깨끗하지 않을 수 있습니다.
- https://github.com/twoweekhee/kafkaTest
- https://github.com/twoweekhee/kafkaTestConsumerFe
- https://github.com/twoweekhee/ReplicationTestFe



