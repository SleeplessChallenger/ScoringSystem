package com.finalchecks.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

    @Bean(name = "applicantTopicBean")
    public NewTopic createApplicantTopic() {
        return TopicBuilder.name("applicant-topic").build();
    }

    @Bean(name = "depositTopicBean")
    public NewTopic createDepositTopic() {
        return TopicBuilder.name("deposit-topic").build();
    }
}
