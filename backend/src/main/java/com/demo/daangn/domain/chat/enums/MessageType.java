package com.demo.daangn.domain.chat.enums;

public enum MessageType {
    ENTER,
    TALK,
    LEAVE;
    // 일단 3개만!

    public static MessageType getMessageType(Integer type) {
        return switch (type) {
            case 1 -> ENTER;
            case 2 -> TALK;
            case 3 -> LEAVE;
            default -> throw new IllegalArgumentException("Unexpected value: " + type);
        };
    }
}
