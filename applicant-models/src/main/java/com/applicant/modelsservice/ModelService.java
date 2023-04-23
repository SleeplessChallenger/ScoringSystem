package com.applicant.modelsservice;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ModelService {

    // TODO: put DTO further
    public void scoreApplicant(Object payload) {
        log.info("Start checking applicant = {} with AI models", 12345); // TODO: refactor later
    }
}
