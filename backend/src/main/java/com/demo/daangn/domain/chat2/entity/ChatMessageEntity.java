package com.demo.daangn.domain.chat2.entity;

import com.demo.daangn.global.dto.entity.BaseAuditEntity;

// @Table(
//     name = "daangn_chat_message",
//     indexes = {
//         @Index(name = "idx_daangn_chat_message_room_id", columnList = "roomId"),
//         @Index(name = "idx_daangn_chat_message_sender", columnList = "sender")})
// @Entity
// @EqualsAndHashCode(callSuper = false)
// @Getter
// @ToString(callSuper = true)
// @SuperBuilder
public class ChatMessageEntity extends BaseAuditEntity {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;

    // @ManyToOne
    // @JoinColumn(name = "roomId", nullable = false)
    // private ChatRoomEntity room;

    // @ManyToOne
    // @JoinColumn(name = "sender", nullable = false)
    // private User sender;

    // private Integer types;

    // private String content; // 파일저장시 여기에 파일 경로.. // 일정일시 여기에 YYYY-MM-DD HH:mm:ss

    // private Integer readed;

    // @Column(name = "is_used", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    // private Integer isUsed;

    // public void delete() {
    //     this.isUsed = 0;
    // }

}
