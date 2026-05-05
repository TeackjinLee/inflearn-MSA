<img width="1794" height="1009" alt="image" src="https://github.com/user-attachments/assets/9b2ad5d5-3220-4274-9e7c-d27bfc5d0f47" /><img width="1485" height="848" alt="image" src="https://github.com/user-attachments/assets/bac18424-f3d8-44bf-b283-28a9ca030325" />
# inflearn-MSA
# SESSION_3. API Gateway Service
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
Filter - Custom,Global, Logging Filter들은 어느 위치에 지정되냐에 따라 달라짐 AbstractGatewayFilterFactor을 상속.
27. Spring Cloud Gateway - Custom Filter 적용 ➀
29. Spring Cloud Gateway - Global Filter 적용 ➀
2026-04-15T08:26:21.540+09:00  INFO 15125 --- [apigateway-service] [ctor-http-nio-2] c.d.a.filter.GlobalFilter                : Global Filter baseMessage: Spring Cloud Gateway WebFlux Global Filter, /[0:0:0:0:0:0:0:1]:65100
2026-04-15T08:26:21.541+09:00  INFO 15125 --- [apigateway-service] [ctor-http-nio-2] c.d.a.filter.GlobalFilter                : Global Filter: request id -> fa5616b5-1
2026-04-15T08:26:21.541+09:00  INFO 15125 --- [apigateway-service] [ctor-http-nio-2] c.d.a.filter.CustomFilter                : Custom PRE Filter: request id -> fa5616b5-1
2026-04-15T08:26:21.567+09:00 ERROR 15125 --- [apigateway-service] [ctor-http-nio-2] i.n.r.d.DnsServerAddressStreamProviders  : Unable to load io.netty.resolver.dns.macos.MacOSDnsServerAddressStreamProvider, fallback to system defaults. This may result in incorrect DNS resolutions on MacOS. Check whether you have a dependency on 'io.netty:netty-resolver-dns-native-macos'. Use DEBUG level to see the full stack: java.lang.UnsatisfiedLinkError: failed to load the required native library
2026-04-15T08:26:21.607+09:00  INFO 15125 --- [apigateway-service] [ctor-http-nio-2] c.d.a.filter.CustomFilter                : Custom PRE Filter: request id -> 200 OK
2026-04-15T08:26:21.607+09:00  INFO 15125 --- [apigateway-service] [ctor-http-nio-2] c.d.a.filter.GlobalFilter                : Global Filter End: response code -> 200 OK


32. Spring Cloud Gateway - Logging Filter 적용 ➁
<img width="1440" height="814" alt="image" src="https://github.com/user-attachments/assets/806c7970-bba2-4174-855a-27ce6daed609" />

<img width="1432" height="878" alt="image" src="https://github.com/user-attachments/assets/b562282e-c92c-4d3a-b9ef-67350076c090" />

# SESSION_4. E-commerce 애플리케이션
35. E-commerce 애플리케이션 개요
<img width="1469" height="832" alt="image" src="https://github.com/user-attachments/assets/89fee070-b789-4331-aec5-11b5d8c7fefa" />

36. E-commerce 애플리케이션 구성
<img width="1693" height="844" alt="image" src="https://github.com/user-attachments/assets/5a286a0d-9798-4242-9577-cc1b03c65fd2" />
<img width="1445" height="812" alt="image" src="https://github.com/user-attachments/assets/e6c87cc1-5916-421f-9c43-5e34c10aac56" />
Message Broker - Kafka

<img width="1455" height="817" alt="image" src="https://github.com/user-attachments/assets/5d405a6e-3733-4385-beaf-7bd270b5590c" />

# SESSION_5. Users Microservice ➀
    섹션소개
    - Users Microservice 개요
    - Users Microservice - 프로젝트 생성
    - Users Microservice - DB
    - Users Microservice - 회원가입
    - Users Microservice - Security
38. Users Microservice 개요
<img width="1467" height="824" alt="image" src="https://github.com/user-attachments/assets/c3155344-ba54-4f79-bfb1-5eb3af3aec32" />

<img width="1467" height="831" alt="image" src="https://github.com/user-attachments/assets/4768460e-50c7-4571-b213-a3914fd67aea" />

41. Users Microservice - 사용자 추가 개요 (JPA)
<img width="1457" height="822" alt="image" src="https://github.com/user-attachments/assets/ccd836a9-bf78-48cb-9270-528db239255e" />

45. Users Microservice - Spring Security 연동
<img width="2169" height="1234" alt="image" src="https://github.com/user-attachments/assets/ef57ebfd-c8d5-41d1-8528-c77e35593c83" />

# SESSION_6. Catalogs and Orders Microservice
49. 섹션 소개
    - Users Microservice 기능추가
    - Catalogs Microservice 프로젝트 생성
    - Orders Microservice 프로젝트 생성
50. Users Microservice - 사용자 조회 ①
51. Users Microservice와 Spring Cloud Gateway
52. Users Microservice와 Spring Cloud Gateway
    -     routes: #customFilter
            - id: user-service
              uri: lb://USER-SERVICE
              predicates:
                - Path=/user-service/**
    - @RequestMapping("/user-service") => 일괄 작업

54. Catalogs Microservice - 개요
<img width="1485" height="848" alt="image" src="https://github.com/user-attachments/assets/e737af66-ee66-433b-8cde-0727ce0c4e28" />
<img width="1459" height="613" alt="image" src="https://github.com/user-attachments/assets/41671e4c-cab5-4ef9-be57-cb9eea3ff665" />

<img width="1466" height="831" alt="image" src="https://github.com/user-attachments/assets/9d7fcf69-0fae-446e-847f-4d825a8873f8" />
- 재실행시 상품의 초기 데이터 생성
- 
56. Catalogs Microservice - 기능 구현 ②
aip-gateway의 application.yml에 catalog-service 추가 작업
spring:
  application:
    name: apigateway-service
  cloud:
    gateway:
      server:
        webflux:
          default-filters:
            - name: GlobalFilter
              args:
                baseMessage: Spring Cloud Gateway WebFlux Global Filter
                preLogger: true
                postLogger: true
          routes: #customFilter
            - id: user-service
              uri: lb://USER-SERVICE
              predicates:
                - Path=/user-service/**
            - id: catalog-service
              uri: lb://CATALOG-SERVICE
              predicates:
                - Path=/catalog-service/**

# SESSION_7. Users Microservice_1
61. Users Microservice - 기능 추가
    <img width="1471" height="832" alt="image" src="https://github.com/user-attachments/assets/2bb40a58-5710-48fa-8bba-21a8fcd54e9f" />
    <img width="1478" height="825" alt="image" src="https://github.com/user-attachments/assets/6e52a469-196c-4d85-b9dc-1db772bbbdb5" />
    <img width="1473" height="728" alt="image" src="https://github.com/user-attachments/assets/454a08eb-2af4-4e2b-b84e-9ffe69b43b24" />
    <img width="1479" height="838" alt="image" src="https://github.com/user-attachments/assets/0aeef8ce-f6fb-46a1-b774-687fc0bba665" />
65. AuthenticationFilter 추가
    <img width="1798" height="1008" alt="image" src="https://github.com/user-attachments/assets/483c780a-45ef-466d-af49-1b1ebb2c35f9" />
    - AuthenticationFilter에서 JWT토큰 만들고 그걸 기반으로 UserService에서 한번더 검증.
    <img width="1794" height="1009" alt="image" src="https://github.com/user-attachments/assets/939ea9d8-08de-4e1b-87de-73c415611ef5" />
    - WebSecurity와 AuthenticationFilter는 같은 내용을 공유
    - 
    <img width="1792" height="1010" alt="image" src="https://github.com/user-attachments/assets/455e1f41-792d-4287-ba31-8f16f45c1d3c" />
    - 로그인이 성공했을때 처리해아하는 메소드
    - Jwt생상, Jwt만료시간
      
67. JWT(JSON Web Token) 개요
    <img width="1798" height="1003" alt="image" src="https://github.com/user-attachments/assets/def6677c-6b06-4887-ae14-375dece560f5" />
    <img width="1793" height="1009" alt="image" src="https://github.com/user-attachments/assets/8b9c19c2-3dbb-40fa-a30f-e01754304d0e" />
    <img width="1797" height="1010" alt="image" src="https://github.com/user-attachments/assets/6423a87d-de50-4b3c-9c23-9941f4bc7937" />
    <img width="1794" height="1013" alt="image" src="https://github.com/user-attachments/assets/c511f0d3-fedf-4e42-8d18-7d1dda3dbd4c" />
    


