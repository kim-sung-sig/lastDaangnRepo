package com.demo.daangn.app.config.websocket;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.demo.daangn.app.service.chat.room.component.WebsocketChatRoomRegistry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** 소캣 생성 소멸 감지 리스너 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final WebsocketChatRoomRegistry chatRoomRegistry;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 웹소켓 연결이 열렸을 때 감지
     * @param event websocket session connect event
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        String methodName = "handleWebSocketConnectListener";
        try {
            logger.info("[{}] WebSocket connection opened", methodName);
            StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
            String webSocketSessionId = headerAccessor.getSessionId();
            logger.info("[{}] Received a new web socket connection, sessionId: {}", methodName, webSocketSessionId);
        } catch (Exception e) {
            logger.error("[{}] Error", methodName, e);
        }
    }

    /**
     * 웹소켓 연결이 끊어졌을 때 감지
     * @param event websocket session disconnect event
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String methodName = "handleWebSocketDisconnectListener";
        try {

            logger.info("[{}] WebSocket connection closed", methodName);
            StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

            String sessionId = headerAccessor.getSessionId();
            logger.info("[{}] Web socket connection closed, sessionId: {}", methodName, sessionId);

            Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();

            if(sessionAttributes == null) return;

            UUID roomId = UUID.fromString(sessionAttributes.get("roomId").toString());
            UUID userId = UUID.fromString(sessionAttributes.get("userId").toString());

            if(userId != null && roomId != null){
                chatRoomRegistry.removeUserFromRoom(roomId, userId);
            }

        } catch (Exception e) {
            logger.error("[{}] Error", methodName, e);
        }
    }

}
