package com.furkanozdemir.common.exception;

public class StoreListEmptyException extends RuntimeException {

    private static final String TASK_NOT_FOUND_MESSAGE = "Store List is empty";

    public StoreListEmptyException() {
        super(String.format(TASK_NOT_FOUND_MESSAGE));
    }

}
