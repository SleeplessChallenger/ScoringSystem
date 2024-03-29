package com.initialchecks.process.controller;

import com.feign.clients.initialchecks.dto.ApplicationRequest;
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
        log.info("Accepted request = {} in Initial-Checks", requestId);
        final String applicantId = applicationRequest.getApplicantId();
        final String depositId = applicationRequest.getDepositId();

        final ApplicationCheck applicationCheck = ApplicationCheck.builder()
                .createdAt(LocalDateTime.now())
                .applicantId(applicantId)
                .depositId(depositId)
                .build();

        try {
            initialChecksService.persistData(applicationCheck);
            // Accept request from Tomcat and give to Java Executor. Don't wait and give response
            initialChecksService.checkApplication(applicationCheck);
        } catch (RuntimeException ex) {
            log.error("ERROR during persisting application: applicant = {}, deposit = {}", applicantId, depositId);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(String.format(
                        "Application of user = %s, deposit = %s has been accepted and now is being processed",
                        applicantId, depositId)
                );
    }
}
