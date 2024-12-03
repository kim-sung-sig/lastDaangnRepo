package com.demo.daangn.domain.user.event;

import com.demo.daangn.domain.user.entity.User;

import lombok.Getter;

@Getter
public class UserSignUpEvent {

    private User signUpUser;

    public UserSignUpEvent (User signUpUser) {
        this.signUpUser = signUpUser;
    }

}
