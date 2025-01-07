package com.demo.daangn.app.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{

    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry registry) {
        // 메시지를 구독할 때 (서버가 클라이언트에게 메시지를 보낼 때)
        registry.enableSimpleBroker("/sub");
        // 클라이언트가 메시지를 퍼블리시할 때 (클라이언트가 메시지를 보낼 때)
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
        registry
                .addEndpoint("/api/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS()
                // .setInterceptors(new CustomWebSocketInterceptors())
                ;
    }

}
