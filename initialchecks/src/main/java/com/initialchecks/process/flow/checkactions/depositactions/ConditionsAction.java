package com.initialchecks.process.flow.checkactions.depositactions;

import com.initialchecks.process.dto.FlowContext;
import com.initialchecks.process.flow.checkactions.CheckAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConditionsAction implements CheckAction {
    public static final String ACTION_NAME = "conditionsAction";

    @Override
    public void makeCheck(FlowContext context) {
        log.info("Action = ");
    }

    @Override
    public String getActionName() {
        return ACTION_NAME;
    }
}
