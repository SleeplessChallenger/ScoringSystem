package com.initialchecks.process.flow.checksflow;

import com.amqp.producer.MessageProducer;
import com.initialchecks.process.beanloader.BeanLoader;
import com.initialchecks.process.dto.FlowContext;
import com.initialchecks.process.flow.checkactions.depositactions.ConditionsAction;
import com.initialchecks.process.flow.checkactions.depositactions.ConditionsErrorAction;
import com.initialchecks.process.flow.checkactions.depositactions.ReservedDepositAction;
import com.initialchecks.process.flow.checkactions.depositactions.ReservedDepositErrorAction;
import kotlin.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.initialchecks.process.utils.BeanLoaderUtils.createActions;

@Slf4j
@Component
public class DepositFlow extends CheckFlow {

    public static final String FLOW_NAME = "DEPOSIT_FLOW";

    private static final List<Pair<String, String>> FLOW_ACTIONS = List.of(
            new Pair<>(ReservedDepositAction.ACTION_NAME, ReservedDepositErrorAction.ACTION_NAME),
            new Pair<>(ConditionsAction.ACTION_NAME, ConditionsErrorAction.ACTION_NAME)
    );

    // FIXME: fine-tune
    @Value("${rabbitmq.exchanges.internal}")
    private String applicantExchange;

    @Value("${rabbitmq.routing-keys.internal-applicant}")
    private String applicantRoutingKey;

    private final MessageProducer messageProducer;

    public DepositFlow(BeanLoader beanLoader, MessageProducer messageProducer) {
        super(FLOW_NAME, createActions(beanLoader, FLOW_ACTIONS));
        this.messageProducer = messageProducer;
    }

    @Override
    public void sendDataToQueue(FlowContext flowContext) {
        log.info("Data has been sent to RabbitMQ exchange = {} using routing key = {}. Flow = {}",
                applicantExchange, applicantRoutingKey, flowContext.getFlowName());
        messageProducer.publish(flowContext, applicantExchange, applicantRoutingKey);
    }

    @Override
    public String getFlowName() {
        return "DEPOSIT_FLOW";
    }
}