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

  







    
