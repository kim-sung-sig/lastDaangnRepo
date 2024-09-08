package com.demo.daangn.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VerifyCodeRequest {

    @NotEmpty
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Code should not be empty")
    private String code;

}
