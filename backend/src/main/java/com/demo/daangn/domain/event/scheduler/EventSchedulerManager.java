package com.demo.daangn.domain.event.scheduler;

import org.springframework.stereotype.Component;

import com.demo.daangn.domain.event.repository.EventPublisherRepository;

import lombok.RequiredArgsConstructor;

/*
 * 이벤트 재발송 스케줄러를 관리하는 클래스
 */
@Component
@RequiredArgsConstructor
public class EventSchedulerManager {

    private final EventPublisherRepository eventPublisherRepository;

    

}
