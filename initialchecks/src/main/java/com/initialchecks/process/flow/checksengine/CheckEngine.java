package com.initialchecks.process.flow.checksengine;

import com.initialchecks.process.dto.ApplicationCheck;
import com.initialchecks.process.dto.FlowContext;
import com.initialchecks.process.flow.checkactions.CheckAction;
import com.initialchecks.process.flow.checkactions.ErrorAction;
import com.initialchecks.process.flow.checksflow.CheckFlow;
import com.scoring.commons.utils.UuidUtils;
import kotlin.Pair;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class CheckEngine {

    public void startEngine(CheckFlow checkFlow, ApplicationCheck applicationCheck) {
        final FlowContext context = FlowContext.builder()
                .flowUniqueId(UuidUtils.generateUuid())
                .applicationCheck(applicationCheck)
                .build();

        final String applicantId = context.getApplicationCheck().getApplicantId();
        final String depositId = context.getApplicationCheck().getDepositId();
        final String flowName = checkFlow.getFlowName();

        context.setFlowName(flowName);
        log.info("Start processing application with applicant = {} and deposit = {}. Flow = {}",
                applicantId, depositId, flowName);
        processFlow(checkFlow.getFlowActions(), context);
        log.info("Finished checking applicant = {} and deposit = {} for flow = {}. Start sending to queue",
                applicantId, depositId, flowName);
        // If checks haven't ended with Reject or no error => send data to RabbitMQ
        checkFlow.sendDataToQueue(context);
    }

    private void processFlow(List<Pair<CheckAction, ErrorAction>> actions, FlowContext flowContext) {
        for (Pair<CheckAction, ErrorAction> actionPair : actions) {
            final CheckAction action = actionPair.getFirst();
            final ErrorAction errorAction = actionPair.getSecond();

            try {
                action.makeCheck(flowContext);
            } catch (RuntimeException ex) {
                log.error("Error while processing flow = {}, action = {}",
                        flowContext.getFlowName(),
                        action.getActionName(),
                        ex
                );
                errorAction.handleError(flowContext);
            }
        }
    }
}
