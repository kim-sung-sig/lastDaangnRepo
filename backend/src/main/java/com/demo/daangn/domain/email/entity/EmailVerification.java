package com.demo.daangn.domain.email.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.demo.daangn.global.dto.entity.BaseAuditEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Table(
    name = "email_verification")
@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class EmailVerification extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String emailToken;

    private String emailKey;

    @Column(name = "is_verified",columnDefinition = "TINYINT(1) DEFAULT 0")
    private Integer isVerified;

    @Column(name = "expire_date")
    private LocalDateTime expireDate;

    @PrePersist
    private void prePersist() {
        if (this.emailToken == null) { // 토큰 생성
            this.emailToken = UUID.randomUUID().toString() + "_" + System.currentTimeMillis();
        }
        if(getCreateDate() == null) {
            setCreateDate(LocalDateTime.now());
        }
        if(this.isVerified == null) {
            this.isVerified = 0;
        }
        log.debug("prePersist emailToken: {}", this.emailToken);
    }
}
