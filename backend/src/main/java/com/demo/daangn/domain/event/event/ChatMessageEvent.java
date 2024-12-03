package com.demo.daangn.domain.event.event;

import com.demo.daangn.domain.chat2.dto.response.ChatMessageResponse;

import lombok.Getter;

@Getter
public class ChatMessageEvent {

    private final ChatMessageResponse messageResponse;

    public ChatMessageEvent(ChatMessageResponse messageResponse){
        this.messageResponse = messageResponse;
    }

}
