package com.demo.daangn.global.config.websocket;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebsocketChatRoomRegistry {
    
    private Map< Long, Set<Long> > roomUserMap = new ConcurrentHashMap<>();

    public void addUser(Long roomId, Long userId) {
        roomUserMap.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet()).add(userId);
        log.info("roomUserMap -> {}", roomUserMap);
    }

    public void removeUserFromRoom(Long roomId, Long userId) {
        Set<Long> roomUsers = roomUserMap.get(roomId);
        if(roomUsers != null){
            roomUsers.remove(userId);
        }
        if (roomUsers.isEmpty()) {
            roomUserMap.remove(roomId);
        }
    }

    // 확인용
    public Map< Long, Set<Long> > getcheck(){
        return roomUserMap;
    }

    // 멀티채팅인 경우 아래 두 메서드를 수정하는 방향으로
    public Set<Long> getRoomUsers(Long roomId) {
        return roomUserMap.get(roomId);
    }
    /**  채팅방에 입장한 유저가 2명인지 확인 */
    public boolean hasUser(Long roomId) {  //int roomId, Integer userId
        return roomUserMap.containsKey(roomId) && roomUserMap.get(roomId).size() == 2 ;
        // 일단 채팅방 유저가 2명이면 true를 리턴
    }

}