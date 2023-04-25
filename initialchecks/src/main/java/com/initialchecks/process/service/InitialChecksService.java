package com.initialchecks.process.service;

import com.amqp.producer.MessageProducer;
import com.initialchecks.process.dto.ApplicationCheck;
import com.initialchecks.process.dto.FlowContext;
import com.initialchecks.process.flow.FlowProvider;
import com.initialchecks.process.flow.checksengine.CheckEngine;
import com.initialchecks.process.flow.checksflow.ApplicantFlow;
import com.initialchecks.process.flow.checksflow.CheckFlow;
import com.initialchecks.process.flow.checksflow.DepositFlow;
import com.initialchecks.process.persistence.CheckEntity;
import com.initialchecks.process.persistence.CheckRepository;
import com.initialchecks.process.utils.UuidUtils;
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

    @Value("${rabbitmq.exchanges.internal}")
    private String applicantExchange;

    @Value("${rabbitmq.routing-keys.internal-applicant}")
    private String applicantRoutingKey;

    @Value("${app.executor.threadsPerProcessor}")
    private int threadsPerProcessor;

    private ExecutorService executorService;
    private final CheckEngine checkEngine;
    private final FlowProvider flowProvider;
    private final MessageProducer messageProducer;

    private final CheckRepository checkRepository;

    @PostConstruct
    public void postConstruct() {
        this.executorService = Executors.newFixedThreadPool(
                threadsPerProcessor * Runtime.getRuntime().availableProcessors()
        );
    }

    public void checkApplication(ApplicationCheck applicationCheck) {
        final CheckFlow applicantFlow = flowProvider.getFullFlow(ApplicantFlow.FLOW_NAME);
        final CheckFlow depositFlow = flowProvider.getFullFlow(DepositFlow.FLOW_NAME);

        final FlowContext context = FlowContext.builder()
                .flowUniqueId(UuidUtils.generateUuid())
                .applicationCheck(applicationCheck)
                .build();

        executorService.execute(() -> checkEngine.startEngine(applicantFlow, context));
        executorService.execute(() -> checkEngine.startEngine(depositFlow, context));

        // If checks haven't ended with Reject or no error => send data to RabbitMQ
        sendDataToQueue(applicationCheck);
        log.info("Data has been sent to RabbitMQ exchange = {} using routing key = {}",
                applicantExchange, applicantRoutingKey);
    }

    private void sendDataToQueue(ApplicationCheck applicationCheck) {
        messageProducer.publish(applicationCheck, applicantExchange, applicantRoutingKey);
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
