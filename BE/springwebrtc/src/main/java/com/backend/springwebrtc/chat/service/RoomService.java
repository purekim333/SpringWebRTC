package com.backend.springwebrtc.chat.service;

import com.backend.springwebrtc.chat.domain.Room;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    private final List<Room> rooms = new ArrayList<>(); // 메모리 내 저장소

    public Room createRoom(String name) {
        Room room = new Room(name); // roomId 포함 생성
        rooms.add(room);
        return room; // 생성된 Room 반환
    }

    public List<Room> getAllRooms() {
        return rooms; // 모든 Room 반환
    }
}
