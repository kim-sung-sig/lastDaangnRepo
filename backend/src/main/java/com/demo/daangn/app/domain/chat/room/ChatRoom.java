package com.demo.daangn.app.domain.chat.room;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.demo.daangn.app.common.enums.IsUsedEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "dn_chat_room")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @Column(name = "chat_room_id")
    private UUID id;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY)
    private Set<UserChatRoom> chatRoomUsers;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "is_used")
    @Enumerated(EnumType.STRING)
    private IsUsedEnum isUsed;

    @PrePersist
    private void prePersist() {
        if(this.id == null) {
            this.id = UUID.randomUUID();
        }
        if(this.createDate == null) {
            this.createDate = LocalDateTime.now();
        }
        if(this.isUsed == null) {
            this.isUsed = IsUsedEnum.ENABLED;
        }
    }
}
