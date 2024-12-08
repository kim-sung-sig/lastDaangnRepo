package com.example.daangn.chat;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.daangn.BackendApplication;
import com.demo.daangn.domain.chat.components.WebsocketChatRoomRegistry;

@SpringBootTest(classes = BackendApplication.class)
public class WebsocketChatRoomRegistryTest {

    @Autowired
    private WebsocketChatRoomRegistry websocketChatRoomRegistry;

    @Test
    public void addUserTest() {
        websocketChatRoomRegistry.addUser(1L, 1L);
        websocketChatRoomRegistry.addUser(1L, 2L);
        websocketChatRoomRegistry.addUser(1L, 3L);
        websocketChatRoomRegistry.addUser(1L, 4L);
        websocketChatRoomRegistry.addUser(1L, 5L);
        websocketChatRoomRegistry.addUser(2L, 1L);
        websocketChatRoomRegistry.addUser(2L, 2L);
        websocketChatRoomRegistry.addUser(2L, 3L);
        websocketChatRoomRegistry.addUser(2L, 4L);
        websocketChatRoomRegistry.addUser(2L, 5L);
    }

    @Test
    public void removeUserFromRoomTest() {
        websocketChatRoomRegistry.removeUserFromRoom(1L, 1L);
        websocketChatRoomRegistry.removeUserFromRoom(1L, 2L);
        websocketChatRoomRegistry.removeUserFromRoom(1L, 3L);
        websocketChatRoomRegistry.removeUserFromRoom(1L, 4L);
        websocketChatRoomRegistry.removeUserFromRoom(1L, 5L);
    }

    @Test
    public void getRoomUsersTest() {
        Set<Long> set = websocketChatRoomRegistry.getRoomUsers(1L);
        set.forEach(System.out::println);
    }

}
