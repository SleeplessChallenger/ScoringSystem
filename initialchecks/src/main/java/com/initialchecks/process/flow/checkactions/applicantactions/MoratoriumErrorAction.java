package com.initialchecks.process.flow.checkactions.applicantactions;

import com.initialchecks.process.dto.FlowContext;
import com.initialchecks.process.flow.checkactions.ErrorAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MoratoriumErrorAction implements ErrorAction {
    public static final String ACTION_NAME = "moratoriumErrorAction";


    @Override
    public void handleError(FlowContext context) {
        log.error("Flow = {}, action = {}. Processing applicant = {}",
                context.getFlowName(), getActionName(), context.getApplicationCheck().getApplicantId());
    }

    @Override
    public String getActionName() {
        return ACTION_NAME;
    }
}
