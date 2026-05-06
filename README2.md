# SESSION_9 Spring Cloud Bus
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
    -it : Interactive TTY라는 옵션으로써 사용자가 키보드와 모니터 콘솔을 이용해서 어떠한 명령어를 입력했을 때 해당하는 명령어가 도커 컨테이너 내부에 전달될 수 있도록 제공해주는 기능
    --rm : Remove 옵션이라고 해서 컨테이너가 작동 중이다가 다 작동이 끝난 다음에 컨테이너를 중지시켰을 때 자동적으로 컨테이너 리소스를 삭제해 버리는 기능
    <img width="1783" height="1123" alt="image" src="https://github.com/user-attachments/assets/db3a14a2-29a3-4cf4-a65c-68560ea56861" />
    id/pw: guest/guest










    
