package com.demo.daangn.domain.chat.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.demo.daangn.domain.chat.enums.ChatMessageType;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chatMessage", fetch = FetchType.LAZY)
    private Set<ChatMessageReadStatus> readStatuses;

    // private Set<File> file; // 추후 FileEntity로 변경

    @Column(name = "message_uuid", nullable = false, unique = true, updatable = false)
    private UUID messageUuid;

    @Enumerated(EnumType.STRING)
    private ChatMessageType type; // type에 따라서 text, image, video, audio, location, file, sticker, etc

    private Boolean isSent;

    @Column(name = "create_date")
    private LocalDateTime createdDate;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @PrePersist
    public void prePersist() {
        if(this.messageUuid == null) {
            this.messageUuid = UUID.randomUUID();
        }
        if(this.createdDate == null) {
            this.createdDate = LocalDateTime.now();
        }
        if(this.isDeleted == null) {
            this.isDeleted = false;
        }
    }


}
