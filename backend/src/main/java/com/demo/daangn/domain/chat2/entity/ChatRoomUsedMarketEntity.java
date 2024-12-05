package com.demo.daangn.domain.chat2.entity;

import com.demo.daangn.global.dto.entity.BaseAuditEntity;

// @Table(
//     name = "chat_room_used_market_map",
//     indexes = {
//         @Index(name = "idx_chat_room_used_market_id", columnList = "used_market_id"),
//         @Index(name = "idx_chat_room_used_market_id", columnList = "chat_room_id")})
// @Entity
// @EqualsAndHashCode(callSuper = false)
// @Getter
// @ToString(callSuper = true)
// @SuperBuilder
public class ChatRoomUsedMarketEntity extends BaseAuditEntity {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "used_market_id", nullable = false)
    // private UsedMarket usedMarket;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "chat_room_id", nullable = false)
    // private ChatRoomEntity chatRoom;

}
