package com.initialchecks.process.flow.checkactions.depositactions;

import com.initialchecks.process.dto.FlowContext;
import com.initialchecks.process.flow.checkactions.CheckAction;
import com.initialchecks.process.service.RetryServiceInitialChecks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReservedDepositAction extends CheckAction {
    public static final String ACTION_NAME = "reservedDepositAction";

    public ReservedDepositAction(RetryServiceInitialChecks retryServiceInitialChecks) {
        super(retryServiceInitialChecks);
    }

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
