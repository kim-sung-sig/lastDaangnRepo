package com.demo.daangn.global.dto.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
// @Entity
@EntityListeners(AuditingEntityListener.class)
public class BaseAuditEntity {

    @Column(name = "isUsed", nullable = false, columnDefinition = "TINYINT(1) default 1")
    private Integer isUsed;

    @CreatedDate
    @Column(name = "create_date", updatable = false, nullable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    public void updateIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

}
