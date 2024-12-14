package com.demo.daangn.app.service.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginRequest {

    private String username;

    private String password;

}
