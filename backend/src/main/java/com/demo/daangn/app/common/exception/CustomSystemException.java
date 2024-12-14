package com.demo.daangn.app.common.exception;

public class CustomSystemException extends RuntimeException {
    public CustomSystemException(String message) {
        super(message);
    }

    public CustomSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
