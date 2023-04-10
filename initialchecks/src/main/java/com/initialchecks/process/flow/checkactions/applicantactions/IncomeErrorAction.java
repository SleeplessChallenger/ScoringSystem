package com.initialchecks.process.flow.checkactions.applicantactions;

import com.initialchecks.process.flow.checkactions.ErrorAction;
import org.springframework.stereotype.Service;

@Service
public class IncomeErrorAction implements ErrorAction {
    public static final String ACTION_NAME = "incomeErrorAction";

    @Override
    public void handleError() {

    }

    @Override
    public String getActionName() {
        return ACTION_NAME;
    }
}
