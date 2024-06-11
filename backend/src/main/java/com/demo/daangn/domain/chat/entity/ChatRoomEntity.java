package com.demo.daangn.domain.chat.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_room_cd", columnDefinition = "BINARY(16)", unique = true)
    private UUID chatRoomCd;

    // TODO UsedMarketEntity
    // @JoinColumn(name = "board_id")
    // @ManyToOne
    // private UsedMarketEntity usedMarketEntity;

    @CreatedDate
    private LocalDateTime createDate;
}
