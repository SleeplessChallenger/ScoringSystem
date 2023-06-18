package com.finalchecks.controller;

import com.feign.clients.finalchecks.dto.RejectDecision;
import com.finalchecks.service.DecisionService;
import com.scoring.commons.dto.kafka.ApplicantDto;
import com.scoring.commons.dto.kafka.DepositDto;
import com.scoring.commons.enums.Decision;
import com.scoring.commons.enums.TypeIdentifier;
import com.scoring.commons.functionalinterfaces.CustomConsumer;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/v1/finalChecks")
@AllArgsConstructor
public class FinalChecksController {

    private final DecisionService decisionService;

    private final Map<String, CustomConsumer<RejectDecision>> persistenceCall = Map.of(
            "applicant", this::persistApplicant,
            "deposit", this::persistDeposit
    );

    /**
     * Transactional over method as we don't need to persist only one part - applicant and deposit
     * both must succeed. And there is only one way for application - from Kafka or from REST.
     * Hence, there won't be any duplicates
     */

    @Transactional
    @PostMapping(path = "reject/{requestId}")
    public ResponseEntity<String> acceptRejectDecision(@PathVariable("requestId") @NotBlank String requestId,
                                                       @RequestBody @Valid RejectDecision rejectDecision) {
        final String applicantId = rejectDecision.getApplicantId();
        final String depositId = rejectDecision.getDepositId();
        final String typeName = getTypeIdentifier(rejectDecision);
        persistenceCall.get(typeName).accept(rejectDecision);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(String.format("Data persisted. Applicant id = %s. Deposit id = %s", applicantId, depositId));
    }

    private static String getTypeIdentifier(RejectDecision rejectDecision) {
        final TypeIdentifier typeIdentifier = rejectDecision.getTypeIdentifier();
        return typeIdentifier.getType();
    }

    private void persistApplicant(RejectDecision rejectDecision) {
        final String applicantId = rejectDecision.getApplicantId();
        log.info("Persisting applicant = {}", applicantId);
        try {
            final String uniqueFlowId = rejectDecision.getUniqueFlowId();
            decisionService.persistApplicantDecision(ApplicantDto.builder()
                    .applicantId(applicantId)
                    .decision(Decision.REJECT)
                    .decisionAtTime(rejectDecision.getSentTime())
                    .flowUniqueId(uniqueFlowId)
                    .build());
            log.info("Applicant = {} has been persisted. FlowId = {}", applicantId, uniqueFlowId);
        } catch (RuntimeException ex) {
            log.error("Error during persisting data = {}", ex.getMessage());
            throw ex;
        }
    }

    private void persistDeposit(RejectDecision rejectDecision) {
        final String depositId = rejectDecision.getDepositId();
        log.info("Persisting deposit = {}", depositId);
        try {
            final String uniqueFlowId = rejectDecision.getUniqueFlowId();
            decisionService.persistDepositDecision(DepositDto.builder()
                    .depositId(depositId)
                    .decision(Decision.REJECT)
                    .decisionAtTime(rejectDecision.getSentTime())
                    .flowUniqueId(uniqueFlowId)
                    .build());
            log.info("Deposit = {} has been persisted. FlowId = {}", depositId, uniqueFlowId);
        } catch (RuntimeException ex) {
            log.error("Error during persisting data = {}", ex.getMessage());
            throw ex;
        }
    }
}
