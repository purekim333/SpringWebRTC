package com.backend.springwebrtc.room.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisMessageSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;


    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(pattern);
        String body = new String(message.getBody());

        // 모든 Redis 채널의 메시지를 WebSocket 브로커로 전달
        messagingTemplate.convertAndSend("/subscribe/global", body);
    }
}
