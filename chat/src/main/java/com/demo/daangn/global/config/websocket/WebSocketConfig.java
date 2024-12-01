package com.demo.daangn.global.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지를 보낼때
        registry.enableSimpleBroker("/sub");
        // 메시지 구독
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        String[] allowOrgins = {
            "http://127.0.0.0.1:8080","http://localhost:8080",
            "http://127.0.0.0.1:8081","http://localhost:8081",
            "http://127.0.0.0.1:3000","http://localhost:3000",
        };
        registry
                .addEndpoint("/ws")
                .setAllowedOrigins(allowOrgins)
                .withSockJS()
                // .setInterceptors(new CustomWebSocketInterceptors())
                ;
    }

}
