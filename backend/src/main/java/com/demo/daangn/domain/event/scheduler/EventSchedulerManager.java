package com.demo.daangn.domain.event.scheduler;

import org.springframework.stereotype.Component;

import com.demo.daangn.domain.event.repository.EventPublisherRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventSchedulerManager {

    private final EventPublisherRepository eventPublisherRepository;

    

}
