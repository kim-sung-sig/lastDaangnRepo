package com.demo.daangn.domain.chat.entity;

import java.util.Set;
import java.util.UUID;

import com.demo.daangn.domain.chat.enums.ChatMessageType;
import com.demo.daangn.global.dto.entity.BaseAuditEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chatMessage", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatMessageReadStatus> readStatuses;

    // private Set<File> file; // 추후 FileEntity로 변경

    // @OneToOne(mappedBy = "chatMessage", fetch = FetchType.LAZY)
    @Column(name = "reference_message_id")
    private UUID references; // 답변, 참조 등

    @Column(name = "message_uuid", nullable = false, unique = true, updatable = false)
    private UUID messageUuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ChatMessageType type; // type에 따라서 text, image, video, audio, location, file, sticker, etc

    @Column(name = "content", length = 300)
    private String content;

    @Column(name = "big_content", columnDefinition = "TEXT")
    private String bigContent;

    @Column(name = "is_sent", nullable = false)
    private Boolean isSent;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @PrePersist
    public void prePersist() {
        if(this.messageUuid == null) {
            this.messageUuid = UUID.randomUUID();
        }
        if(this.isDeleted == null) {
            this.isDeleted = false;
        }
        if(this.isSent == null) {
            this.isSent = false;
        }
    }

}
