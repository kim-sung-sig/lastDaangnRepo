package com.demo.daangn.domain.chat.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.demo.daangn.domain.user.entity.DaangnUserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ChatRoomUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_Id", nullable = false)
    private DaangnUserEntity user;

    @ManyToOne
    @JoinColumn(name = "chatRoom_Id", nullable = false)
    private ChatRoomEntity chatRoom;

    @Column(name = "chatRorm_name", nullable = true)
    private String chatRoomName; // 유저마다 채팅방 이름을 바꿀수 있게..

    // 여기서 추가로 채팅알림 정지.. 가능하게
    @CreatedDate
    private LocalDateTime createDate;
}
