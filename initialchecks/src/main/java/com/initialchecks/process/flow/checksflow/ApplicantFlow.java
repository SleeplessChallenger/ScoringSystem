package com.initialchecks.process.flow.checksflow;

import com.amqp.producer.MessageProducer;
import com.initialchecks.process.beanloader.BeanLoader;
import com.initialchecks.process.dto.FlowContext;
import com.initialchecks.process.flow.checkactions.applicantactions.IncomeAction;
import com.initialchecks.process.flow.checkactions.applicantactions.IncomeErrorAction;
import com.initialchecks.process.flow.checkactions.applicantactions.MoratoriumAction;
import com.initialchecks.process.flow.checkactions.applicantactions.MoratoriumErrorAction;
import kotlin.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.initialchecks.process.utils.BeanLoaderUtils.*;

import java.util.List;

@Slf4j
@Component
public class ApplicantFlow extends CheckFlow {
    public static final String FLOW_NAME = "APPLICANT_FLOW";
    private static final List<Pair<String, String>> FLOW_ACTIONS = List.of(
            new Pair<>(MoratoriumAction.ACTION_NAME, MoratoriumErrorAction.ACTION_NAME),
            new Pair<>(IncomeAction.ACTION_NAME, IncomeErrorAction.ACTION_NAME)
    );

    // FIXME: fine-tune
    @Value("${rabbitmq.exchanges.internal}")
    private String applicantExchange;

    @Value("${rabbitmq.routing-keys.internal-applicant}")
    private String applicantRoutingKey;

    private final MessageProducer messageProducer;

    public ApplicantFlow(BeanLoader beanLoader, MessageProducer messageProducer) {
        super(FLOW_NAME, createActions(beanLoader, FLOW_ACTIONS));
        this.messageProducer = messageProducer;
    }

    public void sendDataToQueue(FlowContext flowContext) {
        log.info("Data has been sent to RabbitMQ exchange = {} using routing key = {}. Flow = {}",
                applicantExchange, applicantRoutingKey, flowContext.getFlowName());
        messageProducer.publish(flowContext, applicantExchange, applicantRoutingKey);
    }

    @Override
    public String getFlowName() {
        // this name will be used to get Flow from Map
        return FLOW_NAME;
    }
}
