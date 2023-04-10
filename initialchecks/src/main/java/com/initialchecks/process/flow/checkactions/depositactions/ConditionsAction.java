package com.initialchecks.process.flow.checkactions.depositactions;

import com.initialchecks.process.flow.checkactions.CheckAction;
import org.springframework.stereotype.Component;

@Component
public class ConditionsAction implements CheckAction {
    public static final String ACTION_NAME = "conditionsAction";

    @Override
    public void makeCheck() {

    }

    @Override
    public String getActionName() {
        return ACTION_NAME;
    }
}
