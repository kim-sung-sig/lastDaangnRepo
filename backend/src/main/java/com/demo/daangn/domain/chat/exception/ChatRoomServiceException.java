package com.demo.daangn.domain.chat.exception;

public class ChatRoomServiceException extends Exception{

    public ChatRoomServiceException(String message) {
        super(message);
    }

    public ChatRoomServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
