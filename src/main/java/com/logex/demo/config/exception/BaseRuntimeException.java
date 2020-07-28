package com.logex.demo.config.exception;



public class BaseRuntimeException extends RuntimeException {
    protected String message;

    public BaseRuntimeException() {
    }

    public BaseRuntimeException(String message) {
        super(message);
    }
}
