package com.finalchecks.consumer;

import com.finalchecks.service.DecisionService;
import com.scoring.commons.dto.kafka.DepositDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaDepositConsumer {

    @Value("${kafka-custom.deposit.topic}")
    private String topicName;

    @Value("${kafka-custom.deposit.groupId}")
    private String groupId;

    private final DecisionService decisionService;

    @KafkaListener(
            topics = "#{'${kafka-custom.deposit.topic}'.split(',')}",
            containerFactory = "kafkaListenerContainerDepositFactory",
            groupId = "#{'${kafka-custom.deposit.groupId}'.split(','}"
    )
    public void consumeDecisionTopicMessages(DepositDto deposit) {
        log.info("Consumed data from topic = {} using groupId = {}", topicName, groupId);
        decisionService.persistDepositDecision(deposit);
    }
}
