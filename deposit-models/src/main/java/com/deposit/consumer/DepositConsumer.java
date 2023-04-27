package com.deposit.consumer;

import com.deposit.modelservice.ModelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class DepositConsumer {

    private final ModelService modelService;

    @RabbitListener(queues = "${rabbitmq.queues.deposit-models}")
    public void consume(Object payload) {
        log.info("Consumed payload with depositId = {}", 12345);
        try {
            modelService.scoreDeposit(payload);
        } catch (RuntimeException ex) {

        }
    }
}
