package com.initialchecks.process.controller;

import com.feign.clients.initialchecks.ApplicationRequest;
import com.initialchecks.process.dto.ApplicationCheck;
import com.initialchecks.process.service.InitialChecksService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("api/v1/initialChecks")
@AllArgsConstructor
public class InitialChecksController {

    private final InitialChecksService initialChecksService;

    @PostMapping(path = "{requestId}")
    public ResponseEntity<String> checkApplication(@PathVariable @NotBlank String requestId,
                                                   @RequestBody @Valid ApplicationRequest applicationRequest) {
        final String applicantId = applicationRequest.getApplicantId();
        final String depositId = applicationRequest.getDepositId();
        log.info("Request = {}. Started checking application with userId = {} and depositId = {}",
                requestId, applicantId, depositId);

        final LocalDateTime now = LocalDateTime.now();
        final ApplicationCheck applicationCheck = ApplicationCheck.builder()
                .createdAt(now)
                .applicantId(applicantId)
                .depositId(depositId)
                .build();

        initialChecksService.persistData(applicationCheck);

        // Accept request from Tomcat and give to Java Executor. Don't wait and give response
        initialChecksService.checkApplication(applicationCheck
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(String.format(
                        "Application of user = %s, deposit = %s has been accepted and now is being processed",
                        applicantId, depositId)
                );
    }
}
