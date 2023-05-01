package com.applicant.kafka.producer;

import com.scoring.commons.dto.kafka.ApplicantDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaApplicantProducer {

    @Value("${spring.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, ApplicantDto> kafkaTemplate;

    public void produceMessage(ApplicantDto applicant) {
        log.info("Start sending message to applicant topic");
        kafkaTemplate.send(topic, applicant);
    }
}
