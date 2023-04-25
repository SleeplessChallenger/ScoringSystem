package com.initialchecks.process.flow.checkactions.applicantactions;

import com.initialchecks.process.dto.FlowContext;
import com.initialchecks.process.flow.checkactions.ErrorAction;
import org.springframework.stereotype.Service;

@Service
public class MoratoriumErrorAction implements ErrorAction {
    public static final String ACTION_NAME = "moratoriumErrorAction";


    @Override
    public void handleError(FlowContext context) {

    }

    @Override
    public String getActionName() {
        return ACTION_NAME;
    }
}
