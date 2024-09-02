package com.demo.daangn.domain.chat.entity;

import com.demo.daangn.domain.usedmarket.entity.UsedMarketEntity;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(
    name = "chat_room_used_market_map",
    indexes = {
        @Index(name = "idx_chat_room_used_market_id", columnList = "used_market_id"),
        @Index(name = "idx_chat_room_used_market_id", columnList = "chat_room_id")})
@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomUsedMarketEntity extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "used_market_id", nullable = false)
    private UsedMarketEntity usedMarket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoomEntity chatRoom;

}
