package com.applicant.kafka.configuration;

import com.scoring.commons.dto.kafka.ApplicantDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaApplicantConfiguration {

    @Value("${spring.bootstrap-servers}")
    private String bootstrapServers;


    private Map<String, Object> createProducerConfiguration() {
        final Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return config;
    }

    @Bean
    public ProducerFactory<String, ApplicantDto> createProducerFactory() {
        // It will create Producer instances
        return new DefaultKafkaProducerFactory<>(createProducerConfiguration());
    }

    @Bean
    public KafkaTemplate<String, ApplicantDto> createKafkaTemplate(ProducerFactory<String, ApplicantDto> createProducerFactory) {
        // This will send messages
        return new KafkaTemplate<>(createProducerFactory);
    }
}
