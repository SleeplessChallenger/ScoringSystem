package com.finalchecks.exceptions;

public class ScheduledUpdateException extends RuntimeException {

    public ScheduledUpdateException(String message) {
        super(message);
    }

    public ScheduledUpdateException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
