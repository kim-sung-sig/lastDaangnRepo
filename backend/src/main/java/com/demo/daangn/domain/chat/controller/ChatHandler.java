package com.demo.daangn.domain.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.demo.daangn.domain.chat.dto.request.ChatMessageRequest;
import com.demo.daangn.domain.chat.dto.response.ChatMessageResponse;
import com.demo.daangn.domain.chat.entity.ChatMessageEntity;
import com.demo.daangn.domain.chat.entity.ChatRoomEntity;
import com.demo.daangn.domain.chat.enums.MessageType;
import com.demo.daangn.domain.chat.repository.ChatMessageRepository;
import com.demo.daangn.domain.chat.repository.ChatRoomRepository;
import com.demo.daangn.domain.chat.repository.ChatRoomUserRepository;
import com.demo.daangn.domain.event.service.EventPublisherService;
import com.demo.daangn.domain.user.entity.DaangnUserEntity;
import com.demo.daangn.domain.user.repository.user.DaangnUserRepository;
import com.demo.daangn.global.config.websocket.WebsocketChatRoomRegistry;
import com.demo.daangn.global.exception.AuthException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatHandler {
    
    // @Autowired
    private final SimpMessagingTemplate messagingTemplate;
    private final WebsocketChatRoomRegistry chatRoomRegistry;
    
    private final DaangnUserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final EventPublisherService eventPublisher;


    @MessageMapping("/chat/message")
    public ResponseEntity<?> message(@Payload ChatMessageRequest messageRequest, SimpMessageHeaderAccessor headerAccessor){
        try {
            log.info("메시지 받았음! msg => {}", messageRequest);
            ChatMessageResponse response = null;

            DaangnUserEntity sender = userRepository.findById(messageRequest.getSender())
                    .orElseThrow(() -> new EntityNotFoundException("not id"));

            ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(messageRequest.getChatRoomId())
                    .orElseThrow(() -> new EntityNotFoundException("not id"));

            chatRoomUserRepository.findByUserAndChatRoom(sender, chatRoomEntity)
                    .orElseThrow(() -> new AuthException("this is not your chatRoom"));

            // TODO 추후 메서드 빼기 + try-catch로 감싸기
            boolean result = switch( MessageType.getMessageType(messageRequest.getType()) ) {
                case ENTER -> { // enter chat room
                    log.debug("This is enter chat Message!");
                    try {
                        Long senderId = messageRequest.getSender();
                        Long roomId = messageRequest.getChatRoomId();
                        headerAccessor.getSessionAttributes().put("userId", senderId);
                        headerAccessor.getSessionAttributes().put("roomId", roomId);
                        chatRoomRegistry.addUser(roomId, senderId);
                        
                        log.info("RoomUserMapByRoomId => {}", chatRoomRegistry.getRoomUsers(roomId));
                        response = ChatMessageResponse.builder()
                                .type(messageRequest.getType())
                                .chatRoomId(messageRequest.getChatRoomId())
                                .sender(senderId)
                                .build();
                        
                        // 입장메시지 보내기
                        messagingTemplate.convertAndSend("/sub/chat/room/" + messageRequest.getChatRoomId(), response);
                        yield true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        yield false;
                    }
                }
                case TALK -> { // chat message
                    log.info("This is chat Message!");
                    ChatMessageEntity messageEntity = ChatMessageEntity.builder()
                            .types(messageRequest.getType())
                            .sender(sender)
                            .room(chatRoomEntity)
                            .readed(chatRoomRegistry.hasUser(chatRoomEntity.getId()) ? 0 : 1)
                            .content(messageRequest.getContent()).build();

                    chatMessageRepository.save(messageEntity);

                    response = new ChatMessageResponse(messageEntity);

                    // 메시지 전송!
                    messagingTemplate.convertAndSend("/sub/chat2/room/" + response.getChatRoomId(), response); // 채팅방에 메시지

                    eventPublisher.publishEvent(response); // 챗 알림 이벤트 발행!
                    yield true;
                }
                case LEAVE -> { // file message( 멀티파트로 받아야되나? )
                    throw new UnsupportedOperationException("Not supported yet.");
                }
                default -> false;
            };
    
            // TODO 추후 메서드 빼기 & void 타입 바꿔보기
            if (result) {
                log.info("메시지 전송 성공!");
            } else {
                log.info("메시지 전송 실패!");
            }

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
