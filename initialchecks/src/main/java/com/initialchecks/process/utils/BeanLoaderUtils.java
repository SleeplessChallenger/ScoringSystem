package com.initialchecks.process.utils;

import com.initialchecks.process.beanloader.BeanLoader;
import com.initialchecks.process.flow.checkactions.CheckAction;
import com.initialchecks.process.flow.checkactions.ErrorAction;
import kotlin.Pair;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class BeanLoaderUtils {

    public static List<Pair<CheckAction, ErrorAction>> createActions(BeanLoader beanLoader,
                                                                     List<Pair<String, String>> flowTasks) {
        return flowTasks.stream().map(tasks -> {
            final CheckAction checkAction = beanLoader.getBeanFromApplicationContext(tasks.getFirst(),
                    CheckAction.class);
            final ErrorAction errorAction = beanLoader.getBeanFromApplicationContext(tasks.getSecond(),
                    ErrorAction.class);

            return new Pair<>(checkAction, errorAction);
        }).toList();
    }
}
