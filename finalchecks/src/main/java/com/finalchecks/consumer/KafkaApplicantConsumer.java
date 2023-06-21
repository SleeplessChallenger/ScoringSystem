package com.finalchecks.consumer;

import com.finalchecks.service.DecisionService;
import com.scoring.commons.dto.kafka.ApplicantDto;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaApplicantConsumer {

    @Value("${kafka-custom.retry-duration}")
    private int retryDuration;

    private final DecisionService decisionService;

    // TODO: add error serializer

    @KafkaListener(topics = "#{'${kafka-custom.applicant.topic}'.split(',')}",
            containerFactory = "kafkaListenerContainerApplicantFactory")
    public void consumeApplicantTopicMessages(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                              @Header(KafkaHeaders.GROUP_ID) String groupId,
                                              @Payload ApplicantDto applicant,
                                              Acknowledgment acknowledgment) {
        log.info("Consumed data from topic = {} using groupId = {}", topic, groupId);
        try {
            decisionService.persistApplicantDecision(applicant);
            acknowledgment.acknowledge();
        } catch (PersistenceException | DataAccessException ex) {
            log.error("Error during persisting applicant = {} to the database. Making retry", applicant.getApplicantId(), ex);
            acknowledgment.nack(Duration.of(retryDuration, ChronoUnit.SECONDS));
        } catch (RuntimeException ex) {
            log.error("NOT PERSISTENCE ERROR for applicant = {}", applicant.getApplicantId(), ex);
            // TODO: make dead end topic or persist data somewhere else
        }
    }
}
