package com.startscoring.process.controller;

import com.scoring.commons.exceptions.InitialChecksException;
import com.startscoring.process.dto.Application;
import com.startscoring.process.persistence.customer.ApplicantEntity;
import com.startscoring.process.service.StartScoringService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/startScoring")
@AllArgsConstructor
public class StartScoringController {

    private final StartScoringService startScoringService;

    // TODO: use liquibase
    @PostMapping
    public ResponseEntity<String> acceptScoring(@RequestBody @Valid Application application) {
        final String applicantId = application.getApplicant().getId();
        final String depositId = application.getDeposit().getDepositId();
        log.info("Accepted request from customer = {} and depositId = {}", applicantId, depositId);

        final ApplicantEntity applicantEntity = startScoringService.registerApplicant(application.getApplicant());
        startScoringService.registerDeposit(application.getDeposit(), applicantEntity);

        try {
            startScoringService.startInitialChecks(applicantId, depositId);
        } catch (RuntimeException ex) {
            log.error("Error in InitialChecks microservice = {}", ex.getMessage());
            // If error during persistence - also caught in Controller Advice
            startScoringService.updateInteractions(applicantId, depositId);
            throw new InitialChecksException(ex.getMessage(), ex);
        }

        log.info("Sent data to initial checks microservices. ApplicantId = {}, depositId = {}", applicantId, depositId);
        startScoringService.updateInteractions(applicantId, depositId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(String.format(
                        "Accepted request from customer = %s and deposit = %s. Now it is in process",
                        applicantId, depositId)
                );
    }
}
