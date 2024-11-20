package com.demo.daangn.domain.email.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.demo.daangn.domain.email.entity.EmailVerification;

public interface EmailVerificationRepositoryCustom {

    List<EmailVerification> findDeleteUserEmail(Integer isUsed, LocalDateTime createDate);

    EmailVerification findSendedUserEmail(String email);

}
