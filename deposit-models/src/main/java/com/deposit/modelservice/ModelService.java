package com.deposit.modelservice;

import com.deposit.kafka.producer.KafkaDepositProducer;
import com.scoring.commons.dto.kafka.DepositDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ModelService {

    private final KafkaDepositProducer depositProducer;

    // TODO: put DTO further
    public void scoreDeposit(DepositDto payload) {
        log.info("Start checking deposit = {} with AI models", 12345); // TODO: refactor later

        // Score data
        depositProducer.produceMessage(payload);
    }
}
