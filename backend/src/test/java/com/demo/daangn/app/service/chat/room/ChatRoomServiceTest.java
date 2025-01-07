package com.demo.daangn.app.service.chat.room;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.demo.daangn.app.dao.chat.room.jpa.ChatRoomRepository;
import com.demo.daangn.app.dao.user.user.UserRepository;
import com.demo.daangn.app.domain.chat.room.ChatRoom;
import com.demo.daangn.app.domain.chat.room.UserChatRoom;
import com.demo.daangn.app.domain.user.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ChatRoomServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    @Transactional
    void testAddUserToChatRoom() {
        // given
        List<User> users = userRepository.findAll();
        log.debug("[testAddUserToChatRoom] users: {}", users);

        ChatRoom chatRoom = ChatRoom.builder().build();
        chatRoomRepository.save(chatRoom);

        users.forEach(user -> chatRoom.addUser(
            UserChatRoom.builder()
                    .user(user)
                    .chatRoom(chatRoom)
                    .build()));

        log.debug("[testAddUserToChatRoom] chatRoom: {}", chatRoom);

        chatRoomRepository.save(chatRoom);

        chatRoomRepository.findById(chatRoom.getId())
                .ifPresent(c -> log.debug("[testAddUserToChatRoom] findById chatRoom: {}", c));

        chatRoomRepository.findById(chatRoom.getId())
                .ifPresent(c -> log.debug("[testAddUserToChatRoom] findByIdWithUserChatRooms chatRoom: {}", c));
        log.debug("[testAddUserToChatRoom]");
        chatRoomRepository.findAll().forEach(c -> log.debug("[testAddUserToChatRoom] chatRoom: {}", c));
        // when
        // then
    }
}
