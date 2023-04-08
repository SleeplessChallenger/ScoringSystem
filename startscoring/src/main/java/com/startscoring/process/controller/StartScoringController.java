package com.startscoring.process.controller;

import com.startscoring.process.dto.ApplicantData;
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
    @PostMapping
    public ResponseEntity<?> acceptScoring(@RequestBody @Valid ApplicantData applicantData) {
        final String customerId = applicantData.getCustomer().getId();
        log.info("Accepted request from customer = {}", customerId);
        startScoringService.registerApplicant(applicantData);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(String.format("Accepted request from customer = %s", customerId));
    }

}
