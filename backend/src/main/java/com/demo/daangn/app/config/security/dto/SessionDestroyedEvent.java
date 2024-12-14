package com.demo.daangn.app.config.security.dto;

import com.demo.daangn.app.domain.user.User;

import jakarta.servlet.http.HttpSessionEvent;
import lombok.Getter;

@Getter
public class SessionDestroyedEvent {

    private HttpSessionEvent se;
    private User user;

    public SessionDestroyedEvent(HttpSessionEvent se, User user) {
        this.se = se;
        this.user = user;
    }

}
