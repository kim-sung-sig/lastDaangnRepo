package com.demo.daangn.app.service.chat.message.request;

import java.time.LocalDateTime;
import java.util.UUID;

import com.demo.daangn.app.domain.chat.message.enums.ChatMessageType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequestBase {

    private UUID chatRoomId;

    private ChatMessageType type;

    private LocalDateTime createdAt = LocalDateTime.now();

}
