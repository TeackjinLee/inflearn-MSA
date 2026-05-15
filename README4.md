# SECTION_14. 장애처리와 Microservice 분산 추적

## 129.색션소개
  - CircuitBreaker(회복성패턴)
  - Resilience4j
  - Distributed Tracing
  - Trace ID and Span ID
  - Zipkin server 활용

## 130. CircuitBreaker와 Resilience4J의 사용
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











