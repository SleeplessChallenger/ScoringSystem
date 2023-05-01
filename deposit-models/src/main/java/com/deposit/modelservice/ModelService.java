package com.deposit.modelservice;

import com.deposit.kafka.producer.KafkaDepositProducer;
import com.scoring.commons.dto.kafka.DepositDto;
import com.scoring.commons.enums.Decision;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class ModelService {


    /**
     * This service does scoring with the help of AI models.
     * Further it can be incorporated with models
     */

    private final KafkaDepositProducer depositProducer;

    public void scoreDeposit(DepositDto deposit) {
        log.info("Start checking deposit = {} with AI models", deposit.getDepositId());
        // Score data and set decision to DTO

        setDtoAttributes(deposit, Decision.ACCEPT);
        depositProducer.produceMessage(deposit);
    }

    private static void setDtoAttributes(DepositDto deposit, Decision decision) {
        deposit.setDecisionAtTime(LocalDateTime.now());
        deposit.setDecision(decision);
    }
}
