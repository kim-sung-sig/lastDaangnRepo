package com.demo.daangn.app.common.dto.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
public class BaseAuditEntity {

    @Column(name = "create_date", updatable = false, nullable = false)
    protected LocalDateTime createDate;

    @Column(name = "modified_date")
    protected LocalDateTime modifiedDate;

    @PrePersist
    public void onCreate() {
        if (this.createDate == null) {
            this.createDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.modifiedDate = LocalDateTime.now();
    }

}
