package com.demo.daangn.app.service.user.event;

import com.demo.daangn.app.domain.user.User;

import lombok.Getter;

@Getter
public class UserSignUpEvent {

    private User signUpUser;

    public UserSignUpEvent (User signUpUser) {
        this.signUpUser = signUpUser;
    }

}
