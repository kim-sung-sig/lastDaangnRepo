package com.demo.daangn.app.domain.temp.nickname;

import java.util.UUID;

import com.demo.daangn.app.common.dto.entity.BaseAuditEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Table(
    name = "dn_user_nick_name",
    indexes = {
        @Index(name = "idx_user_nick_name", columnList = "nick_name")})
@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString(callSuper = true)
@SuperBuilder
@Slf4j
public class UserNickName extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "nick_name", nullable = false, updatable = false)
    private String nickName;

    @Column(name = "num", nullable = false)
    private Long num;

    @PrePersist
    public void prePersist() {
        if(this.id == null) {
            this.id = UUID.randomUUID();
        }
        if(this.num == null) {
            this.num = 1L;
        }
    }

}
