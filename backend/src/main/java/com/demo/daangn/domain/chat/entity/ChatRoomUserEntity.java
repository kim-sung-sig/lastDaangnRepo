package com.demo.daangn.domain.chat.entity;

import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.global.dto.entity.BaseAuditEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "chat_room_user")
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

    @ManyToOne
    @JoinColumn(name = "user_Id", nullable = false)
    private DaangnUserEntity user;

    @ManyToOne
    @JoinColumn(name = "chatRoom_Id", nullable = false)
    private ChatRoomEntity chatRoom;

    @Column(name = "chatRorm_name")
    private String chatRoomName; // 유저마다 채팅방 이름을 바꿀수 있게..

    @Column(name = "pointer")
    private Long pointer; // 삭제시 마지막 커서

    public void updateChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    public void updatePointer(Long pointer) {
        this.pointer = pointer;
    }

}
