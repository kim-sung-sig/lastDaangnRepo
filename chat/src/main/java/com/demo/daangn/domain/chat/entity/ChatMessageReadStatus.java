package com.demo.daangn.domain.chat.entity;

import java.time.LocalDateTime;

import com.demo.daangn.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Table(name = "chat_message_read_status")
@Entity
@Getter
@AllArgsConstructor
public class ChatMessageReadStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    private ChatMessage chatMessage;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "read_date")
    private LocalDateTime readDate;

    public ChatMessageReadStatus(User readUser, ChatMessage chatMessage) {
        this.user = readUser;
        this.chatMessage = chatMessage;
    }

    @PrePersist
    public void prePersist() {
        if(this.readDate == null) {
            this.readDate = LocalDateTime.now();
        }
    }

}
