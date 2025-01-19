package com.backend.springwebrtc.chat.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class Room {
    private String roomId;   // 방 ID
    private String name; // 방 이름

    public Room(String name) {
        this.roomId = UUID.randomUUID().toString();
        this.name = name;
    }
}
