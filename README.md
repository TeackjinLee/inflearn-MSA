
# inflearn-MSA
# inflearn-MSA
17. User Service - 프로젝트 생성
20. API Gateway 란?
    API Gateway는 사용자가 설정한 라우팅 설정에 따라서 각각의 엔드포인트로 클라이언트를 대신해서 요청을 하고 응답을 받아서 다시 클라이언트한테 전달해주는 일종의 프락시 역할을 하게 됩니다.<br/>
    - RestTemplate<br/>
    - Feign Client<br/>
21. Spring Cloud Gateway의 소개<br/>
    - Spring Cloud Gateway Webmvc<br/>
    <img width="2093" height="1163" alt="image" src="https://github.com/user-attachments/assets/e979d6ee-95df-4d45-81c7-236c0e2708eb" />
24. Webflux를 위한 Spring Cloud Gateway
25. Spring Cloud Gateway - Filter 적용 ➀
<img width="2131" height="1326" alt="image" src="https://github.com/user-attachments/assets/55641036-eb2b-49e9-be51-2c02836d09a7" />
<img width="2140" height="1346" alt="image" src="https://github.com/user-attachments/assets/e086e196-6adc-4dc9-93ec-3f08be929616" />
8000포트로 호출시 에러 안남 그러나 8081이나 8082로 호출시 에러
26. Spring Cloud Gateway - Filter 적용 ➁

spring:
  application:
    name: apigateway-service
  cloud:
    gateway:
      server:
        webflux:
          routes:
            - id: first-service
              uri: http://localhost:8081
              predicates:
                - Path=/first-service/**
              filters:
                - AddRequestHeader=f-request, 1st-request-header-by-yaml
                - AddRequestHeader=f-response, 1st-response-header-from-yaml
            - id: second-service
              uri: http://localhost:8082
              predicates:
                - Path=/second-service/**
              filters:
                - AddRequestHeader=s-request, 1nd-request-header-by-yaml
                - AddRequestHeader=s-response, 1nd-response-header-from-yaml

27. Spring Cloud Gateway - Custom Filter 적용 ➀
