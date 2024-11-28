package com.demo.daangn.domain.user.entity;

import java.io.Serializable;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Table(
    name = "user",
    indexes = {
        @Index(name = "idx_user_username", columnList = "username"),
        @Index(name = "idx_user_uuid", columnList = "uuid"),
        @Index(name = "idx_user_nick_name", columnList = "nick_name"),
        @Index(name = "idx_user_nick_name_seq", columnList = "nick_name_seq"),
        @Index(name = "idx_user_nick_name_seq_final", columnList = "nick_name_seq_final")})
@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Column(name = "is_used", nullable = false, columnDefinition = "TINYINT(1) default 1")
    private Integer isUsed;

    @PrePersist
    private void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
        log.debug("prePersist uuid: {}", this.uuid);
        if (this.isUsed == null) {
            this.isUsed = 1;
        }
        log.debug("prePersist isUsed: {}", this.getIsUsed());
    }

    public void updateProfile(String nickName, String userProfile) {
        this.nickName = nickName;
        this.userProfile = userProfile;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

    public void deleteUser() {
        this.isUsed = 0;
    }

}
