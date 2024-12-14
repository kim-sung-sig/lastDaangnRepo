package com.demo.daangn.app.common.exception;

/**
 * 비즈니스 로직에서 발생하는 예외를 처리하기 위한 커스텀 예외 클래스
 */
public class CustomBusinessException extends RuntimeException {
    public CustomBusinessException(String message) {
        super(message);
    }

    public CustomBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
