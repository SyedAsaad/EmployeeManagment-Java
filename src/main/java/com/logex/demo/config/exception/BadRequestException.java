package com.logex.demo.config.exception;

public class BadRequestException extends BaseRuntimeException {
    public BadRequestException(String s) {
        super(s);
    }
}
