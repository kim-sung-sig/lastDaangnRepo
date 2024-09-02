package com.demo.daangn.domain.notification.entity;

import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.global.dto.entity.BaseAuditEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(
    name = "daangn_notifications",
    indexes = {
        @Index(name = "idx_daangn_notifications_user_id", columnList = "user_id")})
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationEntity extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 패치 필요 없음..
    @JoinColumn(name = "user_id")
    private DaangnUserEntity user;

    @Column(name = "content")
    private String content;

    @Column(name = "is_readed")
    private Integer isReaded;

}
