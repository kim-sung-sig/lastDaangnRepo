package com.demo.daangn.domain.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginRequest {

    private String username;

    private String password;

}
