package com.applicant.amqpconfiguration;

import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ApplicantConfiguration {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.applicant-models}")
    private String applicantQueue;

    @Value("${rabbitmq.routing-keys.internal-applicant}")
    private String routingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        // here TopicExchange is just one type of Exchange
        return new TopicExchange(internalExchange);
    }

    @Bean
    public Queue applicantQueue() {
        // make queue survive the server restart
        return new Queue(applicantQueue, true);
    }

    @Bean
    public Binding applicantBinding() {
        return BindingBuilder
                .bind(applicantQueue())
                .to(internalTopicExchange())
                .with(routingKey);
    }
}
