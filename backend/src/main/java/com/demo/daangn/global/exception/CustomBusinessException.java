package com.demo.daangn.global.exception;

public class CustomBusinessException extends RuntimeException {
    public CustomBusinessException(String message) {
        super(message);
    }

    public CustomBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
