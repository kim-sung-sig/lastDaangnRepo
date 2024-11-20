package com.demo.daangn.domain.email.response;

import com.demo.daangn.domain.email.entity.EmailVerification;

import lombok.Data;

@Data
public class EmailSendResponse {

    private String email;

    private String emailToken;

    public static EmailSendResponse of(EmailVerification entity) {
        EmailSendResponse response = new EmailSendResponse();
        response.setEmail(entity.getEmail());
        response.setEmailToken(entity.getEmailToken());
        return response;
    }

}
