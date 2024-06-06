package com.demo.daangn.domain.chat.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.demo.daangn.domain.chat.dto.ChatMessageRequest;
import com.demo.daangn.domain.chat.dto.ChatMessageResponse;
import com.demo.daangn.domain.chat.entity.ChatMessageEntity;
import com.demo.daangn.domain.chat.entity.ChatRoomEntity;
import com.demo.daangn.domain.chat.repository.ChatMessageRepository;
import com.demo.daangn.domain.chat.repository.ChatRoomRepository;
import com.demo.daangn.domain.chat.repository.ChatRoomUserRepository;
import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.domain.user.repository.DaangnUserRepository;
import com.demo.daangn.global.config.websocket.WebscoketChatRoomRegistry;
import com.demo.daangn.global.exception.AuthException;
import com.demo.daangn.global.exception.EntityNotFoundException;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    
    // @Autowired
    private final SimpMessagingTemplate messagingTemplate;
    private final WebscoketChatRoomRegistry chatRoomRegistry;
    private final DaangnUserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatMessageRepository chatMessageRepository;

    @MessageMapping("/chat/message")
    public void message(HttpSession session, ChatMessageRequest messageRequest, SimpMessageHeaderAccessor headerAccessor){
        log.info("메시지 받았음! msg => {}", messageRequest);
        ChatMessageResponse response = null;

        DaangnUserEntity sender = userRepository.findById(messageRequest.getSender())
                .orElseThrow(() -> new EntityNotFoundException("not id"));

        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(messageRequest.getChatRoomId())
                .orElseThrow(() -> new EntityNotFoundException("not id"));

        chatRoomUserRepository.findByUserAndChatRoom(sender, chatRoomEntity)
                .orElseThrow(() -> new AuthException("this is not your chatRoom"));

        List<Long> receivedUserIds = chatRoomUserRepository.findUserIdByChatRoom(chatRoomEntity);
        receivedUserIds.removeIf(num -> num.equals(sender.getId()));

        if(messageRequest.getType() == 1){
            log.info("입장메시지 였음");
            Long senderId = messageRequest.getSender();
            Long roomId = messageRequest.getChatRoomId();
            headerAccessor.getSessionAttributes().put("userId", senderId);
            headerAccessor.getSessionAttributes().put("roomId", roomId);
            chatRoomRegistry.addUser(roomId, senderId);
            
			log.info("RoomUserMapByRoomId => {}", chatRoomRegistry.getRoomUsers(roomId));
            response = ChatMessageResponse.builder()
                    .type(1)
                    .sender(senderId)
                    .build();
            
            // 입장메시지 보내기
			messagingTemplate.convertAndSend("/sub/chat/room/" + messageRequest.getChatRoomId(), response); 
			return ;
        }

        // 진짜 메시지인 경우

        log.info("진짜 메시지임!");

        ChatMessageEntity messageEntity = ChatMessageEntity.builder()
                .type(messageRequest.getType())
                .sender(sender)
                .room(chatRoomEntity)
                .readed(chatRoomRegistry.hasUser(chatRoomEntity.getId()) ? 0 : 1)
                .content(messageRequest.getContent()).build();

        chatMessageRepository.save(messageEntity);

        response = new ChatMessageResponse(messageEntity);

        // 메시지 전송!
        messagingTemplate.convertAndSend("/sub/chat/alarm" + receivedUserIds.get(0), response); // 유저에게 알림 (현재는 1대1이라 한명만 남음)
        messagingTemplate.convertAndSend("/sub/chat2/room/" + response.getChatRoomId(), response); // 채팅방에 메시지

    }
}
