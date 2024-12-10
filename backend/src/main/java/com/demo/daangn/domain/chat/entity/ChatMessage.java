package com.demo.daangn.domain.chat.entity;

import java.util.Set;
import java.util.UUID;

import com.demo.daangn.domain.chat.enums.ChatMessageType;
import com.demo.daangn.domain.user.entity.User;
import com.demo.daangn.global.dto.entity.BaseAuditEntity;
import com.demo.daangn.global.enums.IsUsedEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Table(name = "dn_chat_message")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ChatMessage extends BaseAuditEntity {

    @Id
    @Column(name = "id", updatable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @OneToMany(mappedBy = "chatMessage", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatMessageReadStatus> readStatuses;

    // private Set<File> file; // 추후 FileEntity로 변경

    // @OneToOne(mappedBy = "chatMessage", fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_message_id", nullable = true)
    private ChatMessage references; // 답변, 참조 등

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ChatMessageType type; // type에 따라서 text, image, video, audio, location, file, sticker, etc

    @Column(name = "content", length = 300)
    private String content;

    @Column(name = "big_content", columnDefinition = "TEXT")
    private String bigContent;

    @Column(name = "is_sent", nullable = false)
    private Boolean isSent;

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
        if(this.isSent == null) {
            this.isSent = false;
        }
    }

}
