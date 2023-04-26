package com.initialchecks.process.flow.checkactions.depositactions;

import com.initialchecks.process.dto.FlowContext;
import com.initialchecks.process.flow.checkactions.ErrorAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConditionsErrorAction implements ErrorAction {
    public static final String ACTION_NAME = "conditionsErrorAction";

    @Override
    public void handleError(FlowContext context) {
        log.error("Flow = {}, action = {}. Processing deposit = {}",
                context.getFlowName(), getActionName(), context.getApplicationCheck().getDepositId());
    }

    @Override
    public String getActionName() {
        return ACTION_NAME;
    }
}
