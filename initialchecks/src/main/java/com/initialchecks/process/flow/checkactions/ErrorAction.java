package com.initialchecks.process.flow.checkactions;

public interface ErrorAction {

    void handleError();
    String getActionName();
}
