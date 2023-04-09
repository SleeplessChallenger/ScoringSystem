package com.startscoring.process.controller;

import com.startscoring.process.dto.Application;
import com.startscoring.process.service.StartScoringService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
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
    public ResponseEntity<?> acceptScoring(@RequestBody @Valid Application application) {
        final String customerId = application.getApplicant().getId();
        log.info("Accepted request from customer = {}", customerId);
        startScoringService.registerApplicant(application.getApplicant());
        startScoringService.registerDeposit(application.getDeposit());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(String.format("Accepted request from customer = %s", customerId));
    }

}
