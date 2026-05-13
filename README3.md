# Section12_데이터 동기화를 위한 Apache Kafka의 활용_1
109. 섹션 소개
  - Kafka란?
  - Kafka 설치
  - Kafka Producer/Consumer
  - Kafka Connect
    
110. Apache Kafka 개요
     <img width="2055" height="1108" alt="image" src="https://github.com/user-attachments/assets/02a91779-0024-4dc7-93db-f62891544193" />
     - Broker 서버로 샹각하면 된다.
  <img width="2054" height="1115" alt="image" src="https://github.com/user-attachments/assets/258e0b6a-64d6-4e55-8e03-06eb678b8640" />
  <img width="2053" height="1149" alt="image" src="https://github.com/user-attachments/assets/1b7af4a2-e737-4927-bf8e-af225aca74f6" />
  <img width="2048" height="1146" alt="image" src="https://github.com/user-attachments/assets/f47e82b8-d30d-4a6e-97c1-d9ec4b0f6030" />
  - Zookeeper Broker들을 제어
111. Apache Kafka 설치
  <img width="2054" height="1107" alt="image" src="https://github.com/user-attachments/assets/fcb30ea4-76d8-4416-b425-4b29a56fd294" />
  http://kafka.apache.org/
  <img width="2898" height="1726" alt="image" src="https://github.com/user-attachments/assets/c5ac13f4-a41b-4120-be77-635c8fe7d73e" />
  > tar xvf kafka_*.tgz
  - bin 실행파일들, config 설정 폴더들
  - zookeeper, kafka 3대면 안정적, 예제에서는 하나씩만 설정
112. Apache Kafka 사용 - Producer/Consumer
  <img width="2045" height="1142" alt="image" src="https://github.com/user-attachments/assets/982ac683-1988-4cf7-83c1-3e11f9d2d897" />
  <img width="2976" height="1406" alt="image" src="https://github.com/user-attachments/assets/7a7aa55d-72b9-4146-af2d-8a213df2569c" />
  - java외에 사용 가능한 언어들
  <img width="2046" height="1149" alt="image" src="https://github.com/user-attachments/assets/6448f0fe-b7cc-494c-9e48-4cf3304d7fe1" />
  - kafka 자체를 관리하는 zookeeper 필요
  - zookeeper > kafka 
  <img width="2055" height="1126" alt="image" src="https://github.com/user-attachments/assets/86b56310-4c6b-4ff2-aa0f-4a371c747146" />
  <img width="2055" height="1153" alt="image" src="https://github.com/user-attachments/assets/a14d8262-51b0-40f8-abc7-10093adca9e5" />
  예전 구조:
    Producer/Consumer
          ↓
      Kafka Broker
          ↓
      ZooKeeper
  현재(KRaft):
    Producer/Consumer
          ↓
    Kafka Broker + Controller(KRaft)
  요즘 신규 구축이면 거의 다 KRaft로 가고, 기존 운영 중인 오래된 클러스터만 ZooKeeper 기반이 아직 남아있는 편

  예전(ZooKeeper 기반):
  zookeeper-server-start.sh config/zookeeper.properties
  kafka-server-start.sh config/server.properties
  이제(KRaft 기반):
  kafka-server-start.sh config/kraft/server.properties
    오류시
      가장 흔한 원인
      server.properties에서 이거 빠짐:
      listeners=PLAINTEXT://:9092,CONTROLLER://:9093
      advertised.listeners=PLAINTEXT://localhost:9092
      controller.quorum.voters=1@localhost:9093
      process.roles=broker,controller
      node.id=1
      controller.listener.names=CONTROLLER
      listener.security.protocol.map=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT
      inter.broker.listener.name=PLAINTEXT
  Kafka 4.x라면 반드시 해야 하는 것
  1. UUID 생성
    > ./bin/kafka-storage.sh random-uuid
    > ./bin/kafka-server-start.sh config/kraft/server.properties
    > ./bin/kafka-storage.sh format -t tqxW-EpbTamxaHddwkGaEg -c ./config/server.properties
    > ./bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic quickstart-events --partitions 1
  - topic 생성
    > ./bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic quickstart-events --partitions 1
  - topic list
    > ./bin/kafka-topics.sh --bootstrap-server localhost:9092 --list 
  - topic detail
    > ./bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic quickstart-events

  - Producer : topic에 메세지를 전달
    > ./bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic quickstart-events
    Kafka 4.x부터 옵션명이 바뀌었어.
    예전:
    --broker-list
    이제:
    --bootstrap-server
  - Consumer : topic에 문자 받기
    > ./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic quickstart-events --from-beginning
  <img width="2042" height="1084" alt="image" src="https://github.com/user-attachments/assets/62b6a29d-bff2-4cfa-98c1-3096e1269319" />
  
113. Apache Kafka 사용 - Kafka Connect
  <img width="2045" height="1112" alt="image" src="https://github.com/user-attachments/assets/246a1bbf-8786-486b-a6f4-1331c6b57a11" />
  - data를 가져오는것을 Kafka Connect Source,
    data를 보내는것을 Kafka Connect Sink
  <img width="2060" height="1141" alt="image" src="https://github.com/user-attachments/assets/15cb059e-befc-40e4-b646-a53f9d84b61d" />
  <img width="2055" height="1151" alt="image" src="https://github.com/user-attachments/assets/c0c54b54-7cf4-4afd-9029-d19b206863aa" />
  
114. Orders Microservice에서 MariaDB 연동
  <img width="2049" height="1149" alt="image" src="https://github.com/user-attachments/assets/1201d1e0-41d9-47b8-bfbe-7c2c4cbb7070" />
  <img width="2073" height="1113" alt="image" src="https://github.com/user-attachments/assets/4538e77d-9a7d-4355-84f4-35979ea00ab0" />
  <img width="2077" height="1107" alt="image" src="https://github.com/user-attachments/assets/64fdc73e-220c-4a68-a757-7696fdccaa40" />
  
115. Kafka Connect 설치 ①
  - MAC
  <img width="2059" height="1152" alt="image" src="https://github.com/user-attachments/assets/d2262467-8a50-468b-bb0f-fe14ee9f70f9" />
  <img width="2055" height="1152" alt="image" src="https://github.com/user-attachments/assets/cc6515b8-acbb-4d77-a609-9a9358dd9ca0" />
  - Windows
  <img width="2059" height="1156" alt="image" src="https://github.com/user-attachments/assets/f08d0ffa-5e46-4e24-a4ea-ee72459ff7ab" />
  <img width="2056" height="1146" alt="image" src="https://github.com/user-attachments/assets/914497ca-ea6e-4a38-bf66-c33f103d9500" />
  <img width="2050" height="1151" alt="image" src="https://github.com/user-attachments/assets/c99d42bd-65d9-457c-a95a-03d8ea515f1b" />
  <img width="2055" height="1141" alt="image" src="https://github.com/user-attachments/assets/ad6d542d-2e05-4e27-b386-3fba1dea3327" />
  
117. Kafka Source Connect 사용
  <img width="2061" height="1155" alt="image" src="https://github.com/user-attachments/assets/c2a35cde-b626-423b-9b97-35a13651e763" />
  <img width="2019" height="1145" alt="image" src="https://github.com/user-attachments/assets/32afdbd3-c4ad-457b-a5db-76754c202c37" />
  <img width="2034" height="1111" alt="image" src="https://github.com/user-attachments/assets/c08e951b-3208-435d-b0d2-4880afd6fb1a" />
  <img width="2058" height="1145" alt="image" src="https://github.com/user-attachments/assets/962c6698-607f-421d-9074-0f231aec51df" />
  <img width="2051" height="1143" alt="image" src="https://github.com/user-attachments/assets/78cc3321-6937-40eb-9cbf-a24b4f15b4c1" />
  
117. Kafka Source Connect 사용
  <img width="2037" height="1131" alt="image" src="https://github.com/user-attachments/assets/8e7896e4-f893-4cca-a02d-7bd5d995046b" />
  

  
118. Kafka Sink Connect 사용
  <img width="2040" height="1095" alt="image" src="https://github.com/user-attachments/assets/6f53356f-3734-4742-aecf-a33879822431" />
  - 싱크커넥트가 하는 역할은 토픽에 전달된 데이터를 가지고 와서 자기가 사용하는 것, 즉, 사용처로 보면된다.
  <img width="2046" height="1143" alt="image" src="https://github.com/user-attachments/assets/67d68b4f-339a-4297-a9a4-f55c0cc57569" />
  <img width="2039" height="1134" alt="image" src="https://github.com/user-attachments/assets/879f0bf2-8d5a-4e35-a26d-9aaf2102d1e5" />
  <img width="2077" height="1145" alt="image" src="https://github.com/user-attachments/assets/a5a38b06-6ff2-4ff4-b69d-abf2c739d432" />
  - 직접 데이터 추가.
  > kafka_2.13-4.2.0 % ./bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic my_topic_users
  <img width="964" height="619" alt="image" src="https://github.com/user-attachments/assets/b9bead12-1f3a-482a-9ab9-45237de231a7" />
  select * from users;에는 직접 데이터 추가한게 없다.
> <img width="966" height="624" alt="image" src="https://github.com/user-attachments/assets/61a5de6c-a707-4d69-b27e-19a92b294078" />
  select * from my_topic_user; 데이터 존재한다.


# Section13_데이터 동기화를 위한 Apache Kafka의 활용_2
  119. Orders Microservice와 Catalogs Microservice에 Kafka Topic의 적용
    <img width="2055" height="1152" alt="image" src="https://github.com/user-attachments/assets/17f7bf6e-5e27-42be-ae4b-3903bc146234" />
    <img width="2044" height="1134" alt="image" src="https://github.com/user-attachments/assets/28d6acfd-323f-4174-9131-dfbfcf6cbcec" />
    - CatalogsService는 데이터를 가져오는 Consumer 역할
    - OrderService는 Topic에 전달되있는 데이터를 전달하는 Producer역할
    <img width="2058" height="1158" alt="image" src="https://github.com/user-attachments/assets/85fece32-6460-46fa-b16a-84b269cf419b" />
    <img width="2052" height="1158" alt="image" src="https://github.com/user-attachments/assets/584b629b-0145-4658-a309-4b5ae0793c6e" />
    <img width="2050" height="1155" alt="image" src="https://github.com/user-attachments/assets/d217268f-07da-43a3-8f09-5c8bbe64a6f7" />
    <img width="2053" height="1141" alt="image" src="https://github.com/user-attachments/assets/339c4535-31b6-4100-bea7-8105b5a98211" />
    <img width="2051" height="1148" alt="image" src="https://github.com/user-attachments/assets/ece4aa1c-1a10-4444-aa55-7f9f0dfd671c" />
    <img width="2057" height="1140" alt="image" src="https://github.com/user-attachments/assets/bc66fdf3-a770-42b6-a77d-07b0a51cd4e3" />
    - Consumer는 리스너가 데이터를 받음
    <img width="2052" height="1154" alt="image" src="https://github.com/user-attachments/assets/324f23dd-f7c3-4005-968e-363580ae91ff" />





    
