package com.demo.daangn.domain.user.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.demo.daangn.global.dto.entity.BaseAuditEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Table(
    name = "dn_user",
    indexes = {
        @Index(name = "idx_user_username", columnList = "username"),
        @Index(name = "idx_user_uuid", columnList = "uuid"),
        @Index(name = "idx_user_nick_name", columnList = "nick_name"),
        @Index(name = "idx_user_nick_name_seq", columnList = "nick_name_seq"),
        @Index(name = "idx_user_nick_name_seq_final", columnList = "nick_name_seq_final")})
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@SuperBuilder
@Slf4j
public class User extends BaseAuditEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private UUID uuid;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nick_name", nullable = false)
    private String nickName; // seq

    @Column(name = "nick_name_seq", nullable = false)
    private Long nickNameSeq;

    @Column(name = "nick_name_seq_final", unique = true, nullable = false)
    private String nickNameSeqFinal;

    @Column(name = "user_profile")
    private String userProfile;

    @Column(name = "is_used", nullable = false)
    private Integer isUsed;                     // 0 : 탈퇴, 1 : 사용, 2 : 잠김

    @Column(name = "pwd_unlock_code")
    private String pwdUnlockCode;

    @Column(name = "pwd_fail_count", nullable = false)
    private Integer pwdFailCount;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @PrePersist
    private void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
        if (this.isUsed == null) {
            this.isUsed = 1;
        }
        if (this.pwdFailCount == null) {
            this.pwdFailCount = 0;
        }
    }

    public void deleteUser() {
        this.isUsed = 0;
    }

}
