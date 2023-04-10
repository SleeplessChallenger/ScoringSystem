package com.initialchecks.process.checksflow;

import com.initialchecks.process.checkactions.CheckAction;
import com.initialchecks.process.checkactions.ErrorAction;
import kotlin.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class CheckFlow {

    protected final List<Pair<CheckAction, ErrorAction>> actions = new ArrayList<>();

}
