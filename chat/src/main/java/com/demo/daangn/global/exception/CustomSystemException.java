package com.demo.daangn.global.exception;

public class CustomSystemException extends RuntimeException {
    public CustomSystemException(String message) {
        super(message);
    }

    public CustomSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
