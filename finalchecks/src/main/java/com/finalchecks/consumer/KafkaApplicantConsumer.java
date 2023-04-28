package com.finalchecks.consumer;

import com.scoring.commons.dto.kafka.ApplicantDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaApplicantConsumer {

    @Value("${kafka-custom.deposit.topic}")
    private String topicName;

    @Value("${kafka-custom.deposit.groupId}")
    private String groupId;

    @KafkaListener(
            topics = "${'${kafka-custom.applicant.topic}'.split(','}",
            containerFactory = "kafkaListenerContainerApplicantFactory",
            groupId = "#{'${kafka-custom.applicant.groupId}'.split(',')}" // If multiple instances of our app - use this to read from the same topic/partition
    )
    public void consumeApplicantTopicMessages(ApplicantDto applicant) {
        log.info("Consumed data from topic = {} using groupId = {}", topicName, groupId);
    }
}
