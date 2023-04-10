package com.initialchecks.process.checksflow;

import com.initialchecks.process.checkactions.CheckAction;
import com.initialchecks.process.checkactions.ErrorAction;
import kotlin.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicantFlow extends CheckFlow {

    @Autowired
    public ApplicantFlow(List<Pair<CheckAction, ErrorAction>> applicantActions) {
        actions.addAll(applicantActions);
    }
}
