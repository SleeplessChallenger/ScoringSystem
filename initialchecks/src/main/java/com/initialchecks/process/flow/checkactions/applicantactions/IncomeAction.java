package com.initialchecks.process.flow.checkactions.applicantactions;

import com.initialchecks.process.dto.FlowContext;
import com.initialchecks.process.flow.checkactions.CheckAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IncomeAction implements CheckAction {
    public static final String ACTION_NAME = "incomeAction";

    @Override
    public void makeCheck(FlowContext context) {
        log.info("Flow = {}, action = {}. Processing applicant = {}",
                context.getFlowName(), getActionName(), context.getApplicationCheck().getApplicantId());
    }

    @Override
    public String getActionName() {
        return ACTION_NAME;
    }
}
