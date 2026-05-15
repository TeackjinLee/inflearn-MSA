# SECTION_14. 장애처리와 Microservice 분산 추적

# 129.색션소개
  - CircuitBreaker(회복성패턴)
  - Resilience4j
  - Distributed Tracing
  - Trace ID and Span ID
  - Zipkin server 활용

# 130. CircuitBreaker와 Resilience4J의 사용
## 마이크로서비스 통신 장애 대응: Circuit Breaker & Resilience4j

이 문서는 마이크로서비스 아키텍처(MSA)에서 서비스 간 통신 시 발생할 수 있는 오류를 인지하고, 이를 서킷 브레이커를 통해 해결하는 방법을 정리한 가이드입니다.

---

### 1. 마이크로서비스 간 통신과 장애 발생

#### 1.1 주요 발생 오류
- **Unknown Host Exception**: 호출하려는 서비스의 주소를 찾을 수 없을 때 발생합니다.
- **500 Server Exception / Timeout**: 대상 서비스가 응답하지 않거나 내부 오류가 발생했을 때 호출 측까지 장애가 전파됩니다.

> **<<img width="2000" height="1414" alt="다운로드 (1)" src="https://github.com/user-attachments/assets/0b889f83-bee0-4c53-87a7-6c4094c882ff" />**
> *설명: 유저 서비스에서 오더 서비스를 호출했을 때 발생하는 500 에러와 인텔리제이 콘솔의 Unknown Host Exception 로그 화면*

#### 1.2 장애 전파 (Cascading Failure)
- 특정 서비스(예: Order Service)의 문제가 해당 서비스를 호출하는 다른 서비스(예: User Service)의 장애로 이어지는 현상입니다.
- 사용자는 유저 정보 조회만 원했더라도, 주문 내역 조회 기능의 장애 때문에 전체 요청이 실패하게 됩니다.

---

### 2. 서킷 브레이커 (Circuit Breaker)

#### 2.1 개념
장애가 발생하는 서비스에 대해 반복적인 호출을 하지 않도록 차단하는 '차단기' 역할을 합니다. 장애가 복구될 때까지 요청을 우회시켜 시스템 전체의 가용성을 보장합니다.

> **<img width="2000" height="1414" alt="다운로드 (2)" src="https://github.com/user-attachments/assets/6af85163-7f06-461a-8b9e-c68a25bb76f9" />**
> *설명: Client - Circuit Breaker - Supplier 사이의 데이터 흐름과 장애 시 회피 경로를 보여주는 그림*

#### 2.2 서킷 브레이커의 상태
- **Closed (닫힘)**: 모든 서비스가 정상일 때. 요청이 목적지로 전달됩니다.
- **Open (열림)**: 장애 발생 시. 요청을 목적지로 전달하지 않고 즉시 에러 또는 기본값(Fallback)을 반환합니다.

---

### 3. 기술의 변화: Hystrix에서 Resilience4j로

기존 Spring Cloud에서는 Netflix의 **Hystrix**를 주로 사용했으나, 2019년 이후 유지보수 모드로 전환되면서 최신 버전에서는 **Resilience4j** 사용을 권장합니다.

| 기존 기술 (Netflix) | 최신 대체 기술 (Spring Cloud) |
| :--- | :--- |
| Hystrix | **Resilience4j** |
| Hystrix Dashboard | Micrometer / Prometheus |
| Ribbon | Spring Cloud Loadbalancer |
| Zuul | Spring Cloud Gateway |

> **<img width="2000" height="1414" alt="다운로드 (3)" src="https://github.com/user-attachments/assets/00cd5fa4-5602-4979-9886-395c0b2ddac2" />**
> *설명: 강의 중 소개된 넷플릭스 라이브러리와 이를 대체하는 최신 기술들의 비교 화면*

---

### 4. Resilience4j 구현 및 설정

#### 4.1 의존성 추가 (pom.xml)
`spring-cloud-starter-circuitbreaker-resilience4j` 라이브러리를 추가하여 사용합니다.

#### 4.2 기본 구현 방식 (Fallback)
서비스 호출 시 문제가 생기면 `ArrayList`의 기본 생성자(빈 리스트)를 반환하도록 설정하여, "주문 내역이 없는 것"처럼 보여줌으로써 전체 시스템 에러를 방지합니다.

> **<img width="2000" height="1414" alt="image" src="https://github.com/user-attachments/assets/c5077961-79bd-4b9c-a9f8-60ea85f74e72" />**
> *설명: getOrders() 메소드 호출 시 서킷 브레이커를 적용하고 fallback을 지정하는 Java 코드*

#### 4.3 주요 상세 설정 (Customizing)
`Resilience4jCircuitBreakerFactory`를 통해 세부 파라미터를 조정할 수 있습니다.

- **failureRateThreshold**: 실패 확률 임계치 (기본값 50%). 이 비율을 넘으면 서킷이 Open 됩니다.
- **waitDurationInOpenState**: 서킷이 Open 상태를 유지하는 시간. 이 시간이 지나면 다시 연결을 시도(Half-open)합니다.
- **slidingWindowType**: 통계 기준 (Count 기반 또는 Time 기반).
- **slidingWindowSize**: 통계에 반영할 기록의 수.
- **timeoutDuration**: TimeLimiter 설정. 특정 시간(예: 4초) 동안 응답이 없으면 장애로 간주합니다.

> **<img width="2000" height="1414" alt="다운로드 (5)" src="https://github.com/user-attachments/assets/68e113a2-198a-479c-96df-62b268c2515f" />**
> *설명: 각 설정 옵션들의 의미와 기본값이 정리된 강의 슬라이드*

---

### 5. 결론
마이크로서비스 환경에서 **Resilience4j**를 통한 서킷 브레이커 도입은 필수적입니다. 이를 통해 특정 서비스의 장애가 전체 시스템의 중단으로 이어지는 것을 방지하고, 사용자에게 최소한의 기능(Fallback)을 지속적으로 제공할 수 있습니다.

  <img width="2000" height="1414" alt="다운로드(0)" src="https://github.com/user-attachments/assets/22b6ff5f-1797-4141-ba54-acd815dc4556" />
  <img width="2000" height="1414" alt="다운로드 (1)" src="https://github.com/user-attachments/assets/0b889f83-bee0-4c53-87a7-6c4094c882ff" />
  <img width="2000" height="1414" alt="다운로드 (2)" src="https://github.com/user-attachments/assets/6af85163-7f06-461a-8b9e-c68a25bb76f9" />
  <img width="2000" height="1414" alt="다운로드 (3)" src="https://github.com/user-attachments/assets/00cd5fa4-5602-4979-9886-395c0b2ddac2" />
  <img width="2000" height="1414" alt="다운로드 (4)" src="https://github.com/user-attachments/assets/a50021c4-df34-4c18-af64-475e40297c8f" />
  <img width="2000" height="1414" alt="다운로드 (5)" src="https://github.com/user-attachments/assets/68e113a2-198a-479c-96df-62b268c2515f" />
  <img width="2000" height="1414" alt="다운로드 (6)" src="https://github.com/user-attachments/assets/5859cd04-2e84-4e4d-bd85-07ae9c79d0dd" />
  <img width="2000" height="1414" alt="다운로드 (7)" src="https://github.com/user-attachments/assets/d7e45b95-34b8-4750-b4b0-02dfc47410cb" />
  <img width="2000" height="1414" alt="다운로드 (8)" src="https://github.com/user-attachments/assets/c8b37843-7b71-48a3-ac72-3e947c5c8dea" />
  <img width="2000" height="1414" alt="다운로드 (9)" src="https://github.com/user-attachments/assets/8274351f-f8c4-47a9-bf39-befb425debfe" />


# 131. Users Microservice에 CircuitBreaker 적용
  <img width="3024" height="1964" alt="image" src="https://github.com/user-attachments/assets/f6ff65dd-e9bc-403d-b78f-895065581e0c" />
  - OrderService 장애가 UserService 전체 장애로 이어지면 안 된다
  
  <!-- Resilience4J -->
  ``` pom.xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
  </dependency>
  ```
  - 첫번째, 기본적으로 제공해주는 CircuitBreaker 사용
  ``` userServiceImpl
    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new UsernameNotFoundException("User not found");

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        /* usgin a restTemplate */
//        String orderUrl = String.format(env.getProperty("order-service.url"), userId);
//        ResponseEntity<List<ResponseOrder>> orderListResponse =
//                restTemplate.exchange(orderUrl, HttpMethod.GET, null,
//                        new ParameterizedTypeReference<List<ResponseOrder>>() {
//                        });
//        List<ResponseOrder> orderList = orderListResponse.getBody();

        /* usgin a feignClient with logger */
//        List<ResponseOrder> orderList = null;
//        try {
//            orderList = orderServiceClient.getOrders(userId);
//        } catch (FeignException feignException) {
//            log.error(feignException.getMessage());
//        }
        /* usgin a feignClient with feignErrorDecoder */
        // List<ResponseOrder> orderList = orderServiceClient.getOrders(userId);
        /* CircuitBreaker */
        CircuitBreaker circuitBreaker =  circuitBreakerFactory.create("circuitbreaker");
        List<ResponseOrder> orderList = circuitBreaker.run(() -> orderServiceClient.getOrders(userId),
                throwable -> new ArrayList<>());

        userDto.setOrders(orderList);

        return userDto;
    }
  ```

  - 두 번째, Circuit Breaker 팩토리를 생성할 때 몇 가지 파라미터 값을 변환
  ``` Resilience4Jconfig
  @Configuration
  public class Resilience4Jconfig {
      @Bean
      public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {
          CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                  .failureRateThreshold(4)
                  .waitDurationInOpenState(Duration.ofMillis(1000))
                  .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                  .slidingWindowSize(2)
                  .build();
  
          TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                  .timeoutDuration(Duration.ofSeconds(4))
                  .build();
  
          return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                  .timeLimiterConfig(timeLimiterConfig)
                  .circuitBreakerConfig(circuitBreakerConfig)
                  .build()
          );
      }
  }
   ```

# 132. 분산 추적의 개요 Zipkin 서버 설치
## [정리] 마이크로서비스 분산 추적: Zipkin & Spring Cloud Sleuth

이 문서는 MSA 환경에서 서비스 간 호출 흐름을 파악하고 장애를 진단하기 위한 **분산 추적(Distributed Tracing)** 기술을 정리한 문서입니다.

---

### 1. 분산 추적(Distributed Tracing)이란?

### 1.1 필요성
- 마이크로서비스는 독립적으로 작동하지 않고 연쇄적으로 호출됩니다.
- 요청이 어디를 거쳐 가는지, 어느 단계에서 에러가 나거나 시간이 지연되는지(병목 현상) 파악하기 위해 필수적입니다.

> **📸 <img width="2000" height="1414" alt="다운로드_1" src="https://github.com/user-attachments/assets/113ab0aa-3aef-4cbb-adda-d0fe820c75ea" />**
> (설명: User -> Order -> Catalog 등 서비스들이 서로 거미줄처럼 얽혀 통신하는 강의 화면)

---

### 2. Zipkin (집킨)

트위터에서 개발한 분산 환경의 타이밍 데이터 수집 및 추적 시스템입니다. 구글의 **Dapper** 논문을 바탕으로 만들어진 오픈소스입니다.

#### 2.1 주요 구성 요소
- **Collector**: 각 마이크로서비스가 보내는 트래킹 정보를 수집합니다.
- **Query Service**: 수집된 데이터를 조회합니다.
- **Database**: 데이터를 저장합니다. (기본은 메모리 저장, 설정 시 DB 가능)
- **Web UI**: 수집된 정보를 대시보드로 시각화합니다.

#### 2.2 핵심 용어 (Trace & Span)
- **Trace ID**: 전체 요청 흐름을 관통하는 고유 ID입니다. 요청의 시작부터 끝까지 동일하게 유지됩니다.
- **Span ID**: 각 서비스 호출(작업 단위)마다 부여되는 고유 ID입니다. 

> **📸 <img width="2000" height="1414" alt="다운로드_1" src="https://github.com/user-attachments/assets/113ab0aa-3aef-4cbb-adda-d0fe820c75ea" />**
> (설명: 서비스 간 이동 시 Trace ID는 고정되고 Span ID가 새로 생성되는 다이어그램)

---

### 3. Spring Cloud Sleuth
<img width="2000" height="1414" alt="다운로드_2" src="https://github.com/user-attachments/assets/1476fc4f-7f9b-4880-9614-dbd41630cc76" />
Spring Boot 어플리케이션에서 Zipkin과 연동하기 위해 사용하는 라이브러리입니다.

- **역할**: 로그 파일에 `Trace ID`와 `Span ID`를 자동으로 생성하고 주입합니다.
- **연동 기술**: Servlet Filter, RestTemplate, FeignClient 등과 연동되어 호출 시마다 추적 정보를 헤더에 실어 보냅니다.

---

### 4. Zipkin 설치 및 실행

가장 간편하게 실행하는 방법은 `.jar` 파일을 이용하는 방법입니다.

#### 4.1 실행 명령어
```bash
# 최신 파일 다운로드 (Unix/Mac 기준)
curl -sSL [https://zipkin.io/quickstart.sh](https://zipkin.io/quickstart.sh) | bash -s

# jar 파일 실행
java -jar zipkin.jar
```

#### 4.2 Web UI 접속
Zipkin 서버가 실행되면 웹 브라우저를 통해 수집된 트래킹 데이터를 시각적으로 확인할 수 있습니다.

- **포트 번호**: `9411` (기본 설정)
- **접속 주소**: [http://localhost:9411](http://localhost:9411)

> **📸 이미지 추천 3: Zipkin 대시보드 메인 화면** > *설명: 9411 포트로 접속했을 때 보이는 대시보드와 검색 필터(Service Name, Span Name 등) 화면<img width="2000" height="1414" alt="다운로드_5" src="https://github.com/user-attachments/assets/be4c36d4-5987-4916-8bd5-19c6aeb0093e" />*

---

### 5. 전체 동작 프로세스 요약

분산 환경에서 하나의 요청이 처리되는 전체 흐름은 다음과 같습니다.

1. **최초 요청 (Trace ID 생성)**
   - 사용자가 시스템의 진입점(예: 마이크로서비스 A)에 요청을 보냅니다.
   - **Spring Cloud Sleuth**가 해당 요청에 대해 고유한 **Trace ID**를 생성합니다.

2. **정보 전파 (Span ID 발급)**
   - 서비스 A가 내부 로직 처리 후 서비스 B를 호출합니다.
   - 이때 동일한 **Trace ID**를 HTTP 헤더 등에 실어 공유하며, 서비스 B 호출이라는 개별 작업 단위를 나타내는 새로운 **Span ID**가 발급됩니다.

3. **데이터 전송 (Zipkin Reporter)**
   - 각 마이크로서비스는 자신의 작업이 완료되면, 수집된 추적 정보(Trace ID, Span ID, 소요 시간 등)를 **Zipkin 서버**로 전송합니다.

4. **시각화 (Zipkin UI)**
   - 개발자는 Zipkin UI에 접속하여 "누가 누구를 호출했는지", "어느 구간에서 몇 ms가 걸렸는지", "정상 처리되었는지"를 한눈에 확인하고 분석합니다.

> **📸 이미지 추천 4: Sleuth와 Zipkin 연동 프로세스 도식화** > *설명: 서비스 A -> B -> C 호출 시 Trace/Span ID가 어떻게 유지/변경되고 Zipkin으로 전달되는지 보여주는 전체 시스템 구성도
<img width="2000" height="1414" alt="image" src="https://github.com/user-attachments/assets/fa5f5f43-af46-4404-8300-fec9de495399" />*

# 133. Spring Cloud Sleuth + Zipkin을 이용한 Microservice의 분산 추적 ①

## 강의 개요
MSA 환경에서는 하나의 요청이 여러 서비스(UserService, OrderService 등)를 거쳐 처리된다.

이때:

- 어떤 서비스가 호출되었는지
- 어디서 장애가 발생했는지
- 응답 시간이 얼마나 걸렸는지

추적하기 위해 `Spring Cloud Sleuth`와 `Zipkin`을 사용한다.

---

# 1. Sleuth + Zipkin 설정

## pom.xml 의존성 추가

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-sleuth</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>
```

### 최신 프로젝트 샘플 코드는 아래 Github에서 확인하시기 바랍니다. 

- https://github.com/joneconsulting/toy-msa/tree/springboot3.2

- OpenJDK 21
- Spring Boot 3.2.2 + Spring Cloud 2023.0.0 
```xml
<!-- zipkin -->
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-observation</artifactId>
</dependency>

<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-brave</artifactId>
</dependency>

<dependency>
    <groupId>io.zipkin.reporter2</groupId>
    <artifactId>zipkin-reporter-brave</artifactId>
</dependency>
```

### 이미지 위치
- 다운로드_6.png

---

## application.yaml 설정

```yaml
spring:
  zipkin:
    base-url: http://localhost:9411
    enabled: true

sleuth:
  sampler:
    probability: 1.0
```

## 핵심 설정

- `base-url`
  - Zipkin 서버 주소
- `enabled: true`
  - Zipkin 활성화
- `probability: 1.0`
  - 모든 Trace 정보를 Zipkin으로 전송

<img width="2000" height="1414" alt="다운로드_6" src="https://github.com/user-attachments/assets/c22f15a7-f1b2-4a49-93d4-7b0cc8432747" />

---

# 2. Trace ID / Span ID

## Trace ID

하나의 요청 전체를 식별하는 ID

## Span ID

각 서비스 작업 단위를 식별하는 ID

예:

```text
Client
  ↓
UserService
  ↓
OrderService
```

- Trace ID는 동일
- Span ID는 서비스마다 새롭게 생성

<img width="2000" height="1414" alt="image" src="https://github.com/user-attachments/assets/60b7e0d8-0f76-4354-b236-d13012e56218" />
<img width="2000" height="1414" alt="image" src="https://github.com/user-attachments/assets/08aefeea-0752-4c57-aeae-a224cf591811" />


---

# 3. 로그 추적

Sleuth 적용 후 로그에 Trace 정보가 자동 추가된다.

```text
[서비스명, TraceId, SpanId]
```

이를 통해:

- 서비스 호출 흐름
- 장애 발생 위치
- 응답 시간

을 확인할 수 있다.

### 이미지 위치
<img width="2000" height="1414" alt="image" src="https://github.com/user-attachments/assets/8f56fb54-6672-439d-be40-3c777be72fd5" />
<img width="2000" height="1414" alt="image" src="https://github.com/user-attachments/assets/e5d55370-465f-4edf-9500-6b468b759b38" />


---

# 4. Zipkin에서 Trace 확인

로그에서 확인한 Trace ID를 Zipkin UI에서 검색하면:

- 어떤 서비스가 호출되었는지
- 어떤 메소드가 실행되었는지
- 처리 시간이 얼마나 걸렸는지

확인할 수 있다.

<img width="2000" height="1414" alt="image" src="https://github.com/user-attachments/assets/314d4742-ba88-4421-bf1c-8487e664753e" />
<img width="2000" height="1414" alt="image" src="https://github.com/user-attachments/assets/0ef88320-db8c-4378-b607-2d70875cecb2" />
<img width="2000" height="1414" alt="image" src="https://github.com/user-attachments/assets/c5afa9f8-9c5d-45c7-8bb3-15f24b69eff8" />


---

# 5. Dependency 분석

Zipkin의 Dependency 메뉴에서는:

```text
UserService → OrderService
```

같은 서비스 간 호출 관계를 시각적으로 확인할 수 있다.

또한:

- 호출 횟수
- 성공 횟수
- 실패 횟수

도 확인 가능하다.

### 이미지 위치
- 다운로드_13.png
<img width="2000" height="1414" alt="image" src="https://github.com/user-attachments/assets/1186a89f-1f76-468b-a0c2-5fcd00065816" />

---

# 6. 장애 테스트

강의에서는 OrderService에서 강제로 Exception을 발생시켜 장애 상황을 테스트했다.

장애 발생 시:

- Zipkin에서 에러 표시
- 실패 Trace 확인
- 실패 서비스 추적

이 가능하다.

### 이미지 위치
<img width="2000" height="1414" alt="image" src="https://github.com/user-attachments/assets/ce258b51-7e8b-431e-9f7a-e0c0b060d0ff" />
<img width="2000" height="1414" alt="image" src="https://github.com/user-attachments/assets/1d1a8654-e2e3-4184-90d9-47c32513f272" />

---

# 핵심 정리

## Sleuth 역할

- Trace ID 생성
- Span ID 생성
- 서비스 간 Trace 전파

## Zipkin 역할

- Trace 시각화
- 서비스 호출 흐름 분석
- 장애 및 성능 분석

## 핵심 포인트

- 같은 요청은 동일한 Trace ID 사용
- 서비스마다 다른 Span ID 생성
- Zipkin으로 MSA 호출 흐름 추적 가능

## 기존강의자료
<img width="2000" height="1414" alt="다운로드_6" src="https://github.com/user-attachments/assets/7a984759-f75d-4c06-a629-78d1c79b5ecc" />
<img width="2000" height="1414" alt="다운로드_7" src="https://github.com/user-attachments/assets/7f42258a-f710-49d0-8234-aabeca9d42fc" />
<img width="2000" height="1414" alt="다운로드_8" src="https://github.com/user-attachments/assets/a8204881-9b8d-4ef9-aee5-9e26ad805850" />
<img width="2000" height="1414" alt="다운로드_9" src="https://github.com/user-attachments/assets/18cb77e4-6203-4fb2-9ecf-2070e07a004a" />
<img width="2000" height="1414" alt="다운로드_10" src="https://github.com/user-attachments/assets/4b3a0189-f1cc-4fa0-9c87-27da1dc04421" />
<img width="2000" height="1414" alt="다운로드_11" src="https://github.com/user-attachments/assets/184b2fc6-87db-4284-a025-db7a38e053fa" />
<img width="2000" height="1414" alt="다운로드_12" src="https://github.com/user-attachments/assets/de2ac56d-3ed6-4b7c-a08b-82463d1af3d3" />
<img width="2000" height="1414" alt="다운로드_13" src="https://github.com/user-attachments/assets/012b1eff-ac50-434d-9405-dae7cecb77e2" />
<img width="2000" height="1414" alt="다운로드_14" src="https://github.com/user-attachments/assets/4b7ecf84-f8cd-434d-9bcb-2a1126858830" />
<img width="2000" height="1414" alt="다운로드_15" src="https://github.com/user-attachments/assets/7ee596dd-a584-4f99-9107-90af618e584d" />
<img width="2000" height="1414" alt="다운로드_16" src="https://github.com/user-attachments/assets/278356a2-dc68-472b-b237-8df72eb55d11" />

# 134. Spring Cloud Sleuth + Zipkin을 이용한 Microservice의 분산 추적 ②
# 1. 기존 방식 (Spring Boot 2.x)

## 기존 의존성

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-sleuth</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>
```

---

## 기존 로그 형태

```text
[order-service, ae377e3700a77724, as377e3900a77723]
```

구조:

```text
[서비스명, TraceId, SpanId]
```

### 설명

- order-service
  - 서비스 이름
- ae377e3700a77724
  - Trace ID
- as377e3900a77723
  - Span ID

즉 로그만 봐도:

- 어떤 요청인지
- 어떤 서비스 흐름인지
- 어떤 Span 인지

즉시 확인 가능했다.

---

# 2. 최신 방식 (Spring Boot 3.x)

## 환경

- OpenJDK 21
- Spring Boot 3.2.2
- Spring Cloud 2023.0.0

---

## Github 예제

- https://github.com/joneconsulting/toy-msa/tree/springboot3.2

---

## 변경된 의존성

```xml
<!-- zipkin -->
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-observation</artifactId>
</dependency>

<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-brave</artifactId>
</dependency>

<dependency>
    <groupId>io.zipkin.reporter2</groupId>
    <artifactId>zipkin-reporter-brave</artifactId>
</dependency>
```

---

# 3. 로그 출력 방식 변경

## Boot 3.x 기본 로그
- 의존성 변경으로 인한 [order-service] [o-auto-1-exec-1]로 수정
<img width="2728" height="660" alt="image" src="https://github.com/user-attachments/assets/cde8fd6d-8475-4406-b234-1e4c92c56d7c" />
```text
[order-service] [o-auto-1-exec-1]
```

구조:

```text
[서비스명] [스레드명]
```

---

## 변경 이유

Spring Boot 3.x 부터는:

```text
Spring Cloud Sleuth 제거
→ Micrometer Tracing 사용
```

구조로 변경되면서,
기본 로그 패턴에서도 TraceId / SpanId 출력이 제거되었다.

따라서:

- Trace ID
- Span ID

를 다시 출력하려면 logging 패턴을 직접 설정해야 한다.

---

# 4. TraceId / SpanId 다시 출력하기

## application.yaml 설정

```yaml
logging:
  pattern:
    level: "%5p [${spring.application.name:},%X{traceId:-},%X{spanId:-}]"
```







