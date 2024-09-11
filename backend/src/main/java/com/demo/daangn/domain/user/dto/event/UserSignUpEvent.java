package com.demo.daangn.domain.user.dto.event;

import com.demo.daangn.domain.user.entity.DaangnUserEntity;

import lombok.Getter;

@Getter
public class UserSignUpEvent {

    private DaangnUserEntity signUpUser;

    public UserSignUpEvent (DaangnUserEntity signUpUser) {
        this.signUpUser = signUpUser;
    }

}
