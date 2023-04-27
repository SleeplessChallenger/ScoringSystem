package com.applicant.consumer;

import com.applicant.modelsservice.ModelService;
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
    public void consume(Object payload) {
        // TODO: create some DTO which will have all the data
        log.info("Consumed payload with applicantId = {}", 12345); // TODO: fix later
        try {
            modelService.scoreApplicant(payload);
        } catch (RuntimeException ex) {
            // TODO: do something if error occurred during checking applicant
        }
    }
}
