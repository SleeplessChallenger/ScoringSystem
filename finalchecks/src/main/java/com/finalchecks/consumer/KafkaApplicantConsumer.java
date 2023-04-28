package com.finalchecks.consumer;

import com.scoring.commons.dto.kafka.ApplicantDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaApplicantConsumer {

    @KafkaListener(
            topics = "applicant-topic",
            containerFactory = "kafkaListenerContainerApplicantFactory",
            groupId = "deposit" // If multiple instances of our app - use this to read from the same topic/partition
    )
    public void consumeApplicantTopicMessages(ApplicantDto applicant) {

    }
}
