package com.demo.daangn.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DaangnUserSignUpRequest {

    private String username;

    private String password;

    private String email;

}
