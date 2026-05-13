package com.dogmeeting.catalogservice.messagequeue;

// CatalogEntity(JPA Entity) import
// DB의 catalog 테이블과 매핑되는 엔티티 클래스
import com.dogmeeting.catalogservice.jpa.CatalogEntity;
// CatalogRepository(JPA Repository) import
// DB 조회/저장 등을 처리하는 Repository
import com.dogmeeting.catalogservice.jpa.CatalogRepository;
// JSON 파싱 예외 처리용
import com.fasterxml.jackson.core.JsonProcessingException;
// JSON -> Map 변환 시 타입 정보를 전달하기 위한 클래스
import com.fasterxml.jackson.core.type.TypeReference;
// Jackson JSON 변환 객체
import com.fasterxml.jackson.databind.ObjectMapper;
// 로그 출력용 Lombok 어노테이션
import lombok.extern.slf4j.Slf4j;
// Spring 의존성 주입 어노테이션
import org.springframework.beans.factory.annotation.Autowired;
// Kafka 메시지를 수신하기 위한 어노테이션
import org.springframework.kafka.annotation.KafkaListener;
// Spring Bean 등록용 어노테이션
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

// Spring Service Bean 등록
@Service
// log.info(), log.error() 등을 사용 가능하게 해줌
@Slf4j
public class KafkaConsumer {

    // DB 접근용 Repository 객체
    CatalogRepository catalogRepository;

    // 생성자 주입 방식
    // Spring이 CatalogRepository Bean을 자동 주입함
    @Autowired
    public KafkaConsumer(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    // Kafka 토픽 메시지 수신
    // example-catalog-topic 토픽에 메시지가 들어오면 자동 실행됨
    @KafkaListener(topics = "example-catalog-topic")
    public void updateQty(String kafkaMessage) {

        // Kafka에서 받은 메시지 로그 출력
        log.info("Kafka Message: -> {}", kafkaMessage);

        // JSON 데이터를 저장할 Map 객체 생성
        Map<Object, Object> map = new HashMap<>();

        // JSON 파싱용 ObjectMapper 생성
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Kafka 메시지(JSON 문자열)를 Map 형태로 변환
            // 예:
            // {
            //   "productId":"CATALOG-001",
            //   "qty":2
            // }
            //
            // =>
            // map.get("productId")
            // map.get("qty")
            //
            map = mapper.readValue( kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonProcessingException ex) {
            // JSON 파싱 실패 시 예외 출력
            ex.printStackTrace();
        }

        // productId 기준으로 상품 조회
        CatalogEntity catalogEntity = catalogRepository.findByProductId((String) map.get("productId"));

        // 상품이 존재하면 재고 차감
        if (catalogEntity != null) {
            // 현재 재고 - 주문 수량(qty)
            catalogEntity.setStock(catalogEntity.getStock() - (Integer) map.get("qty"));

            // 변경된 재고 DB 저장
            catalogRepository.save(catalogEntity);
        }
    }
}