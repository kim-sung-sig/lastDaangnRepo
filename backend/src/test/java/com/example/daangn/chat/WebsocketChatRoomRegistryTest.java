package com.example.daangn.chat;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.daangn.BackendApplication;
import com.demo.daangn.domain.chat.components.WebsocketChatRoomRegistry;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(classes = BackendApplication.class)
public class WebsocketChatRoomRegistryTest {

    @Autowired
    private WebsocketChatRoomRegistry websocketChatRoomRegistry;

    @Test
    public void roomTest() {
        UUID roomId = UUID.randomUUID();
        log.debug("roomId: {}", roomId);
        
        // add user
        Set<UUID> userIdSet = IntStream.range(0, 10).mapToObj(i -> UUID.randomUUID()).collect(Collectors.toSet());
        for(UUID userId : userIdSet) {
            websocketChatRoomRegistry.addUser(roomId, userId);
        }

        // check user
        Set<UUID> roomUser1 = websocketChatRoomRegistry.getRoomUsers(roomId);
        log.debug("roomUser1: {}", roomUser1);

        // remove user
        UUID removeUserId = userIdSet.iterator().next();
        log.debug("removeUserId: {}", removeUserId);
        websocketChatRoomRegistry.removeUserFromRoom(roomId, removeUserId);

        // check user
        Set<UUID> roomUser2 = websocketChatRoomRegistry.getRoomUsers(roomId);
        log.debug("roomUser2: {}", roomUser2);

    }

    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();

        System.out.println(uuid);

        String uuidStr = uuid.toString();
        System.out.println(uuidStr);

        UUID uuid2 = UUID.fromString(uuidStr);
        System.out.println(uuid2);

        System.out.println(uuid.equals(uuid2));
    }
}
