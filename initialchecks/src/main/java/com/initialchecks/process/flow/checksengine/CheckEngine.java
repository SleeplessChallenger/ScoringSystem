package com.initialchecks.process.flow.checksengine;

import com.initialchecks.process.flow.checkactions.CheckAction;
import com.initialchecks.process.flow.checkactions.ErrorAction;
import com.initialchecks.process.flow.checksflow.CheckFlow;
import com.initialchecks.process.dto.ApplicationCheck;
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

    public void startEngine(CheckFlow checkFlow, ApplicationCheck applicationCheck) {
        final String applicantId = applicationCheck.getApplicantId();
        final String depositId = applicationCheck.getDepositId();
        log.info("Start processing application with user = {} and deposit = {}", applicantId, depositId);

    }

    private void processFlow(List<Pair<CheckAction, ErrorAction>> actions, String flowName) {
        for (Pair<CheckAction, ErrorAction> actionPair : actions) {
            final CheckAction action = actionPair.getFirst();
            final ErrorAction errorAction = actionPair.getSecond();

            try {
                action.makeCheck();
            } catch (RuntimeException ex) {
                log.error("Error while processing flow = {}, action = {}", flowName, action);
            }
        }
    }
}
