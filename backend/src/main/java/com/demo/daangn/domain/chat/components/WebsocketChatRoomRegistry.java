package com.demo.daangn.domain.chat.components;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebsocketChatRoomRegistry {

    private final RedisTemplate<String, Object> redisTemplate;

    private final String CHAT_ROOM_USERS = "chatRoomUsers:";

    public void addUser(Long roomId, Long userId) {
        String key = CHAT_ROOM_USERS + roomId;
        redisTemplate.opsForSet().add(key, userId);
        log.info("채팅방 ROOM {}에 USER {} 입장", roomId, userId);
    }

    public void removeUserFromRoom(Long roomId, Long userId) {
        String key = CHAT_ROOM_USERS + roomId;
        redisTemplate.opsForSet().remove(key, userId);
        log.info("채팅방 ROOM {}에서 USER {} 나감", roomId, userId);
        if(redisTemplate.opsForSet().size(key) == 0){
            redisTemplate.delete(key);
            log.info("채팅방 ROOM {} 삭제", roomId);
        }

    }

    public Set<Long> getRoomUsers(Long roomId) {
        return redisTemplate.opsForSet().members(CHAT_ROOM_USERS + roomId).stream()
                .map(o -> Long.parseLong(o.toString()))
                .collect(Collectors.toSet());
    }

}