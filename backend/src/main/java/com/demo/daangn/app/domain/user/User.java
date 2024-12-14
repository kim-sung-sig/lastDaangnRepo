package com.demo.daangn.app.domain.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.demo.daangn.app.common.dto.entity.BaseAuditEntity;
import com.demo.daangn.app.common.enums.IsUsedEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

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
    @Enumerated(EnumType.STRING)
    private IsUsedEnum isUsed;                     // 0 : 탈퇴, 1 : 사용, 2 : 잠김

    @Column(name = "pwd_unlock_code")
    private String pwdUnlockCode;

    @Column(name = "pwd_fail_count", nullable = false)
    private Integer pwdFailCount;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @PrePersist
    private void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
        if (this.isUsed == null) {
            this.isUsed = IsUsedEnum.ENABLED;
        }
        if (this.pwdFailCount == null) {
            this.pwdFailCount = 0;
        }
    }

    public void deleteUser() {
        this.isUsed = IsUsedEnum.DISABLED;
    }

}
