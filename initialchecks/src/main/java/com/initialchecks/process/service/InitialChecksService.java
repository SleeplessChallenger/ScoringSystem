package com.initialchecks.process.service;

import com.initialchecks.process.dto.ApplicationCheck;
import com.initialchecks.process.flow.FlowProvider;
import com.initialchecks.process.flow.checksengine.CheckEngine;
import com.initialchecks.process.flow.checksflow.ApplicantFlow;
import com.initialchecks.process.flow.checksflow.CheckFlow;
import com.initialchecks.process.flow.checksflow.DepositFlow;
import com.initialchecks.process.persistence.CheckEntity;
import com.initialchecks.process.persistence.CheckRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitialChecksService {

    @Value("${app.executor.threadsPerProcessor}")
    private int threadsPerProcessor;

    private final ApplicantFlow applicantFlow;
    private final DepositFlow depositFlow;

    private ExecutorService executorService;
    private final CheckEngine checkEngine;
    private final FlowProvider flowProvider;

    private final CheckRepository checkRepository;

    @PostConstruct
    public void postConstruct() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        log.info("Processors available = {}", availableProcessors);
        this.executorService = Executors.newFixedThreadPool(
                threadsPerProcessor * availableProcessors);
    }

    public void checkApplication(ApplicationCheck applicationCheck) {
        final CheckFlow applicantFlow = flowProvider.getFullFlow(ApplicantFlow.FLOW_NAME);
        final CheckFlow depositFlow = flowProvider.getFullFlow(DepositFlow.FLOW_NAME);

        executorService.execute(() -> checkEngine.startEngine(applicantFlow, applicationCheck));
        executorService.execute(() -> checkEngine.startEngine(depositFlow, applicationCheck));
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void persistData(ApplicationCheck applicationCheck) {
        final String applicantId = applicationCheck.getApplicantId();
        final String depositId = applicationCheck.getDepositId();

        final CheckEntity checkEntity = CheckEntity.builder()
                .applicantId(applicantId)
                .depositId(depositId)
                .createdAt(applicationCheck.getCreatedAt())
                .build();

        checkRepository.save(checkEntity);
        log.info("Saved check entity with applicantId = {} and depositId = {}", applicantId, depositId);
    }
}
