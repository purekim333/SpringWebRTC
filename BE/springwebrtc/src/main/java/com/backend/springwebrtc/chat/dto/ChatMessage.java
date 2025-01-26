package com.backend.springwebrtc.chat.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ChatMessage {
    private UUID id;
    private String roomId;   // 방 ID
    private String sender;   // 보낸 사람
    private String content;  // 메시지 내용
}
