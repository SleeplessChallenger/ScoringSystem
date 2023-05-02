package com.finalchecks.controller;

import com.feign.clients.finalchecks.dto.RejectDecision;
import com.finalchecks.service.DecisionService;
import com.scoring.commons.dto.kafka.ApplicantDto;
import com.scoring.commons.dto.kafka.DepositDto;
import com.scoring.commons.enums.Decision;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/finalChecks")
@AllArgsConstructor
public class FinalChecksController {

    private final DecisionService decisionService;

    @PostMapping(path = "reject/{requestId}")
    public ResponseEntity<String> acceptRejectDecision(@PathVariable("requestId") @NotBlank String requestId,
                                                       @RequestBody @Valid RejectDecision rejectDecision) {
        final String applicantId = rejectDecision.getApplicantId();
        final String depositId = rejectDecision.getDepositId();
        final String flowId = rejectDecision.getUniqueFlowId();
        log.info("Accepted REJECT request from applicant = {} and deposit = {}. Flow id = {}",
                applicantId, depositId, flowId);

        try {
            // Without successful applicant no way to save deposit
            persistApplicant(applicantId, rejectDecision);
            persistDeposit(depositId, rejectDecision);
        } catch (RuntimeException ex) {
            log.error("Error during persisting data = {}", ex.getMessage(), ex);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }

        log.info("Data about applicant = {}, deposit = {} in flow = {} has been persisted",
                applicantId, depositId, flowId);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(String.format("Data persisted. Applicant id = %s. Deposit id = %s", applicantId, depositId));
    }

    private void persistApplicant(String applicantId, RejectDecision rejectDecision) {
        log.info("Persisting applicant = {}", applicantId);
        try {
            decisionService.persistApplicantDecision(ApplicantDto.builder()
                    .applicantId(applicantId)
                    .decision(Decision.REJECT)
                    .decisionAtTime(rejectDecision.getSentTime())
                    .flowUniqueId(rejectDecision.getUniqueFlowId())
                    .build());
        } catch (RuntimeException ex) {
            log.error("Error during persisting data = {}", ex.getMessage());
            throw ex;
        }
    }

    private void persistDeposit(String depositId, RejectDecision rejectDecision) {
        log.info("Persisting deposit = {}", depositId);
        try {
            decisionService.persistDepositDecision(DepositDto.builder()
                    .depositId(depositId)
                    .decision(Decision.REJECT)
                    .decisionAtTime(rejectDecision.getSentTime())
                    .flowUniqueId(rejectDecision.getUniqueFlowId())
                    .build());
        } catch (RuntimeException ex) {
            log.error("Error during persisting data = {}", ex.getMessage());
            throw ex;
        }
    }
}