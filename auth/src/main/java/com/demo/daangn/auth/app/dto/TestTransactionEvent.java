package com.demo.daangn.auth.app.dto;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class TestTransactionEvent extends ApplicationEvent {

    private final Integer id;
    private final String message;

    public TestTransactionEvent(Object source, Integer id, String message) {
        super(source);
        this.id = id;
        this.message = message;
    }

}
