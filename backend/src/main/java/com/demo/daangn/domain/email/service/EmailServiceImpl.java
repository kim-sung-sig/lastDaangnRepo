package com.demo.daangn.domain.email.service;

import org.springframework.stereotype.Service;

import com.demo.daangn.domain.email.components.SmtpComponent;
import com.demo.daangn.domain.email.entity.EmailVerification;
import com.demo.daangn.domain.email.repository.EmailVerificationRepository;
import com.demo.daangn.global.exception.CustomBusinessException;
import com.demo.daangn.global.util.common.CommonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("emailService")
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    
    private final EmailVerificationRepository emailVerificationRepository;
    private final SmtpComponent smtpComponent;

    @Override
    public boolean sendAuthEmail(String email) {
        log.info("이메일 인증 메일 발송 요청 email: {}", email);
        // 1. 이메일 형식 체크
        if(!CommonUtil.emailValidation(email)) {
            throw new CustomBusinessException("이메일 형식이 유효하지 않습니다.");
        }

        log.debug("이메일 인증 객체 저장 시작");
        EmailVerification emailVerification = EmailVerification.builder()
                .email(email)
                .emailKey(CommonUtil.generateRandomKey())
                .build();
        emailVerificationRepository.save(emailVerification);
        log.debug("이메일 인증 객체 저장 완료 emailVerification: {}", emailVerification);

        log.debug("이메일 전송 요청 email: {}", email);
        smtpComponent.mailSend(email, "당근마켓 인증 번호", "인증번호 : " + emailVerification.getEmailKey());
        log.debug("이메일 비동기 요청 완료");
        return true;
    }

    @Override
    public boolean verifyEmail(String email, String token) {
        throw new UnsupportedOperationException("Unimplemented method 'verifyEmail'");
    }

    @Override
    public boolean sendEmail(String email, String title, String content) {
        throw new UnsupportedOperationException("Unimplemented method 'sendEmail'");
    }

    
}
