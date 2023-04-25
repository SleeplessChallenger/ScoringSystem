package com.initialchecks.process.flow.checkactions;

import com.initialchecks.process.dto.FlowContext;

public interface CheckAction {

    void makeCheck(FlowContext context);

    String getActionName();
}
