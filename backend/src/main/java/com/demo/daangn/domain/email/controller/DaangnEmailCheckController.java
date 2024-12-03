package com.demo.daangn.domain.email.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.daangn.domain.email.service.EmailService;
import com.demo.daangn.domain.user.request.VerifyCodeRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/email/auth")
@RequiredArgsConstructor
public class DaangnEmailCheckController {

    private final EmailService emailService;

    @PostMapping(value = "/send-code")
    public ResponseEntity<?> sendVerificationCode(@RequestBody @Valid @NotEmpty String email) {
        emailService.sendAuthEmail(email);
        return new ResponseEntity<>("Send Code Result", HttpStatus.OK);
    }

    @PostMapping(value = "/check")
    public ResponseEntity<?> checkEmail(@RequestParam @Valid @NotEmpty String email) {
        return new ResponseEntity<>("Email is available", HttpStatus.OK);
    }


    @PostMapping(value = "/email/verify-code")
    public ResponseEntity<?> verifyCode(@RequestBody VerifyCodeRequest verifyCodeRequest) {
        log.debug("이메일 인증 코드 확인 요청 => {}", verifyCodeRequest);
        return new ResponseEntity<>("Email verified", HttpStatus.OK);
    }
}
