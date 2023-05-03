package com.initialchecks.process.service;

import com.feign.clients.finalchecks.dto.RejectDecision;
import com.feign.clients.finalchecks.feigninterfaces.FinalChecksClient;
import com.initialchecks.process.dto.FlowContext;
import com.scoring.commons.utils.UuidUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class RetryServiceInitialChecks {

    private final FinalChecksClient finalChecksClient;

    @Retryable(maxAttempts = 3,
            backoff = @Backoff(delay = 15, maxDelay = 25, multiplier = 1.5),
            retryFor = {RuntimeException.class})
    public void sendRejectDecision(FlowContext flowContext) {
        log.info("Start sending data to final checks, Retry = {}",
                RetrySynchronizationManager.getContext().getRetryCount());
        final String requestId = UuidUtils.generateUuid();
        final String applicantId = flowContext.getApplicationCheck().getApplicantId();
        final String flowUniqueId = flowContext.getFlowUniqueId();
        final String depositId = flowContext.getApplicationCheck().getDepositId();
        final RejectDecision rejectDecision = RejectDecision.builder()
                .applicantId(applicantId)
                .depositId(depositId)
                .uniqueFlowId(flowUniqueId)
                .sentTime(LocalDateTime.now())
                .build();

        finalChecksClient.sendRejectDecision(requestId, rejectDecision);
        log.info("REJECT decision about applicant = {}, deposit = {}, flow = {} has been sent",
                applicantId, depositId, flowUniqueId);
    }
}
