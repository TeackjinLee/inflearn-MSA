# Section_9 Spring Cloud Bus
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


    

    


