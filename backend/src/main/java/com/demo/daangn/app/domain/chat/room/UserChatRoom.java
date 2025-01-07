package com.demo.daangn.app.domain.chat.room;

import java.time.LocalDateTime;
import java.util.UUID;

import com.demo.daangn.app.common.dto.entity.BaseAuditEntity;
import com.demo.daangn.app.common.enums.IsUsedEnum;
import com.demo.daangn.app.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Table(name = "user__dn_chat_room")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class UserChatRoom extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    @ToString.Exclude
    private ChatRoom chatRoom;

    @Column(name = "chat_room_name")
    private String chatRoomName;

    @Column(name = "is_used", nullable = false)
    @Enumerated(EnumType.STRING)
    private IsUsedEnum isUsed;

    @Column(name = "pointer")
    private LocalDateTime pointer;

    @PrePersist
    private void prePersist() {
        // if(this.id == null) {
        //     this.id = UUID.randomUUID();
        // }
        if(this.pointer == null) {
            this.pointer = LocalDateTime.now();
        }
        if(this.isUsed == null) {
            this.isUsed = IsUsedEnum.ENABLED;
        }
    }

    public void updateChatRoomName(String newChatRoomName) {
        this.chatRoomName = newChatRoomName;
    }

    public void recreate() {
        this.isUsed = IsUsedEnum.ENABLED;
        this.chatRoomName = null;
        this.pointer = LocalDateTime.now();
    }

    public void delete() {
        this.isUsed = IsUsedEnum.DISABLED;
    }

}
