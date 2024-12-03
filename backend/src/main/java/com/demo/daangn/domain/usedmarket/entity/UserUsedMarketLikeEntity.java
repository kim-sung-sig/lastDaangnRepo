package com.demo.daangn.domain.usedmarket.entity;

import com.demo.daangn.domain.user.entity.User;
import com.demo.daangn.global.dto.entity.BaseAuditEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Table(
    name = "user_used_market_like_map",
    indexes = {
        @Index(name = "idx_daangn_user_used_market_like_user_id", columnList = "user_id"),
        @Index(name = "idx_daangn_user_used_market_like_used_market_id", columnList = "used_market_id")})
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Getter
@SuperBuilder
public class UserUsedMarketLikeEntity extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "used_market_id", nullable = false)
    private UsedMarket usedMarket;

}
