package com.demo.daangn.app.domain.chat.room;

import java.time.LocalDateTime;
import java.util.UUID;

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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "user__dn_chat_room")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @Column(name = "chat_room_name")
    private String chatRoomName;

    @Column(name = "is_used", nullable = false)
    @Enumerated(EnumType.STRING)
    private IsUsedEnum isUsed;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "pointer")
    private LocalDateTime pointer;

    @PrePersist
    private void prePersist() {
        if(this.id == null) {
            this.id = UUID.randomUUID();
        }
        if(this.pointer == null) {
            this.pointer = LocalDateTime.now();
        }
        if(this.isUsed == null) {
            this.isUsed = IsUsedEnum.ENABLED;
        }
        if(this.createDate == null) {
            this.createDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    private void preUpdate() {
        this.updateDate = LocalDateTime.now();
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
