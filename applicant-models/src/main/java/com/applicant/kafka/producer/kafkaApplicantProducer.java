package com.applicant.kafka.producer;

import com.scoring.commons.dto.kafka.ApplicantDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class kafkaApplicantProducer {

    private final KafkaTemplate<String, ApplicantDto> kafkaTemplate;

    public void produceMessage(ApplicantDto applicant) {
        log.info("Start sending message to applicant topic");
        kafkaTemplate.send("stub", applicant);
    }
}
