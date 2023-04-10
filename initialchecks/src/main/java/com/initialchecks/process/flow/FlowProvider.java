package com.initialchecks.process.flow;

import com.initialchecks.process.flow.checksflow.CheckFlow;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FlowProvider {

    private final Map<String, CheckFlow> flowMap;

    public FlowProvider(List<CheckFlow> flows) {
        this.flowMap = flows.stream().collect(Collectors.toMap(CheckFlow::getFlowName, Function.identity()));
    }

    public CheckFlow getFullFlow(String flowName) {
        return flowMap.get(flowName);
    }
}
