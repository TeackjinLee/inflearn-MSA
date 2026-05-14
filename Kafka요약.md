# Kafka 실행 및 KRaft 구성 정리

## Kafka 서버 구동

```bash
./bin/kafka-server-start.sh ./config/server.properties
```

---

# Kafka 구조 변화

## 예전 구조 (ZooKeeper 기반)

```text
Producer/Consumer
        ↓
    Kafka Broker
        ↓
     ZooKeeper
```

### 실행 방법

```bash
zookeeper-server-start.sh config/zookeeper.properties
kafka-server-start.sh config/server.properties
```

---

## 현재 구조 (KRaft 기반)

```text
Producer/Consumer
        ↓
Kafka Broker + Controller(KRaft)
```

- 요즘 신규 구축은 대부분 KRaft 기반 사용
- 기존 오래된 운영 클러스터만 ZooKeeper 기반이 남아있는 편

### 실행 방법

```bash
kafka-server-start.sh config/kraft/server.properties
```

---

# KRaft 실행 시 자주 발생하는 오류

## 가장 흔한 원인

`server.properties` 또는 `kraft/server.properties` 에 아래 설정이 누락된 경우

```properties
listeners=PLAINTEXT://:9092,CONTROLLER://:9093
advertised.listeners=PLAINTEXT://localhost:9092

controller.quorum.voters=1@localhost:9093

process.roles=broker,controller
node.id=1

controller.listener.names=CONTROLLER

listener.security.protocol.map=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT

inter.broker.listener.name=PLAINTEXT
```

---

# Kafka 4.x 필수 작업

## 1. UUID 생성

```bash
./bin/kafka-storage.sh random-uuid
```

예시 결과:

```text
tqxW-EpbTamxaHddwkGaEg
```

---

## 2. Storage Format 수행

```bash
./bin/kafka-storage.sh format \
-t tqxW-EpbTamxaHddwkGaEg \
-c ./config/server.properties
```

---

## 3. Kafka 서버 실행

```bash
./bin/kafka-server-start.sh config/kraft/server.properties
```

---

# Topic 관리

## Topic 생성

```bash
./bin/kafka-topics.sh \
--bootstrap-server localhost:9092 \
--create \
--topic quickstart-events \
--partitions 1
```

---

## Topic 목록 조회

```bash
./bin/kafka-topics.sh \
--bootstrap-server localhost:9092 \
--list
```

---

## Topic 상세 조회

```bash
./bin/kafka-topics.sh \
--bootstrap-server localhost:9092 \
--describe \
--topic quickstart-events
```

---

# Producer / Consumer

## Producer

- Topic 에 메시지를 전달하는 역할

```bash
./bin/kafka-console-producer.sh \
--bootstrap-server localhost:9092 \
--topic quickstart-events
```

### Kafka 4.x 변경 사항

예전 옵션:

```bash
--broker-list
```

현재 옵션:

```bash
--bootstrap-server
```

---

## Consumer

- Topic 의 메시지를 수신하는 역할

```bash
./bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic quickstart-events \
--from-beginning
```

---

# 전체 흐름 예시

## 1. Kafka 실행

```bash
./bin/kafka-server-start.sh config/kraft/server.properties
```

## 2. Topic 생성

```bash
./bin/kafka-topics.sh \
--bootstrap-server localhost:9092 \
--create \
--topic quickstart-events \
--partitions 1
```

## 3. Producer 실행

```bash
./bin/kafka-console-producer.sh \
--bootstrap-server localhost:9092 \
--topic quickstart-events
```

메시지 입력:

```text
hello kafka
```

## 4. Consumer 실행

```bash
./bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic quickstart-events \
--from-beginning
```

출력:

```text
hello kafka
```
