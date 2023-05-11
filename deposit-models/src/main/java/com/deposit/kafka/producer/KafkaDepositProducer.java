package com.deposit.kafka.producer;


import com.scoring.commons.dto.kafka.DepositDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaDepositProducer {

    @Value("${spring.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, DepositDto> kafkaTemplate;

    public void produceMessage(DepositDto deposit) {
        try {
            log.info("Start sending message to deposit topic");
            kafkaTemplate.send(topic, deposit);
            log.info("Successfully persisted data to topic = {}", topic);
        } catch (RuntimeException ex) {
            log.error("Error during persisting data to Kafka. Topic = {}", topic);
            // TODO: add meterRegistry
        }
    }
}
