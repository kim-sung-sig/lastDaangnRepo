package com.demo.daangn.global.config.websocket;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebSocketUserRegistry {

    // ConcurrentHashSet
    private Set<Long> loginUsers = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public void addUser(Long userId){
        log.info("userId => {} 입장", userId);
        loginUsers.add(userId);
    }

    public void removeUser(Long userId){
        log.info("userId => {} 퇴장", userId);
        loginUsers.remove(userId);
    }
}
