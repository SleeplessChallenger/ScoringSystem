package com.initialchecks.process.flow.checkactions;

import com.initialchecks.process.dto.FlowContext;

public interface ErrorAction {

    void handleError(FlowContext context);
    String getActionName();
}
