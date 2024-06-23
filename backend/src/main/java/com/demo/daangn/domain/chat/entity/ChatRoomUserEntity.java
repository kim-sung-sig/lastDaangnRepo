package com.demo.daangn.domain.chat.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "chat_room_user")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "chatRorm_name")
    private String chatRoomName; // 유저마다 채팅방 이름을 바꿀수 있게..

    // 여기서 추가로 채팅알림 정지.. 가능하게
    @Column(name = "is_used")
    private Integer isUsed; // 삭제여부 -> 삭제했는데 이 채팅방에 메시지가 오면 isUsed = 1 설정해야댐!!!!

    @Column(name = "pointer")
    private Long pointer; // 삭제시 마지막 커서

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
