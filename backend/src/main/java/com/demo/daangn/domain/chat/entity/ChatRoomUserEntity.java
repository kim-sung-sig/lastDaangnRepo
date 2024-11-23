package com.demo.daangn.domain.chat.entity;

import com.demo.daangn.domain.user.entity.User;
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
    name = "user_chat_room_map",
    indexes = {
        @Index(name = "idx_user_chat_room_user_id", columnList = "user_id"),
        @Index(name = "idx_user_chat_room_chat_room_id", columnList = "chat_room_id")})
@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomUserEntity extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_Id", nullable = false)
    private ChatRoomEntity chatRoom;

    @Column(name = "chat_room_name")
    private String chatRoomName; // 유저마다 채팅방 이름을 바꿀수 있게..

    @Column(name = "pointer")
    private Long pointer; // 삭제시 마지막 커서

    @Column(name = "is_used", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Integer isUsed;

    public void updateChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    public void updatePointer(Long pointer) {
        this.pointer = pointer;
    }

}
