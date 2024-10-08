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

## Nginx 무중단 베포

### 로드밸런싱이란?

> 로드밸런싱은 아래의 그림처럼 클라이언트가 접속할 때 응답을 주는 서버를 분산해주는 기술이다.
> 

!https://velog.velcdn.com/images/twoweekhee/post/0a6d72fc-c42f-429b-bfaf-9cdfaaf77d29/image.png

### 로드밸런서란?

> 로드밸런서(Load Balancer)는 클라이언트와 서버 그룹 사이에 위치해 서버에 가해지는 트래픽을 여러 대의 서버에 고르게 분배하여 특정 서버의 부하를 덜어주는 역할을 하는 것.
> 

그중 우리는 NginX라는 WS 를 이용해서 로드밸런싱을 해보도록 하겠다.

**로컬에서 진행하기 위해서는 여러 방법을 쓸 수 있겠지만 가장 간단하게 포트 번호만 따로 설정해서 로드밸런싱을 해보겠다.** *~~여러 서버가 있는 환경에서 하고 싶다면 클라우드나 물리서버를 이용하면 된다.~~* 우리는 한대의 서버를 이용하기 위해서 포트번호만 바꿔 다른 서버로 가는 것과 같은 테스트를 진행해 볼것이다.

일단 NginX를 로컬에 깔아야 한다.맥 기준으로 설명하자면 다음과 같다.

### NginX 로컬에 설치

!https://velog.velcdn.com/images/twoweekhee/post/baf1a9ac-8246-461e-a4e0-e6251cb2e04e/image.png

Homebrew를 사용하여 Nginx를 설치합니다:

```bash
brew install nginx
```

- **nginx 설치 확인**: 먼저 nginx가 제대로 설치되었는지 확인합니다.
    
    ```
    brew list nginx
    ```
    
- **nginx 실행**:
    
    ```
    sudo nginx
    ```
    
- **nginx 중지**: nginx를 중지하려면 다음 명령어를 사용합니다.
    
    ```
    sudo nginx -s stop
    ```
    
- **nginx 재시작**: 설정을 변경한 후 nginx를 재시작하려면 다음 명령어를 사용합니다.
    
    ```
    sudo nginx -s reload
    ```
    

### Nginx.conf 파일 바꾸기

(복사하여 진행하는 것이 더 나은 선택)

```
cd /usr/local/etc/nginx/nginx.conf
```

```
vi nginx.conf
```

#다른 conf 파일로 nginX 실행하기

```
 sudo nginx -c /usr/local/etc/nginx/my_nginx.conf
```

vi편집기를 이용하여 문서 편집 해주기

```
# /usr/local/etc/nginx/my_nginx.conf

events {
    worker_connections 1024;
}

http {
iq00

    # upstream 블록 정의 (origin이라는 이름은 그저 마음대로 지정)
    upstream origin {
        server localhost:8080; -> 이용할 아무 포트
        server localhost:8090; -> 이용할 아무 포트
    }

	#최초 리슨 포트 지정
    server {
        listen 80;
        server_name localhost;

        #charset koi8-r;

        #access_log logs/host.access.log main;

	#여기서 위에 지정한 이름과 동일하게 지정해야 함
        location / {
            proxy_pass http://origin;
            root html;
            index index.html index.htm;
        }
    }
}

```

!https://velog.velcdn.com/images/twoweekhee/post/bb20adab-405d-4412-904c-5d519d25f482/image.png

위와 같은 그림처럼 되는 것이다!!

그렇다면 이걸 테스트할 수 있는 방법을 설명하겠다!!

### 테스트 하는 법(인텔리제이)

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController {

       @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    @GetMapping
    public String count() {
        int port = webServerAppCtxt.getWebServer().getPort();
        return "FirstPage, running on port: " + port;
    }
}
```

위와 같은 컨트롤러 하나 생성

그런 다음인텔리 제이 우측 상단 1시방향을 클릭

!https://velog.velcdn.com/images/twoweekhee/post/0b79a12c-9a0a-4b8c-9220-79f1a81874b4/image.png

edit Configuration 클릭

옆에 복사 버튼 누르면 똑같은 어플리케이션이 하나 더 생긴다.

!https://velog.velcdn.com/images/twoweekhee/post/7b062d54-f62f-4a12-8f4a-b79cb6a4a15c/image.png

그다음 modify option을 누른다.

1. 첫번째 방법
- Vm option 선택
- Dserver.port=포트번호 (원하는 포트번호 입력)
1. 
2. 두번째 방법
- environment variable 선택
- server.port=포트번호 (원하는 포트번호 입력)

그런 다음 ok

두개를 동시에 실행시켜 본다음

!https://velog.velcdn.com/images/twoweekhee/post/2b3433b2-32c7-4bf6-8fc8-5b788ce9d014/image.png

!https://velog.velcdn.com/images/twoweekhee/post/2d1f6a5a-12c4-4f34-a5e2-9e2035faa8fc/image.png

!https://velog.velcdn.com/images/twoweekhee/post/1964d7d7-282b-43fb-8564-b471406478a2/image.png

잘 실행 된다.

localhost에 접속해보면 계속 새로고침 했을 때 포트 번호가 다르게

화면에 띄워지는 것을 알 수 있다.

!https://velog.velcdn.com/images/twoweekhee/post/9aabb8e1-3e1b-4f88-9a00-1b86848e39e5/image.png

!https://velog.velcdn.com/images/twoweekhee/post/e225977f-94d5-465c-97af-62150266fd24/image.png

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



