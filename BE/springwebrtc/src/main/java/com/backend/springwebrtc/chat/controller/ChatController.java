package com.backend.springwebrtc.chat.controller;

import com.backend.springwebrtc.chat.dto.ChatMessage;
import com.backend.springwebrtc.chat.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final RedisPublisher redisPublisher;

    @MessageMapping("/chat/global/mistake")
    public void handleGlobalMistake(ChatMessage message) {
        // Redis Pub/Sub에 메시지 발행
        redisPublisher.publish("global-events", message);
    }

    @MessageMapping("/chat/{roomId}")
    @SendTo("/subscribe/chat/{roomId}")
    public ChatMessage handleMessage(@DestinationVariable String roomId, ChatMessage message){
        logger.info("Message received in room {}: {}", roomId, message);
        return message;
    }


    @MessageMapping("/chat/{roomId}/join")
    @SendTo("/subscribe/chat/{roomId}")
    public ChatMessage joinRoom(@DestinationVariable String roomId, ChatMessage message) {
        message.setContent(message.getSender() + " has joined the room!");
        logger.info("User {} joined room {}", message.getSender(), roomId);
        return message;
    }

    @MessageMapping("/chat/{roomId}/leave")
    @SendTo("/subscribe/chat/{roomId}")
    public ChatMessage leaveRoom(@DestinationVariable String roomId, ChatMessage message) {
        message.setContent(message.getSender() + " has left the room!");
        logger.info("User {} leaved room {}", message.getSender(), roomId);
        return message;
    }
}
