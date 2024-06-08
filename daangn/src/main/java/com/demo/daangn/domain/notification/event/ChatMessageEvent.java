package com.demo.daangn.domain.notification.event;

import com.demo.daangn.domain.chat.dto.ChatMessageResponse;

import lombok.Getter;

@Getter
public class ChatMessageEvent {

    private final ChatMessageResponse messageResponse;

    public ChatMessageEvent(ChatMessageResponse messageResponse){
        this.messageResponse = messageResponse;
    }

}
