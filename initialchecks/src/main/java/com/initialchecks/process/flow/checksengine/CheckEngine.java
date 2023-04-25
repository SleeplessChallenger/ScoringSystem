package com.initialchecks.process.flow.checksengine;

import com.initialchecks.process.dto.FlowContext;
import com.initialchecks.process.flow.checkactions.CheckAction;
import com.initialchecks.process.flow.checkactions.ErrorAction;
import com.initialchecks.process.flow.checksflow.CheckFlow;
import kotlin.Pair;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class CheckEngine {

    private List<CheckFlow> checksFlow;

    public void startEngine(CheckFlow checkFlow, FlowContext flowContext) {
        final String applicantId = flowContext.getApplicationCheck().getApplicantId();
        final String depositId = flowContext.getApplicationCheck().getDepositId();
        log.info("Start processing application with user = {} and deposit = {}", applicantId, depositId);

        final String flowName = checkFlow.getFlowName();
        flowContext.setFlowName(flowName);

        processFlow(checkFlow.getFlowActions(), flowContext);
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
