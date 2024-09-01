package com.demo.daangn.domain.chat.entity;

import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.global.dto.entity.BaseAuditEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "chat_message")
@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageEntity extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "roomId", nullable = false)
    private ChatRoomEntity room;

    @ManyToOne
    @JoinColumn(name = "sender", nullable = false)
    private DaangnUserEntity sender;

    private Integer types;
    
    private String content; // 파일저장시 여기에 파일 경로.. // 일정일시 여기에 YYYY-MM-DD HH:mm:ss

    private Integer readed;

}
