package com.amqp.producers;

import com.scoring.commons.dto.kafka.KafkaDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MessageProducer<T extends KafkaDto> {

    private final AmqpTemplate amqpTemplate;

    // TODO: add ack from Queue
    public void publish(T payload, String exchange, String routingKey) {
        log.info("Sending payload = {} to exchange = {} using routing key = {}",
                payload.getDtoName(),
                exchange,
                routingKey
        );
        amqpTemplate.convertAndSend(exchange, routingKey, payload);
    }
}
