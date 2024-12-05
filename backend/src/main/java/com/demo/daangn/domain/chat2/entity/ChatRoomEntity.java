package com.demo.daangn.domain.chat2.entity;

import com.demo.daangn.global.dto.entity.BaseAuditEntity;

// @Table(name = "daangn_chat_room")
// @Entity
// @EqualsAndHashCode(callSuper = false)
// @Getter
// @ToString(callSuper = true)
// @SuperBuilder
public class ChatRoomEntity extends BaseAuditEntity {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;

    // @Column(name = "chat_room_cd", columnDefinition = "BINARY(16)", unique = true)
    // private String chatRoomCd;

    // @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY)
    // private List<ChatRoomUserEntity> chatRoomUsers;

}
