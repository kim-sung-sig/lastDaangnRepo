package com.demo.daangn.domain.user.entity;

import com.demo.daangn.global.dto.entity.BaseFileEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Table(
    name = "dn_user_profiles",
    indexes = {
        @Index(name = "idx_user_profiles_user_id", columnList = "user_id")})
@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString(callSuper = true)
@SuperBuilder
public class UserProfile extends BaseFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_used")
    private Boolean isUsed;

    @PrePersist
    public void prePersist() {
        if (this.isUsed == null) {
            this.isUsed = true;
        }
    }

}
