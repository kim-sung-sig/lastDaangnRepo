package com.demo.daangn.domain.email.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.email.entity.EmailVerification;

@Repository
public interface EmailVerificationRepository extends JpaRepository <EmailVerification, UUID>, EmailVerificationRepositoryCustom {

    // 1. 저장
    // save
    // 2. 조회
    Optional<EmailVerification> findByEmailToken(String emailToken);
    // 3. 수정

    // 4. 삭제

}
