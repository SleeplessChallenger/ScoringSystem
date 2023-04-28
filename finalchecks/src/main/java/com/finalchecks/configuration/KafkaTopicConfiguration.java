package com.finalchecks.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    @Value("${kafka-custom.deposit.topic}")
    private String depositTopicName;

    @Value("${kafka-custom.applicant.topic}")
    private String applicantTopicName;

    @Bean(name = "applicantTopicBean")
    public NewTopic createApplicantTopic() {
        return TopicBuilder.name(applicantTopicName).build();
    }

    @Bean(name = "depositTopicBean")
    public NewTopic createDepositTopic() {
        return TopicBuilder.name(depositTopicName).build();
    }
}
