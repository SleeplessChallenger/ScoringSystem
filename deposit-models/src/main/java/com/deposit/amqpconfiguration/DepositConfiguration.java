package com.deposit.amqpconfiguration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DepositConfiguration {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.deposit-models}")
    private String depositQueue;

    @Value(("${rabbitmq.routing-keys.internal-deposit}"))
    private String routingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(internalExchange);
    }

    @Bean
    public Queue depositQueue() {
        return new Queue(depositQueue, true);
    }

    @Bean
    public Binding depositBinding() {
        return BindingBuilder
                .bind(depositQueue())
                .to(internalTopicExchange())
                .with(routingKey);
    }
}
