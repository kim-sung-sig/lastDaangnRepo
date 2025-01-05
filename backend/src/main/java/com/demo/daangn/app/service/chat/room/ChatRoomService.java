package com.demo.daangn.app.service.chat.room;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.demo.daangn.app.common.exception.AuthException;
import com.demo.daangn.app.dao.chat.room.jpa.ChatRoomRepository;
import com.demo.daangn.app.dao.chat.room.jpa.UserChatRoomRepository;
import com.demo.daangn.app.dao.user.user.UserRepository;
import com.demo.daangn.app.domain.chat.room.ChatRoom;
import com.demo.daangn.app.domain.chat.room.UserChatRoom;
import com.demo.daangn.app.domain.user.User;
import com.demo.daangn.app.util.CommonUtil;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    // 1:1 채팅인 경우
    public ChatRoom findOrCreateChatRoom(UUID targetUserId) { // TODO : 반환 타입 수정
        User loginUser = CommonUtil.getLoginUser()
                .orElseThrow(() -> new AuthException("로그인 유저가 존재하지 않습니다."));

        // 1. 타겟 유저가 존재하는지 확인
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new EntityNotFoundException("타겟 유저가 존재하지 않습니다."));

        // 2. 채팅방이 이미 존재하는지 확인
        ChatRoom existingChatRoom = chatRoomRepository.findById(null)
                .orElse(null);

        // 2-1. 채팅방이 존재
        if (existingChatRoom != null) {
            log.info("채팅방이 이미 존재합니다 : {}", existingChatRoom);
            return existingChatRoom;
        }

        // 2-2. 채팅방이 존재하지 않음
        ChatRoom newChatRoom = ChatRoom.builder()
                .build();
        chatRoomRepository.save(newChatRoom);

        UserChatRoom loginUserChatRoom = UserChatRoom.builder()
                .user(loginUser)
                .chatRoom(newChatRoom)
                .build();
        UserChatRoom targetUserChatRoom = UserChatRoom.builder()
                .user(targetUser)
                .chatRoom(newChatRoom)
                .build();

        userChatRoomRepository.save(loginUserChatRoom);
        userChatRoomRepository.save(targetUserChatRoom);

        log.info("채팅방이 생성되었습니다 : {}", newChatRoom);

        return newChatRoom;
    }

    // 단체체팅 중도가입
    public void joinGroupChat() {

    }

    // 채팅방 정보 업데이트
    public void updateChatRoomInfo() {

    }

    // 채팅방 나가기
    public void leaveChatRoom() {

    }

}
