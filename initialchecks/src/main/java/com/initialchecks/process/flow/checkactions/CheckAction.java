package com.initialchecks.process.flow.checkactions;

import com.initialchecks.process.dto.FlowContext;
import com.initialchecks.process.service.RetryServiceInitialChecks;
import com.scoring.commons.enums.TypeIdentifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class CheckAction {

    private final RetryServiceInitialChecks retryServiceInitialChecks;

    public abstract void makeCheck(FlowContext context);

    public abstract String getActionName();

    public void sendRejectDecision(FlowContext flowContext, TypeIdentifier typeIdentifier) {
        // As AOP needs another class for creating proxy, we put @Retryable there
        retryServiceInitialChecks.sendRejectDecision(flowContext, typeIdentifier);
    }
}
