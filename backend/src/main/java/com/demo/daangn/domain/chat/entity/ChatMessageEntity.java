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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "roomId", nullable = false)
    private ChatRoomEntity room;

    @ManyToOne
    @JoinColumn(name = "sender", nullable = false)
    private DaangnUserEntity sender;

    private Integer type;
    
    private String content; // 파일저장시 여기에 파일 경로.. // 일정일시 여기에 YYYY-MM-DD HH:mm:ss

    private Integer readed;

    @Column(name = "is_used", columnDefinition = "TINYINT(1) default 1")
    private Integer isUsed; // 삭제여부

    @CreatedDate
    private LocalDateTime createDate;

    //TODO 쳇 메시지 수정??
}
