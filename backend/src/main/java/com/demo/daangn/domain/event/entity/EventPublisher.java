package com.demo.daangn.domain.event.entity;

import com.demo.daangn.global.dto.entity.BaseAuditEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(
    name = "event_publisher",
    indexes = {
        @Index(name = "idx_event_publisher_published", columnList = "published")})
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventPublisher extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "published")
    private Integer published;

    @Column(columnDefinition = "json")
    private String content;

}
