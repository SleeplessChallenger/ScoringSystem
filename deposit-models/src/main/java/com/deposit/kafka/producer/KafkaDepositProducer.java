package com.deposit.kafka.producer;


import com.scoring.commons.dto.kafka.DepositDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaDepositProducer {

    private final KafkaTemplate<String, DepositDto> kafkaTemplate;

    public void produceMessage(DepositDto deposit) {
        log.info("Start sending message to deposit topic");
        kafkaTemplate.send("stub", deposit);
    }
}
