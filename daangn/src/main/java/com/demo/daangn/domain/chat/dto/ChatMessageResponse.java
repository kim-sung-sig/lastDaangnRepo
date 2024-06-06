package com.demo.daangn.domain.chat.dto;

import java.time.LocalDateTime;

import com.demo.daangn.domain.chat.entity.ChatMessageEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponse {

    public ChatMessageResponse(ChatMessageEntity e){
        this.id = e.getId();
        this.type = e.getType();
        this.chatRoomId = e.getRoom().getId();
        this.sender = e.getSender().getId();
        this.name = e.getSender().getNickName();
        this.profile = e.getSender().getUserProfile();
        this.readed = e.getReaded();
        this.createDate = e.getCreateDate();
    }

    private Long id;

    private Integer type;

    private Long chatRoomId;

    private Long sender;

    private String name;

    private String profile;

    private Integer readed;

    private LocalDateTime createDate;

}
