package com.demo.daangn.app.config.security.dto;

import com.demo.daangn.app.domain.user.User;

import lombok.Getter;

@Getter
public class UserLoggedInEvent {

    private User user;

    public UserLoggedInEvent(User user) {
        this.user = user;
    }

}
