package com.demo.daangn.global.config.websocket;

import org.springframework.security.core.session.SessionDestroyedEvent;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

public class CustomSessionListener implements HttpSessionListener{

    @Override
    public void sessionCreated(SessionDestroyedEvent ,HttpSessionEvent se) {
        HttpSessionListener.super.sessionCreated(se);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // TODO Auto-generated method stub
        HttpSessionListener.super.sessionDestroyed(se);
    }
    
}
