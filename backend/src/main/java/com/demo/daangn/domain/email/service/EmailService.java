package com.demo.daangn.domain.email.service;

public interface EmailService {

    // 1. 인증 메일 발송하기
    boolean sendAuthEmail(String email);

    // 2. 인증 메일 확인하기
    boolean verifyEmail(String email, String token);

    // 3. 일반 메일 발송하기
    boolean sendEmail(String email, String title, String content);

}
