package com.demo.daangn.auth.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService2 {

    @Transactional
    public void test(Map<String, Object> map) {
        log.info("[TestService2] test");

        log.info("[TestService2] map id = {}", map.get("id"));
        log.info("[TestService2] map message = {}", map.get("message"));
        log.info("[TestService2] transaction yn = {}",  TransactionSynchronizationManager.isActualTransactionActive());
        log.info("[TestService2] transaction name = {}", TransactionSynchronizationManager.getCurrentTransactionName());


        log.info("[TestService2] test done");
    }

}
