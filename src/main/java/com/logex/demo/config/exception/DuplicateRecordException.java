package com.logex.demo.config.exception;

public class DuplicateRecordException extends BaseRuntimeException {
    public DuplicateRecordException(String s) {
        super(s);
    }
}