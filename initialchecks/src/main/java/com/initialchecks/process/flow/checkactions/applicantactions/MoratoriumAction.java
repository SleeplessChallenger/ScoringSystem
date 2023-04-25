package com.initialchecks.process.flow.checkactions.applicantactions;

import com.initialchecks.process.dto.FlowContext;
import com.initialchecks.process.flow.checkactions.CheckAction;
import org.springframework.stereotype.Service;

@Service
public class MoratoriumAction implements CheckAction {

    public static final String ACTION_NAME = "moratoriumAction";

    @Override
    public void makeCheck(FlowContext context) {

    }

    @Override
    public String getActionName() {
        return ACTION_NAME;
    }
}
