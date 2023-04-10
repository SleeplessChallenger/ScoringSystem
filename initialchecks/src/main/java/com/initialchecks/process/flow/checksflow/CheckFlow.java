package com.initialchecks.process.flow.checksflow;

import com.initialchecks.process.flow.checkactions.CheckAction;
import com.initialchecks.process.flow.checkactions.ErrorAction;
import kotlin.Pair;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class CheckFlow {

    private final String checkFlowName;
    private final List<Pair<CheckAction, ErrorAction>> flowActions;

    public CheckFlow(String name, List<Pair<CheckAction, ErrorAction>> actions) {
        this.checkFlowName = name;
        this.flowActions = actions;
    }

    public abstract String getFlowName();
}
