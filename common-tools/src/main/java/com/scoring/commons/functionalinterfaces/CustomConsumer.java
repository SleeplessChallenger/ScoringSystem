package com.scoring.commons.functionalinterfaces;

@FunctionalInterface
public interface CustomConsumer<T> {

    public void accept(T rejectDecision);
}
