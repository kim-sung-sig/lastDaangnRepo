package com.demo.daangn.app.service.chat.room.component;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
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

    public void addUser(UUID roomId, UUID userId) {
        String key = getRoomKey(roomId);
        redisTemplate.opsForSet().add(key, userId);
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);
        log.info("채팅방 ROOM {}에 USER {} 입장", roomId, userId);
    }

    public void removeUserFromRoom(UUID roomId, UUID userId) {
        String key = getRoomKey(roomId);
        redisTemplate.opsForSet().remove(key, userId);
        log.info("채팅방 ROOM {}에서 USER {} 나감", roomId, userId);
        if(redisTemplate.opsForSet().size(key) == 0){
            redisTemplate.delete(key);
            log.info("채팅방 ROOM {} 삭제", roomId);
        }
    }

    public Set<UUID> getRoomUsers(UUID roomId) {
        return redisTemplate.opsForSet().members(getRoomKey(roomId)).stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(o -> UUID.fromString(o))
                .collect(Collectors.toSet());
    }

    private String getRoomKey(UUID roomId) {
        return CHAT_ROOM_USERS + roomId.toString();
    }

}