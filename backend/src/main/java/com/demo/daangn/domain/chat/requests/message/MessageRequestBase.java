package com.demo.daangn.domain.chat.requests.message;

import java.time.LocalDateTime;
import java.util.UUID;

import com.demo.daangn.domain.chat.enums.ChatMessageType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequestBase {

    private UUID chatRoomUuid;

    private ChatMessageType type;

    private LocalDateTime createdAt = LocalDateTime.now();

}
