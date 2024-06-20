package com.demo.daangn.domain.notification.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.demo.daangn.domain.chat.dto.response.ChatMessageResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    /** 채팅메시지가 발생하면! 이벤트를 발행하자! */
    public void publishChatMessageEvent(ChatMessageResponse messageResponse) {
        ChatMessageEvent event = new ChatMessageEvent(messageResponse);
        applicationEventPublisher.publishEvent(event); // 이벤트 발행!
    }

    /*
    public void publishCommentEvent(CommentResponse commentResponse) {
        CommentEvent event = new CommentEvent(commentResponse);
        applicationEventPublisher.publishEvent(event);
    }
    */
}
