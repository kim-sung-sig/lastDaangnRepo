package com.demo.daangn.auth.app.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.demo.daangn.auth.app.dto.TestTransactionEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void test() throws Exception {
        log.info("[TestService] test");
        log.info("[TestService] transaction yn = {}",  TransactionSynchronizationManager.isActualTransactionActive());
        log.info("[TestService] transaction name = {}", TransactionSynchronizationManager.getCurrentTransactionName());
        log.info("[TestService] publish event");
        applicationEventPublisher.publishEvent(new TestTransactionEvent(this, 1, "test message"));
        log.info("[TestService] publish event done");
        log.info("[TestService] test done");
    }

}
