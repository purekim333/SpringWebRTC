package com.backend.springwebrtc.room.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/subscribe"); // 메시지를 받을 경로
        config.setApplicationDestinationPrefixes("/publish"); // 메시지를 보낼 경로
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // WebSocket 엔드포인트
                .setAllowedOrigins("http://localhost:5173") // Vue.js 프론트엔드 URL
                .withSockJS();// SockJS 지원 활성화
    }
}
