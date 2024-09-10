package com.demo.daangn.domain.user.entity;

import java.util.UUID;

import com.demo.daangn.global.dto.entity.BaseAuditEntity;

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
    name = "daangn_user_temp_email")
@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class DaangnUserEmailEntity extends BaseAuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String emailToken;

    private String emailKey;

    @PrePersist
    private void prePersist() {
        if (this.emailToken == null) {
            this.emailToken = UUID.randomUUID().toString() + System.currentTimeMillis();
        }
        if (this.getIsUsed() == null) {
            this.setIsUsed(0);
        }
        log.debug("prePersist emailToken: {}", this.emailToken);
        log.debug("prePersist isUsed: {}", this.getIsUsed());
    }
}
