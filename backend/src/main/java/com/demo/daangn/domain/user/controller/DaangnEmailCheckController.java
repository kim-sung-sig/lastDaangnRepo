package com.demo.daangn.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.daangn.domain.user.dto.request.VerifyCodeRequest;
import com.demo.daangn.domain.user.service.DaangnUserEmailService;
import com.demo.daangn.global.util.common.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/daangn/users")
@RequiredArgsConstructor
public class DaangnEmailCheckController {

    private final DaangnUserEmailService emailService;

    @GetMapping(value = "/email/check")
    public ResponseEntity<?> checkEmail(HttpServletRequest request,
                                        @RequestParam @Valid @NotEmpty String email
    ) {
        try {
            if(CommonUtil.isUserLogin(request)){ // 로그인 한 유저 차단
                return new ResponseEntity<>("Already logged in", HttpStatus.BAD_REQUEST);
            }

            log.debug("이메일 중복 확인 요청 => {}", email);
            return new ResponseEntity<>("Email is available", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/email/send-code")
    public ResponseEntity<?> sendVerificationCode(HttpServletRequest request,
                                                @RequestBody @Valid @NotEmpty String email
    ) {
        try {
            return new ResponseEntity<>("Send Code Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/email/verify-code")
    public ResponseEntity<?> verifyCode(HttpServletRequest request,
                                        @RequestBody VerifyCodeRequest verifyCodeRequest
    ) {
        log.debug("이메일 인증 코드 확인 요청 => {}", verifyCodeRequest);
        try {
            // boolean isCodeValid = emailService.verifyCode(verifyCodeRequest.getEmail(), verifyCodeRequest.getCode());
            // if (isCodeValid) {
            //     return new ResponseEntity<>("Email verified", HttpStatus.OK);
            // } else {
            //     return new ResponseEntity<>("Invalid verification code", HttpStatus.BAD_REQUEST);
            // }
            return new ResponseEntity<>("Email verified", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
