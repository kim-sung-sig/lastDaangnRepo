package com.example.daangn.chat;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.daangn.BackendApplication;
import com.demo.daangn.domain.chat.repository.ChatRoomRepository;
import com.demo.daangn.domain.chat.repository.ChatRoomUserRepository;
import com.demo.daangn.domain.chat.service.ChatService;
import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.domain.user.repository.DaangnUserRepository;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest(classes = BackendApplication.class)
public class ChatControllerTest {

    @Autowired
    private ChatService chatService;
    @Autowired
    private DaangnUserRepository userRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatRoomUserRepository chatRoomUserRepository;
    
    
    @Test
    void testIsAvailableChatRoom_WhenUserAndChatRoomUNExistAndIsUsed() {
        // 1. 채팅방을 생성해보는 테스트! 첫번째니가 chatRoomId를 1로 설정될것임
        // 2. 다음번에 생성해도 1이여야함!
        Long user1Id = 2L;
        Long user2Id = 1L;
        Long chatRoomId = 1L;

        DaangnUserEntity user1 = userRepository.findById(user1Id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
        DaangnUserEntity user2 = userRepository.findById(user2Id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
        // 1. 기존 체팅방이 있는지 확인!
        List<Long> chatRoomsForUser1 = chatRoomUserRepository.findByUser(user1);
        List<Long> chatRoomsForUser2 = chatRoomUserRepository.findByUser(user2);
        Long result = chatService.createChatRoom(user1Id, user2Id);

        assertEquals(chatRoomId, result); // chatRoomId가 1로 설정될것임
    }

}
