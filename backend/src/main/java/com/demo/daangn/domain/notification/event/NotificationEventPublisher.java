package com.demo.daangn.domain.notification.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.demo.daangn.domain.chat.dto.response.ChatMessageResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    /** 채팅메시지가 말생하면! */
    public void publishChatMessageEvent(ChatMessageResponse messageResponse) {
        ChatMessageEvent event = new ChatMessageEvent(messageResponse);
        applicationEventPublisher.publishEvent(event);
    }

    /*
    public void publishCommentEvent(CommentResponse commentResponse) {
        CommentEvent event = new CommentEvent(commentResponse);
        applicationEventPublisher.publishEvent(event);
    }
    */
}
