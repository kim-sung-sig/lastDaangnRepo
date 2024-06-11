package com.demo.daangn.global.config.websocket;

import com.demo.daangn.domain.user.entity.DaangnUserEntity;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@WebListener
@Slf4j
@RequiredArgsConstructor
public class CustomSessionListener implements HttpSessionListener{

    private final WebSocketUserRegistry userRegistry;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("HttpSession Created");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("HttpSession Destroyed");
        Long userId = ( (DaangnUserEntity) se.getSession().getAttribute("user") ).getId();
        userRegistry.removeUser(userId);
    }
    
}
