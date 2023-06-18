package com.initialchecks.process.flow.checkactions.applicantactions;

import com.initialchecks.process.dto.FlowContext;
import com.initialchecks.process.flow.checkactions.CheckAction;
import com.initialchecks.process.service.RetryServiceInitialChecks;
import com.scoring.commons.enums.TypeIdentifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class IncomeAction extends CheckAction {

    public static final String ACTION_NAME = "incomeAction";

    public IncomeAction(RetryServiceInitialChecks retryServiceInitialChecks) {
        super(retryServiceInitialChecks);
    }

    @Override
    public void makeCheck(FlowContext context) {
        log.info("Flow = {}, action = {}. Processing applicant = {}",
                context.getFlowName(), getActionName(), context.getApplicationCheck().getApplicantId());

        // if some rule worked
        int someRandomCondition = new Random().nextInt(1, 10);
        if (someRandomCondition > 5) {
            sendRejectDecision(context, TypeIdentifier.APPLICANT);
        } else {
            // continue
        }
    }

    @Override
    public String getActionName() {
        return ACTION_NAME;
    }
}
