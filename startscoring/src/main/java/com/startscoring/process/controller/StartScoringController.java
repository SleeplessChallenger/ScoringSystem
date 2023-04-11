package com.startscoring.process.controller;

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

    // TODO: add ControllerAdvice maybe for all services at once
    // TODO: check whether we need @Valid in Controller
    // TODO: one applicant may have multiple deposits => keep this in mind
    // TODO: use liquibase
    @PostMapping
    public ResponseEntity<String> acceptScoring(@RequestBody @Valid Application application) {
        final String applicantId = application.getApplicant().getId();
        final String depositId = application.getDeposit().getDepositId();
        log.info("Accepted request from customer = {}", applicantId);

        final ApplicantEntity applicantEntity = startScoringService.registerApplicant(application.getApplicant());
        startScoringService.registerDeposit(application.getDeposit(), applicantEntity);
        startScoringService.startInitialChecks(applicantId, depositId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(String.format(
                        "Accepted request from customer = %s and deposit = %s. Now it is in process",
                        applicantId, depositId)
                );
    }

}
