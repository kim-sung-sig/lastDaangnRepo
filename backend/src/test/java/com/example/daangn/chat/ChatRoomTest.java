package com.example.daangn.chat;


import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.daangn.BackendApplication;
import com.demo.daangn.domain.chat.dto.request.ChatRoomCreateRequest;
import com.demo.daangn.domain.chat.dto.request.ChatRoomUpdateRequest;
import com.demo.daangn.domain.chat.dto.response.ChatRoomResponse;
import com.demo.daangn.domain.chat.service.ChatService;

@SpringBootTest(classes = BackendApplication.class)
public class ChatRoomTest {

    @Autowired
    private ChatService chatService;

    @Test
    public void createChatRoomTest() {
        ChatRoomCreateRequest req = new ChatRoomCreateRequest();
        req.setUserId(2L);
        Long chatRoomId = chatService.createChatRoom(1L, req);
        assertThat(chatRoomId, equalTo(1L));
    }

    @Test
    public void isAvailableChatRoomTest() {
        boolean result = chatService.isAvailableChatRoom(1L, 1L);

        assertThat(result, equalTo(true));
    }

    @Test
    public void leaveChatRoomTest() {
        chatService.leaveChatRoom(1L, 1L);

        boolean result = chatService.isAvailableChatRoom(1L, 1L);

        assertThat(result, equalTo(false));
    }

    @Test
    public void chatRoomCustomTest(){
        // isUsed = 0 이면 예외 던짐
        ChatRoomUpdateRequest req = new ChatRoomUpdateRequest();
        req.setChatRoomName("asfasgasg");
        chatService.updateChatRoom(1L, 1L, req); // 일단 예외가 터질수 잇음!
    }

    @Test
    public void getChatRoomById(){
        ChatRoomResponse res = chatService.getChatRoom(1L, 1L);
        assertNotEquals(null, res);
    }

}
