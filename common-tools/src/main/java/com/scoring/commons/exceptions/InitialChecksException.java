package com.scoring.commons.exceptions;

public class InitialChecksException extends RuntimeException {

    public InitialChecksException(String message) {
        super(message);
    }

    public InitialChecksException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
