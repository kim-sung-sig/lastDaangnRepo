package com.demo.daangn.app.domain.chat.room;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.demo.daangn.app.common.dto.entity.BaseAuditEntity;
import com.demo.daangn.app.common.enums.IsUsedEnum;
import com.demo.daangn.app.domain.chat.message.ChatMessage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Table(name = "dn_chat_room")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ChatRoom extends BaseAuditEntity {

    @Id
    @Column(name = "chat_room_id")
    private UUID id;

    @JoinColumn(name = "last_message_id")
    @OneToOne(fetch = FetchType.LAZY)
    private ChatMessage lastMessage;

    @Column(name = "last_message_date")
    private LocalDateTime lastMessageDate;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY)
    private Set<UserChatRoom> chatRoomUsers;

    @Column(name = "is_used")
    @Enumerated(EnumType.STRING)
    private IsUsedEnum isUsed;

    @PrePersist
    private void prePersist() {
        if(this.id == null) {
            this.id = UUID.randomUUID();
        }
        if(this.isUsed == null) {
            this.isUsed = IsUsedEnum.ENABLED;
        }
        if(this.lastMessageDate == null) {
            this.lastMessageDate = LocalDateTime.now();
        }
    }

}
