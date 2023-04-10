package com.initialchecks.process.flow.checksflow;

import com.initialchecks.process.beanloader.BeanLoader;
import com.initialchecks.process.flow.checkactions.depositactions.ConditionsAction;
import com.initialchecks.process.flow.checkactions.depositactions.ConditionsErrorAction;
import com.initialchecks.process.flow.checkactions.depositactions.ReservedDepositAction;
import com.initialchecks.process.flow.checkactions.depositactions.ReservedDepositErrorAction;
import kotlin.Pair;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.initialchecks.process.utils.BeanLoaderUtils.createActions;

@Component
public class DepositFlow extends CheckFlow {

    public static final String FLOW_NAME = "DEPOSIT_FLOW";

    private static final List<Pair<String, String>> FLOW_ACTIONS = List.of(
            new Pair<>(ReservedDepositAction.ACTION_NAME, ReservedDepositErrorAction.ACTION_NAME),
            new Pair<>(ConditionsAction.ACTION_NAME, ConditionsErrorAction.ACTION_NAME)
    );

    public DepositFlow(BeanLoader beanLoader) {
        super(FLOW_NAME, createActions(beanLoader, FLOW_ACTIONS));
    }

    @Override
    public String getFlowName() {
        return "DEPOSIT_FLOW";
    }
}