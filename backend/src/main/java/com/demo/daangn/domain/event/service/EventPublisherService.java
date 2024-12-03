package com.demo.daangn.domain.event.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.demo.daangn.domain.chat2.dto.response.ChatMessageResponse;
import com.demo.daangn.domain.event.event.ChatMessageEvent;
import com.demo.daangn.domain.event.repository.EventPublisherRepository;
import com.demo.daangn.domain.user.dto.event.UserSignUpEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventPublisherService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final EventPublisherRepository eventPublisherRepository;

    /** 채팅메시지가 발생하면! 이벤트를 발행하자! */
    public void publishEvent(ChatMessageResponse messageResponse) {
        // 이벤트 저장
        ChatMessageEvent event = new ChatMessageEvent(messageResponse);
        applicationEventPublisher.publishEvent(event); // 이벤트 발행!
    }

    /* 유저 로그인 이벤트가 발생 */
    public void publishEvent(UserSignUpEvent userSignUpEvent) {
        // 이벤트 저장
        applicationEventPublisher.publishEvent(userSignUpEvent); // 이벤트 발행!
    }

}
