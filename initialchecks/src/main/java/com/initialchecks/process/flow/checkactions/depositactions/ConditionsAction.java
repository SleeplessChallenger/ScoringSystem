package com.initialchecks.process.flow.checkactions.depositactions;

import com.initialchecks.process.dto.FlowContext;
import com.initialchecks.process.flow.checkactions.CheckAction;
import com.initialchecks.process.service.RetryServiceInitialChecks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class ConditionsAction extends CheckAction {
    public static final String ACTION_NAME = "conditionsAction";

    public ConditionsAction(RetryServiceInitialChecks retryServiceInitialChecks) {
        super(retryServiceInitialChecks);
    }

    @Override
    public void makeCheck(FlowContext context) {
        log.info("Flow = {}, action = {}. Processing deposit = {}",
                context.getFlowName(), getActionName(), context.getApplicationCheck().getDepositId());

        // if some rule worked
        int someRandomCondition = new Random().nextInt(1, 10);
        if (someRandomCondition > 5) {
            sendRejectDecision(context);
        } else {
            // continue
        }
    }

    @Override
    public String getActionName() {
        return ACTION_NAME;
    }
}
