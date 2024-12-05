package com.demo.daangn.domain.event.entity;

import com.demo.daangn.global.dto.entity.BaseAuditEntity;

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

@Table(
    name = "dn_event_publisher",
    indexes = {
        @Index(name = "idx_event_publisher_published", columnList = "published")})
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Getter
@SuperBuilder
public class EventPublisher extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "published", nullable = false)
    private Boolean published;

    @Column(columnDefinition = "json")
    private String content;

    @PrePersist
    public void prePersist() {
        if(this.published == null) this.published = false;
    }

}
