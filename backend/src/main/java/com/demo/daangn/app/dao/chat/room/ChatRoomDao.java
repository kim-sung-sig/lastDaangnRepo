package com.demo.daangn.app.dao.chat.room;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.demo.daangn.app.dao.chat.room.jpa.ChatRoomRepository;
import com.demo.daangn.app.dao.chat.room.jpa.UserChatRoomRepository;
import com.demo.daangn.app.domain.chat.room.ChatRoom;
import com.demo.daangn.app.domain.chat.room.UserChatRoom;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatRoomDao {

    // private final ChatRoomMapper chatRoomMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    /* SELECT */
    public List<ChatRoom> selectChatRoomsByUserId(String userId) {
        return null;
    }

    /* INSERT */
    public ChatRoom saveChatRoom(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    public List<UserChatRoom> insertChatRoomUser(Collection<UserChatRoom> userChatRooms) {
        return userChatRoomRepository.saveAll(userChatRooms);
    }

    /* UPDATE */

}
