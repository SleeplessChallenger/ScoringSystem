package com.initialchecks.process.flow.checkactions.depositactions;

import com.initialchecks.process.dto.FlowContext;
import com.initialchecks.process.flow.checkactions.ErrorAction;
import org.springframework.stereotype.Service;

@Service
public class ConditionsErrorAction implements ErrorAction {
    public static final String ACTION_NAME = "conditionsErrorAction";

    @Override
    public void handleError(FlowContext context) {

    }

    @Override
    public String getActionName() {
        return ACTION_NAME;
    }
}
