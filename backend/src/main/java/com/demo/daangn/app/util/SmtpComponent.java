package com.demo.daangn.app.util;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmtpComponent {

    private final JavaMailSender javaMailSender;    // 메일 보내는 객체

    @Value("${spring.mail.username}")
    private String from;

    private final String NAME = "DAANGN";

    @Async
    public CompletableFuture<Boolean> mailSend(String to, String subject, String text) {
        log.debug("mailSend email: {}", to);
        boolean isSent = false;
		try {
            // 각 메일 전송마다 새로운 MimeMessage와 MimeMessageHelper를 생성
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");

            mimeMessageHelper.setFrom(from, NAME);              // 발신인

			mimeMessageHelper.setTo(Arrays.stream(to.split("\\s+")) // 수신인
                    .map(this::createInternetAddress)
                    .filter(Objects::nonNull)
                    .toArray(InternetAddress[]::new));
			mimeMessageHelper.setSubject(subject);              // 제목
			mimeMessageHelper.setText(text, true);         // 내용

			javaMailSender.send(message);                       // 전송
            isSent = true;

		} catch (MessagingException e) {
			log.error("메일 전송 실패", e);
		} catch (Exception e) {
            log.error("예기치 못한 오류 발생", e);
        }
		return CompletableFuture.completedFuture(isSent);
    }

    private InternetAddress createInternetAddress(String email) {
        if(email == null) {
            return null;
        }

        String trimmedEmail = email.trim();

        // 1. 정규식 검증
        if(!CommonUtil.emailValidation(trimmedEmail)) {
            log.warn("유효하지 않은 이메일 형식 : {}", email);
            return null;
        }

        // 2. InternetAddress 객체 생성
        try {
            return new InternetAddress(email.trim());
        } catch (Exception e) {
            log.warn("InternetAddress 새성 실패: {}", trimmedEmail, e);
            return null;
        }
    }

}
