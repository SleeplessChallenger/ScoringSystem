package com.applicant.consumer;

import com.applicant.modelsservice.ModelService;
import com.scoring.commons.dto.kafka.ApplicantDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ApplicantConsumer {

    private final ModelService modelService;

    @RabbitListener(queues = "${rabbitmq.queues.applicant-models}")
    public void consume(ApplicantDto payload) {
        log.info("Consumed payload with applicantId = {}", payload.getApplicantId());
        try {
            modelService.scoreApplicant(payload);
        } catch (RuntimeException ex) {
            // TODO: do something if error occurred during checking applicant
        }
    }
}
