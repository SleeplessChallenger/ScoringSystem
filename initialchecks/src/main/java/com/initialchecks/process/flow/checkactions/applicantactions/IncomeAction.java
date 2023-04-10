package com.initialchecks.process.flow.checkactions.applicantactions;

import com.initialchecks.process.flow.checkactions.CheckAction;
import org.springframework.stereotype.Service;

@Service
public class IncomeAction implements CheckAction {
    public static final String ACTION_NAME = "incomeAction";

    @Override
    public void makeCheck() {

    }

    @Override
    public String getActionName() {
        return ACTION_NAME;
    }
}
