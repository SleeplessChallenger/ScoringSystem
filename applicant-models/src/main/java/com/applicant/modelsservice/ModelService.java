package com.applicant.modelsservice;

import com.applicant.kafka.producer.KafkaApplicantProducer;
import com.scoring.commons.dto.kafka.ApplicantDto;
import com.scoring.commons.enums.Decision;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class ModelService {

    /**
     * This service does scoring with the help of AI models.
     * Further it can be incorporated with models
     */
    private final KafkaApplicantProducer applicantProducer;

    public void scoreApplicant(ApplicantDto applicant) {
        log.info("Start checking applicant = {} with AI models", applicant.getApplicantId());
        // Make some checks

        setDtoAttributes(applicant, Decision.ACCEPT);
        applicantProducer.produceMessage(applicant);
    }

    private static void setDtoAttributes(ApplicantDto applicant, Decision decision) {
        applicant.setDecisionAtTime(LocalDateTime.now());
        applicant.setDecision(decision);
    }
}
