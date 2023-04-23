package com.initialchecks.process.service;

import com.amqp.producer.MessageProducer;
import com.initialchecks.process.dto.ApplicationCheck;
import com.initialchecks.process.flow.FlowProvider;
import com.initialchecks.process.flow.checksengine.CheckEngine;
import com.initialchecks.process.flow.checksflow.ApplicantFlow;
import com.initialchecks.process.flow.checksflow.CheckFlow;
import com.initialchecks.process.flow.checksflow.DepositFlow;
import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void postConstruct() {
        this.executorService = Executors.newFixedThreadPool(
                threadsPerProcessor * Runtime.getRuntime().availableProcessors()
        );
    }

    public void checkApplication(ApplicationCheck applicationCheck) {
        final CheckFlow applicantFlow = flowProvider.getFullFlow(ApplicantFlow.FLOW_NAME);
        final CheckFlow depositFlow = flowProvider.getFullFlow(DepositFlow.FLOW_NAME);

        executorService.execute(() -> checkEngine.startEngine(applicantFlow, applicationCheck));
        executorService.execute(() -> checkEngine.startEngine(depositFlow, applicationCheck));

        // TODO: if checks haven't worked - throw data to the RabbitMQ
    }

    private void sendDataToQueue() {
        // TODO: send data to RabbitMQ
        messageProducer.publish("stub", applicantExchange, applicantRoutingKey);
    }
}
