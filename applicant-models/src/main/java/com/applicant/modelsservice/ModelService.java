package com.applicant.modelsservice;

import com.applicant.kafka.producer.KafkaApplicantProducer;
import com.scoring.commons.dto.kafka.ApplicantDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ModelService {

    // TODO: put DTO further

    private final KafkaApplicantProducer applicantProducer;
    public void scoreApplicant(ApplicantDto payload) {
        log.info("Start checking applicant = {} with AI models", 12345); // TODO: refactor later
        applicantProducer.produceMessage(payload);
    }
}
