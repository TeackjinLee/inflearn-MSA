<img width="1790" height="1007" alt="image" src="https://github.com/user-attachments/assets/2c3f6367-fcb1-4d91-a808-c6e923827664" /><img width="1789" height="1008" alt="image" src="https://github.com/user-attachments/assets/ea91ea84-eec2-4c67-b783-b996b6160b4c" /># Section_9 Spring Cloud Bus
84. 섹션 소개
    - Spring Cloud Bus
    - RabbitMQ 설치
    - 프로젝트 수정 - Actuator 추가
    - 테스트
85. Spring Cloud Bus 개요
  <img width="2051" height="1151" alt="image" src="https://github.com/user-attachments/assets/99cb7a57-30a5-4877-82ac-9b0885e344d9" />
  - refresh 각각의 어플래이션마다 재기동 하는 번거로움이있음.
  <img width="2052" height="1112" alt="image" src="https://github.com/user-attachments/assets/82b26ff5-0236-4950-bf77-6bcd5db36b6c" />
  - Actuator의 리프레스를 각각 하지 않더라도 변경된 사항을 다 가져갈 수 있도록 지원해주는 기능이 Spring Cloud 버스.
  <img width="2061" height="1107" alt="image" src="https://github.com/user-attachments/assets/97384c6e-eb3c-4ea6-94c5-862b5fe2fbaf" />
  <img width="2069" height="1103" alt="image" src="https://github.com/user-attachments/assets/98a0a8c3-a515-4f2e-984e-44fac8a36f62" />
  <img width="2057" height="1145" alt="image" src="https://github.com/user-attachments/assets/145160ec-4416-44a4-9a0a-97d837a521d9" />
  한번 호출하게 되면 나머지 스스템 노드에 업데이트 된다.

86. RabbitMQ 설치 - Docker
    <img width="1775" height="1123" alt="image" src="https://github.com/user-attachments/assets/a030dc03-ead4-4c3f-9a6f-46e0ae8ed8b1" />
    실행 명령어
    docker run -it -d --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4-management
    -it : Interactive TTY라는 옵션으로써 사용자가 키보드와 모니터 콘솔을 이용해서 어떠한 명령어를 입력했을 때 해당하는 명령어가 도커 컨테이너 내부에 전달될 수 있도록 제공해주는 기능
    --rm : Remove 옵션이라고 해서 컨테이너가 작동 중이다가 다 작동이 끝난 다음에 컨테이너를 중지시켰을 때 자동적으로 컨테이너 리소스를 삭제해 버리는 기능
    <img width="1783" height="1123" alt="image" src="https://github.com/user-attachments/assets/db3a14a2-29a3-4cf4-a65c-68560ea56861" />
    id/pw: guest/guest

89. AMQP 사용
    <img width="2108" height="1152" alt="image" src="https://github.com/user-attachments/assets/1b197e49-14dd-431a-900f-48197e3f0995" />
    <img width="2100" height="1172" alt="image" src="https://github.com/user-attachments/assets/497d1945-db2f-4c96-ae11-88c71212dd22" />
    <img width="2095" height="1184" alt="image" src="https://github.com/user-attachments/assets/badde0d6-a19c-4a7e-8f7f-f4bdda1adf55" />
    


# Section_10 암호화 처리를 위한 Encryption과 Decryption
92. 섹션 소개
    - Encryption types
    - JCE
    - Symmetric Encryption
    - Asymmetric Encryption
        
    
93. 대칭키와 비대칭키
    <img width="1795" height="1008" alt="image" src="https://github.com/user-attachments/assets/b9bedde6-5947-454d-a223-ee7da7b3f4b5" />
    <img width="1791" height="1006" alt="image" src="https://github.com/user-attachments/assets/744f3d3a-12c8-4861-a7f5-89b927b29d07" />
    <img width="1794" height="1004" alt="image" src="https://github.com/user-attachments/assets/7e82e4d3-f3c9-4feb-9a83-aa891eddee72" />
    - java 21버전은 필요 없음.

94. 대칭키를 이용한 암호화 ①
    <img width="1789" height="1009" alt="image" src="https://github.com/user-attachments/assets/872653d0-98c2-4384-ac0b-ba668a06f678" />
    <img width="1788" height="1006" alt="image" src="https://github.com/user-attachments/assets/8a837dfe-56e4-4eaa-a76f-04204f11d62c" />
    
94. 대칭키를 이용한 암호화 ①
    <img width="1793" height="1006" alt="image" src="https://github.com/user-attachments/assets/e02ed695-f6a9-433d-9459-90bf42f2b627" />
    <img width="1790" height="1007" alt="image" src="https://github.com/user-attachments/assets/58815140-0e75-4a2b-8f72-076f66a9a62a" />
    {cipher} 암호화 데이터를 식별
    <img width="1796" height="1004" alt="image" src="https://github.com/user-attachments/assets/c5f7f313-5c2f-4c8c-99f2-83f0cdde6ef2" />
    - 암호화가 풀려서 보임
    <img width="1792" height="1008" alt="image" src="https://github.com/user-attachments/assets/87ee36c6-d54f-43bb-a7b4-4dc38b8cc130" />
    - 암호화 데이터를 잘못 불러올 경우 <n/a>로 표기
96. 비대칭키를 이용한 암호화 ① - keytool
    <img width="1789" height="1009" alt="image" src="https://github.com/user-attachments/assets/82e790b1-dad6-41ea-968c-2d875b90dc0b" />
    - RSA 공개키, 전자서명 가능, 전자상거래에서 사용
    <img width="1788" height="1006" alt="image" src="https://github.com/user-attachments/assets/7eb942df-0154-4a0b-86a4-e3ee36ace733" />
    <img width="1786" height="1002" alt="image" src="https://github.com/user-attachments/assets/78a8945c-ef67-4bd6-8351-6eb0e637e68d" />
    <img width="1792" height="1001" alt="image" src="https://github.com/user-attachments/assets/4c415ef7-1581-4c15-b8b9-be814116442d" />
    <img width="1790" height="1007" alt="image" src="https://github.com/user-attachments/assets/fe2947bf-d4b5-4f05-978a-4faa6a01e063" />
    public, private 키를 만들어 안전성 확보

97. 비대칭키를 이용한 암호화 ②
    keytool -genkeypair \ -alias #키_별칭(alias)# \ -keyalg RSA \ -dname "CN=#이름(Common Name)#, OU=#부서명(Organizational Unit)#, O=#회사명(Organization)#, L=#도시명(Locality)#, C=#국가코드(Country)#" \ -keypass "#키_비밀번호#" \ -keystore #생성할_keystore_파일명#.jks \ -storepass "#keystore_비밀번호#"
    keytool -genkeypair -alias apiEncryptionKey -keyalg RSA -dname "CN=ltj, OU=API Development, O=tag101, L=Seoul, C=KR" -keypass "1q2w3e4r" -keystore apiEncryptionKey.jks -storepass "1q2w3e4r"
        #키_별칭(alias)# → 키 식별 이름 (예: apiEncryptionKey)
        #이름(Common Name)# → 사용자 또는 서버 이름
        #부서명(Organizational Unit)# → 개발팀, API팀 등
        #회사명(Organization)# → 회사명
        #도시명(Locality)# → Seoul, Busan 등
        #국가코드(Country)# → KR, US 등 2자리 국가코드
        #키_비밀번호# → 개인키 비밀번호
        #생성할_keystore_파일명# → 생성될 jks 파일명
        #keystore_비밀번호# → keystore 접근 비밀번호

    public key 생성
    > keytool -exportcert -alias [별칭] -keystore [키스토어파일].jks -rfc -file [출력파일].pem
    > keytool -exportcert -alias apiEncryptionKey -keystore apiEncryptionKey.jks -rfc -file pulic-key.pem
    
    private key 생성
    - 까다로움. pkcs12로 변환후 뽑아야함.
    - keytool로 1차적으로 가능
    > keytool -importkeystore -srckeystore [원본].jks -srcalias [별칭] -destkeystore [출력].p12 -deststoretype PKCS12
    > keytool -importkeystore -srckeystore apiEncryptionKey.jks -srcalias apiEncryptionKey -destkeystore test-private.p12 -deststoretype PKCS12
    <img width="1763" height="1115" alt="image" src="https://github.com/user-attachments/assets/24f87785-ed7a-49dd-8a96-49a12ae2c3de" />
    openssl 명령어 다운 (window 기준)
    > openssl pkcs12 -in test-private.p12 -nocerts -nodes -out private-key.pem
    <img width="1943" height="1119" alt="image" src="https://github.com/user-attachments/assets/b7356899-3baf-47f8-9d42-ab0da89989cf" />
- 이번 시간에 사용할 파일은 private, public-key를 나눠쓰는게 아닌 apiEncryptionKey.jks하나로 암호화, 복호화 진행

# Section_11 Microservice간 통신
98. 섹션 소개
    - Communication types
    - RestTemplate
    - Feign Client -Log, Exception
    - ErrorDecoder
    - Multiple Orders Service

99. Communication types
    <img width="1783" height="1000" alt="image" src="https://github.com/user-attachments/assets/7ecd7b51-babb-41f5-bcc4-7e408a550ad4" />
     - Synchronous HTTP communication 강력한 일괄성을 유지 동기방식 -> REST API
     - Asynchronous coummunication over AMOP 최종적인 일괄성 유지 비동기 방식 -> M.M kafka
    <img width="1786" height="1007" alt="image" src="https://github.com/user-attachments/assets/4e393561-7a37-4c8c-9bf6-6f26e206d404" />

100. RestTemplate 사용 ①
    <img width="1783" height="1003" alt="image" src="https://github.com/user-attachments/assets/f9d1d876-9d43-46c0-92e9-9e35ad402a33" />
    <img width="1784" height="1006" alt="image" src="https://github.com/user-attachments/assets/685f5fd2-0033-41e7-a786-f761ef29b565" />
    <img width="1786" height="1004" alt="image" src="https://github.com/user-attachments/assets/c5f8dbde-fa9c-4191-873d-8bd7a920e300" />

101. RestTemplate 사용 ②
     <img width="1789" height="1008" alt="image" src="https://github.com/user-attachments/assets/87f37067-604b-498f-9810-ec1a494472d8" />
    url: Microservice name으로 등록하기 위해 @LoadBalanced 어노테이션 필요
102. FeignClient 사용 ①
    <img width="1786" height="1004" alt="image" src="https://github.com/user-attachments/assets/d811eb62-c013-4fe3-9864-bdb3992ab928" />
    <img width="1793" height="1002" alt="image" src="https://github.com/user-attachments/assets/4b3b91ca-e5af-4bc4-b1ab-f8d4487b3b20" />
    <img width="1790" height="1007" alt="image" src="https://github.com/user-attachments/assets/2290358d-4e3b-4815-b9d3-a48d9d9fbcf7" />
    -> 기존 RestApi의 호출부를 인터페이스로 변경하여 깔끔하게 관리 가능
    <img width="1791" height="1000" alt="image" src="https://github.com/user-attachments/assets/304f8b47-4470-4757-9c01-53b7ba9ded7c" />
    
103. FeignClient 사용 ②
    <img width="1786" height="1004" alt="image" src="https://github.com/user-attachments/assets/8e12fdab-ac5a-4966-8493-439b6084b2cc" />
    <img width="1787" height="1006" alt="image" src="https://github.com/user-attachments/assets/0eb2cb7b-f296-400c-b065-ebddaa8dabf3" />
    user-service에서 order-service의 로그를 확인 가능

104. FeignClient 예외 처리 ①
    <img width="1784" height="1005" alt="image" src="https://github.com/user-attachments/assets/b12adb2a-c234-40ee-90b5-3b9d0edd88a2" />
    오류 발생시 처리 방법 -> 잘못된 주소 발생시 400대이지만 500대를 표출되는중. 페이지 오류로 가보니 404가 보임.
    <img width="1792" height="1004" alt="image" src="https://github.com/user-attachments/assets/227e9751-4aa5-420a-b062-ee04e5c7c037" />
    1. try {} catch (){}를 활용. 정상적으로 결과가 나오는거 같지만 ERROR가 나오는중.
105. FeignClient 예외 처리 ②
     OpenFeign을 사용하면서 `FeignException`에 대해 살펴보도록 하겠습니다.
     <img width="2186" height="1520" alt="image" src="https://github.com/user-attachments/assets/276f1d02-92b6-4193-ab1d-8a450267bfa4" />
     
106. ErrorDecoder를 이용한 예외 처리
    FeignClient를 메소드화 해서 관리하는 방법.
    <img width="1794" height="1012" alt="image" src="https://github.com/user-attachments/assets/811d10f2-4007-454c-a555-bd91e92796ae" />
    <img width="1793" height="1008" alt="image" src="https://github.com/user-attachments/assets/0e681a8c-3ccc-4dff-8eae-b67e2f8a6218" />
    <img width="1790" height="1003" alt="image" src="https://github.com/user-attachments/assets/477af45c-c42f-4407-9d46-5c1b5262f13b" />
     "User's orders is empty."를 고정값이 아닌 가변데이터로 바꿔 user-service.yml에서 관리.
     <img width="1792" height="1001" alt="image" src="https://github.com/user-attachments/assets/6346b6eb-a371-48ff-822d-63d78063493d" />
     <img width="1788" height="1158" alt="image" src="https://github.com/user-attachments/assets/d1700a36-44cc-4b96-abd5-849eca2020f7" />

107. 데이터 동기화 문제 ①
    <img width="1788" height="1009" alt="image" src="https://github.com/user-attachments/assets/dcc896ba-c4e3-4c7d-9147-c80da224e1a3" />
    <img width="1791" height="1004" alt="image" src="https://github.com/user-attachments/assets/eb6b9f4b-49d4-44a2-8022-93cc1436c9b6" />
    -> 주문이 각각 다른 서버에 순차적으로 일어남.
    <img width="1786" height="1004" alt="image" src="https://github.com/user-attachments/assets/7848b7b5-d91e-4b65-b8ac-c8d7466a82ae" />
    -> 데이터도 분산되서 가져오는 문제가 발생
    <img width="1790" height="1007" alt="image" src="https://github.com/user-attachments/assets/e41febaf-0920-45a1-a654-b8b2839ca8ab" />
     ->  1. 하나의 데이터 베이스 shared database사용하면 문제 해결
         2. MessageQ를 이용한 데이터베이스 동일화 시키기.
         3. kafka Connector를 통한 동기화.
     
     


    
















