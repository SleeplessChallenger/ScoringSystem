package com.initialchecks.process.flow.checksflow;

import com.initialchecks.process.beanloader.BeanLoader;
import com.initialchecks.process.flow.checkactions.applicantactions.IncomeAction;
import com.initialchecks.process.flow.checkactions.applicantactions.IncomeErrorAction;
import com.initialchecks.process.flow.checkactions.applicantactions.MoratoriumAction;
import com.initialchecks.process.flow.checkactions.applicantactions.MoratoriumErrorAction;
import kotlin.Pair;
import org.springframework.stereotype.Component;

import static com.initialchecks.process.utils.BeanLoaderUtils.*;

import java.util.List;

@Component
public class ApplicantFlow extends CheckFlow {
    public static final String FLOW_NAME = "APPLICANT_FLOW";
    private static final List<Pair<String, String>> FLOW_ACTIONS = List.of(
            new Pair<>(MoratoriumAction.ACTION_NAME, MoratoriumErrorAction.ACTION_NAME),
            new Pair<>(IncomeAction.ACTION_NAME, IncomeErrorAction.ACTION_NAME)
    );

    public ApplicantFlow(BeanLoader beanLoader) {
        super(FLOW_NAME, createActions(beanLoader, FLOW_ACTIONS));
    }

    @Override
    public String getFlowName() {
        // this name will be used to get Flow from Map
        return FLOW_NAME;
    }
}
