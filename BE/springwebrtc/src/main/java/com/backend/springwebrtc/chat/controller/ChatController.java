package com.backend.springwebrtc.chat.controller;

import com.backend.springwebrtc.chat.domain.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat/{roomId}")
    @SendTo("/subscribe/chat/{roomId}")
    public ChatMessage handleMessage(@DestinationVariable String roomId, ChatMessage message){
        return message;
    }


    @MessageMapping("/chat/{roomId}/join")
    @SendTo("/subscribe/chat/{roomId}")
    public ChatMessage joinRoom(@DestinationVariable String roomId, ChatMessage message) {
        message.setContent(message.getSender() + " has joined the room!");
        System.out.println("Join: " + message);
        return message;
    }

    @MessageMapping("/chat/{roomId}/leave")
    @SendTo("/subscribe/chat/{roomId}")
    public ChatMessage leaveRoom(@DestinationVariable String roomId, ChatMessage message) {
        message.setContent(message.getSender() + " has left the room!");
        System.out.println("Leave: " + message);
        return message;
    }
}
