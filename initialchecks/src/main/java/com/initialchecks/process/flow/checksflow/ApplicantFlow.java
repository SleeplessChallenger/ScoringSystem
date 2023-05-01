package com.initialchecks.process.flow.checksflow;

import com.amqp.producers.MessageProducer;
import com.initialchecks.process.beanloader.BeanLoader;
import com.initialchecks.process.dto.FlowContext;
import com.initialchecks.process.flow.checkactions.applicantactions.IncomeAction;
import com.initialchecks.process.flow.checkactions.applicantactions.IncomeErrorAction;
import com.initialchecks.process.flow.checkactions.applicantactions.MoratoriumAction;
import com.initialchecks.process.flow.checkactions.applicantactions.MoratoriumErrorAction;
import com.scoring.commons.dto.kafka.ApplicantDto;
import kotlin.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.initialchecks.process.utils.BeanLoaderUtils.createActions;

@Slf4j
@Component
public class ApplicantFlow extends CheckFlow {
    public static final String FLOW_NAME = "APPLICANT_FLOW";
    private static final List<Pair<String, String>> FLOW_ACTIONS = List.of(
            new Pair<>(MoratoriumAction.ACTION_NAME, MoratoriumErrorAction.ACTION_NAME),
            new Pair<>(IncomeAction.ACTION_NAME, IncomeErrorAction.ACTION_NAME)
    );

    @Value("${rabbitmq.exchanges.internal}")
    private String applicantExchange;

    @Value("${rabbitmq.routing-keys.internal-applicant}")
    private String applicantRoutingKey;

    private final MessageProducer<ApplicantDto> messageProducer;

    public ApplicantFlow(BeanLoader beanLoader, MessageProducer<ApplicantDto> messageProducer) {
        super(FLOW_NAME, createActions(beanLoader, FLOW_ACTIONS));
        this.messageProducer = messageProducer;
    }

    public void sendDataToQueue(FlowContext flowContext) {
        log.info("Data has been sent to RabbitMQ exchange = {} using routing key = {}. Flow = {}",
                applicantExchange, applicantRoutingKey, flowContext.getFlowName());
        final ApplicantDto applicant = ApplicantDto.builder()
                .flowUniqueId(flowContext.getFlowUniqueId())
                .applicantId(flowContext.getApplicationCheck().getApplicantId())
                .build();
        messageProducer.publish(applicant, applicantExchange, applicantRoutingKey);
    }

    @Override
    public String getFlowName() {
        // this name will be used to get Flow from Map
        return FLOW_NAME;
    }
}
