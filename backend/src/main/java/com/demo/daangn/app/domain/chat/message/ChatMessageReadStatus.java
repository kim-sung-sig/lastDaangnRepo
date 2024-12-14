package com.demo.daangn.app.domain.chat.message;

import java.util.UUID;

import com.demo.daangn.app.common.dto.entity.BaseAuditEntity;
import com.demo.daangn.app.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Table(name = "dn_chat_message_read_status")
@Entity
@Getter
@SuperBuilder
public class ChatMessageReadStatus extends BaseAuditEntity {

    @Id
    @Column(name = "id", updatable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    private ChatMessage chatMessage;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ChatMessageReadStatus(User readUser, ChatMessage chatMessage) {
        this.user = readUser;
        this.chatMessage = chatMessage;
    }

    @PrePersist
    private void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }

}
