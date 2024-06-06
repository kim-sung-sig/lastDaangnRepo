package com.demo.daangn.domain.alarm.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.demo.daangn.domain.chat.dto.ChatMessageResponse;

@Component
public class NotificationEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public NotificationEventPublisher(ApplicationEventPublisher applicationEventPublisher){
        this.applicationEventPublisher = applicationEventPublisher;
    }


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
