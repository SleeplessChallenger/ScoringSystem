package com.initialchecks.process.flow.checkactions.depositactions;

import com.initialchecks.process.dto.FlowContext;
import com.initialchecks.process.flow.checkactions.CheckAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReservedDepositAction implements CheckAction {
    public static final String ACTION_NAME = "reservedDepositAction";

    @Override
    public void makeCheck(FlowContext context) {
        log.info("Flow = {}, action = {}. Processing deposit = {}",
                context.getFlowName(), getActionName(), context.getApplicationCheck().getDepositId());
    }

    @Override
    public String getActionName() {
        return ACTION_NAME;
    }
}
