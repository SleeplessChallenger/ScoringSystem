package com.finalchecks.service;

import com.finalchecks.dto.DecisionResultDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RetryServiceFinalChecks {

    private final RestTemplate restTemplate;

    // Further it can be customized with particular exceptions
    @Retryable(maxAttempts = 3,
            backoff = @Backoff(delay = 15, maxDelay = 25, multiplier = 1.5),
            retryFor = {RuntimeException.class})
    public void sendToTheSystemBasedOnCondition(List<DecisionResultDto> decisionResults, String endpoint) {
        log.info("Sending request to endpoint = {}. Retry count = {}",
                endpoint, RetrySynchronizationManager.getContext().getRetryCount());
        restTemplate.postForEntity(endpoint, decisionResults, Object.class);
        log.info("Sent request about all available tasks");
    }
}
