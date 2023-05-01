package com.finalchecks.configuration;

import com.scoring.commons.dto.kafka.ApplicantDto;
import com.scoring.commons.dto.kafka.DepositDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfiguration {

    // FIXME: Read docs for partitioning and so on
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String, Object> createConsumerDepositConfig() {
        final Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return config;
    }

    public Map<String, Object> createConsumerApplicantConfig() {
        final Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return config;
    }

    @Bean(name = "depositBean")
    public ConsumerFactory<String, DepositDto> createConsumerDepositFactory() {
        final JsonDeserializer<DepositDto> jsonDeserializer = new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages("com.scoring.commons");
        return new DefaultKafkaConsumerFactory<>(createConsumerDepositConfig(),
                new StringDeserializer(),
                jsonDeserializer);
    }

    @Bean(name = "applicantBean")
    public ConsumerFactory<String, ApplicantDto> createConsumerApplicantFactory() {
        final JsonDeserializer<ApplicantDto> jsonDeserializer = new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages("com.scoring.commons");
        return new DefaultKafkaConsumerFactory<>(createConsumerApplicantConfig(),
                new StringDeserializer(),
                jsonDeserializer);
    }

    @Bean(name = "kafkaListenerContainerDepositFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, DepositDto>>
    createKafkaDepositListener(ConsumerFactory<String, DepositDto> createConsumerDepositFactory) {
        final ConcurrentKafkaListenerContainerFactory<String, DepositDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createConsumerDepositFactory);
        return factory;
    }

    @Bean(name = "kafkaListenerContainerApplicantFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, ApplicantDto>>
    createKafkaApplicantListener(ConsumerFactory<String, ApplicantDto> createConsumerApplicantFactory) {
        final ConcurrentKafkaListenerContainerFactory<String, ApplicantDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createConsumerApplicantFactory);
        return factory;
    }
}
