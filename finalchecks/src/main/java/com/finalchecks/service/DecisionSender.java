package com.finalchecks.service;

import com.finalchecks.dto.DecisionResultDto;
import com.finalchecks.exceptions.ScheduledUpdateException;
import com.finalchecks.persistence.applicant.ApplicantDecisionRepository;
import com.finalchecks.persistence.deposit.DepositDecisionRepository;
import com.scoring.commons.enums.Decision;
import com.scoring.commons.enums.SentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class DecisionSender {

    @Value("${services.concrete-decision}")
    private String resultSystem;
    @Value("${services.fraud-analyst}")
    private String fraudAnalyst;

    private final DepositDecisionRepository depositDecisionRepository;
    private final ApplicantDecisionRepository applicantDecisionRepository;

    private final RetryServiceFinalChecks retryServiceFinalChecks;


    @Scheduled(fixedDelay = 15, timeUnit = TimeUnit.MINUTES)
    public void sendDecision() {
        // method below will be called each time within a separate transaction
        final List<DecisionResultDto> decisionResults = depositDecisionRepository.findNotSentApplications(
                SentStatus.NOT_SENT.name());
        final LocalDateTime now = LocalDateTime.now();

        if (!decisionResults.isEmpty()) {
            try {
                retryServiceFinalChecks.sendToTheSystemBasedOnCondition(decisionResults.stream()
                                .filter(data -> data.getApplicantDecision().equals(Decision.MANUAL.name()) &&
                                        data.getDepositDecision().equals(Decision.MANUAL.name())
                                ).toList(),
                        fraudAnalyst);

                retryServiceFinalChecks.sendToTheSystemBasedOnCondition(decisionResults.stream()
                                .filter(data -> !data.getApplicantDecision().equals(Decision.MANUAL.name()) &&
                                        data.getDepositDecision().equals(Decision.MANUAL.name())
                                ).toList(),
                        resultSystem);

                final List<String> decisionSystemIds = decisionResults.stream()
                        .map(DecisionResultDto::getDepositSystemId)
                        .toList();

                final List<String> applicantSystemIds = decisionResults.stream()
                        .map(DecisionResultDto::getApplicantSystemId)
                        .toList();

                // both methods below will also be executed in new transactions
                updateSentData(
                        ids -> depositDecisionRepository.updateDepositStatus(SentStatus.SENT.name(), ids),
                        decisionSystemIds
                );
                updateSentData(
                        ids -> applicantDecisionRepository.updateApplicantStatus(SentStatus.SENT.name(), ids),
                        applicantSystemIds
                );
                log.info("Sent data has been updated");
            } catch (RuntimeException ex) {
                log.error("Error during sending data to service = {}", ex.getMessage(), ex);
            }
        } else {
            log.info("No applications for sending = {}", now);
        }
    }

    private void updateSentData(Consumer<List<String>> consumer, List<String> systemIds) {
        try {
            consumer.accept(systemIds);
        } catch (RuntimeException ex) {
            log.error("Error during persisting");
            throw new ScheduledUpdateException(
                    String.format("Error during saving data in Scheduled service = %s", ex.getMessage()),
                    ex);
        }
    }
}
