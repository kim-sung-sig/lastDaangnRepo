package com.demo.daangn.domain.chat.components;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** 소캣 생성 소멸 감지 리스너 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final WebsocketChatRoomRegistry chatRoomRegistry;

    // 웹소캣이 연결되었을 때 감지
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        log.info("WebSocket connection opened");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String webSocketSessionId = headerAccessor.getSessionId();
        log.info("Received a new web socket connection, sessionId: {}", webSocketSessionId);
    }

    // 웹소캣이 끊김을 감지
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("WebSocket connection closed");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = headerAccessor.getSessionId();
        log.info("Web socket connection closed, sessionId: {}", sessionId);

        UUID roomId = UUID.fromString(headerAccessor.getSessionAttributes().get("roomId").toString());
        UUID userId = UUID.fromString(headerAccessor.getSessionAttributes().get("userId").toString());
        
        if(userId != null && roomId != null){
            chatRoomRegistry.removeUserFromRoom(roomId, userId);
            log.info("userId => {}", userId);
            log.info("roomId => {}", roomId);

            if(chatRoomRegistry.getRoomUsers(roomId) != null && chatRoomRegistry.getRoomUsers(roomId).contains(userId)) {
                chatRoomRegistry.getRoomUsers(roomId).remove(userId);
            }
        }
        // log.info("roomUserMap -> {}", chatRoomRegistry.getcheck());
    }

}
