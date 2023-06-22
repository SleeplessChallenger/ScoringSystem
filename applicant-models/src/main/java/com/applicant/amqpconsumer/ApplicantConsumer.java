package com.applicant.amqpconsumer;

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
    public void consume(ApplicantDto applicant) {
        final String applicantId = applicant.getApplicantId();
        log.info("Consumed applicant with applicantId = {}", applicantId);
        try {
            modelService.scoreApplicant(applicant);
        } catch (RuntimeException ex) {
            log.error("ERROR during scoring applicant = {}", applicantId);
            // TODO: do something if error occurred during checking applicant
        }
    }
}
