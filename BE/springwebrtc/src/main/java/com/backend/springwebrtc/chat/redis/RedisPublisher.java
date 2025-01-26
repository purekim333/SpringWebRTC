package com.backend.springwebrtc.chat.redis;

import com.backend.springwebrtc.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(String channel, ChatMessage message) {

        redisTemplate.convertAndSend(channel, message);
    }
}
