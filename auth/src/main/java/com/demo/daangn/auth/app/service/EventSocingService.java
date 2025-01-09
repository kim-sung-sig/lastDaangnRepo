package com.demo.daangn.auth.app.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.demo.daangn.auth.app.dto.TestTransactionEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventSocingService {

    private final TestService2 testService2;

    @Async
    @EventListener
    // @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleEvent(TestTransactionEvent event) {
        log.info("[EventSocingService] handle event");
        log.info("[EventSocingService] transaction yn = {}", TransactionSynchronizationManager.isActualTransactionActive());
        // log.info("[EventSocingService] transaction name = {}", TransactionSynchronizationManager.getCurrentTransactionName());

        log.info("[EventSocingService] testService2.test");
        Map<String, Object> map = new HashMap<>();
        map.put("id", event.getId());
        map.put("message", event.getMessage());
        testService2.test(map);

        log.info("[EventSocingService] handle event done");
    }
}
