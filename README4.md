# SECTION_14. 장애처리와 Microservice 분산 추적

## 129.색션소개
  - CircuitBreaker(회복성패턴)
  - Resilience4j
  - Distributed Tracing
  - Trace ID and Span ID
  - Zipkin server 활용

## 130. CircuitBreaker와 Resilience4J의 사용
## 마이크로서비스 통신 장애 대응: Circuit Breaker & Resilience4j

이 문서는 마이크로서비스 아키텍처(MSA)에서 서비스 간 통신 시 발생할 수 있는 오류를 인지하고, 이를 서킷 브레이커를 통해 해결하는 방법을 정리한 가이드입니다.

---

### 1. 마이크로서비스 간 통신과 장애 발생

#### 1.1 주요 발생 오류
- **Unknown Host Exception**: 호출하려는 서비스의 주소를 찾을 수 없을 때 발생합니다.
- **500 Server Exception / Timeout**: 대상 서비스가 응답하지 않거나 내부 오류가 발생했을 때 호출 측까지 장애가 전파됩니다.

> **<img width="2000" height="1414" alt="다운로드(0)" src="https://github.com/user-attachments/assets/22b6ff5f-1797-4141-ba54-acd815dc4556" />**
> *설명: 유저 서비스에서 오더 서비스를 호출했을 때 발생하는 500 에러와 인텔리제이 콘솔의 Unknown Host Exception 로그 화면*

#### 1.2 장애 전파 (Cascading Failure)
- 특정 서비스(예: Order Service)의 문제가 해당 서비스를 호출하는 다른 서비스(예: User Service)의 장애로 이어지는 현상입니다.
- 사용자는 유저 정보 조회만 원했더라도, 주문 내역 조회 기능의 장애 때문에 전체 요청이 실패하게 됩니다.

---

### 2. 서킷 브레이커 (Circuit Breaker)

#### 2.1 개념
장애가 발생하는 서비스에 대해 반복적인 호출을 하지 않도록 차단하는 '차단기' 역할을 합니다. 장애가 복구될 때까지 요청을 우회시켜 시스템 전체의 가용성을 보장합니다.

> **<img width="2000" height="1414" alt="다운로드 (1)" src="https://github.com/user-attachments/assets/0b889f83-bee0-4c53-87a7-6c4094c882ff" />**
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

> **[이미지 추천 3: Netflix 라이브러리 대체 현황 표]**
> *설명: 강의 중 소개된 넷플릭스 라이브러리와 이를 대체하는 최신 기술들의 비교 화면*

---

### 4. Resilience4j 구현 및 설정

#### 4.1 의존성 추가 (pom.xml)
`spring-cloud-starter-circuitbreaker-resilience4j` 라이브러리를 추가하여 사용합니다.

#### 4.2 기본 구현 방식 (Fallback)
서비스 호출 시 문제가 생기면 `ArrayList`의 기본 생성자(빈 리스트)를 반환하도록 설정하여, "주문 내역이 없는 것"처럼 보여줌으로써 전체 시스템 에러를 방지합니다.

> **[이미지 추천 4: CircuitBreakerFactory 사용 코드 예시]**
> *설명: getOrders() 메소드 호출 시 서킷 브레이커를 적용하고 fallback을 지정하는 Java 코드*

#### 4.3 주요 상세 설정 (Customizing)
`Resilience4jCircuitBreakerFactory`를 통해 세부 파라미터를 조정할 수 있습니다.

- **failureRateThreshold**: 실패 확률 임계치 (기본값 50%). 이 비율을 넘으면 서킷이 Open 됩니다.
- **waitDurationInOpenState**: 서킷이 Open 상태를 유지하는 시간. 이 시간이 지나면 다시 연결을 시도(Half-open)합니다.
- **slidingWindowType**: 통계 기준 (Count 기반 또는 Time 기반).
- **slidingWindowSize**: 통계에 반영할 기록의 수.
- **timeoutDuration**: TimeLimiter 설정. 특정 시간(예: 4초) 동안 응답이 없으면 장애로 간주합니다.

> **[이미지 추천 5: 상세 설정 파라미터 설명 화면]**
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


## 131. Users Microservice에 CircuitBreaker 적용
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











