package com.initialchecks.process.controller;

import com.initialchecks.process.dto.ApplicationCheck;
import com.initialchecks.process.service.InitialChecksService;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("api/v1/initialChecks")
@AllArgsConstructor
public class InitialChecksController {

    private final InitialChecksService initialChecksService;

    @PostMapping(path = "{applicantId}/{depositId}")
    public ResponseEntity<String> checkApplication(@PathVariable @NotBlank String applicantId,
                                                   @PathVariable @NotBlank String depositId) {
        log.info("Started checking application with userId = {} and depositId = {}", applicantId, depositId);

        // Accept request from Tomcat and give to Java Executor. Don't wait and give response
        initialChecksService.checkApplication(ApplicationCheck.builder()
                .createdAt(LocalDateTime.now())
                .applicantId(applicantId)
                .depositId(depositId)
                .build()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(String.format(
                        "Application of user = %s, deposit = %s has been accepted and now is being processed",
                        applicantId, depositId)
                );
    }
}
