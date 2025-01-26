package com.backend.springwebrtc.chat.controller;

import com.backend.springwebrtc.chat.domain.entity.Room;
import com.backend.springwebrtc.chat.domain.request.CreateRoomRequest;
import com.backend.springwebrtc.chat.domain.request.JoinRoomRequest;
import com.backend.springwebrtc.chat.domain.request.LeaveRoomRequest;
import com.backend.springwebrtc.chat.domain.request.UserRoomRequest;
import com.backend.springwebrtc.chat.domain.response.BaseResponse;
import com.backend.springwebrtc.chat.domain.response.RoomResponse;
import com.backend.springwebrtc.chat.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    /**
     * 방 생성
     */
    @PostMapping("rooms/")
    public ResponseEntity<BaseResponse<RoomResponse>> createRoom(@RequestBody CreateRoomRequest request) {
        Room createdRoom = roomService.createRoom(
                request.getTitle(),
                request.getPassword(),
                request.getNickname()
        );
        RoomResponse response = new RoomResponse(createdRoom);
        return ResponseEntity.ok(BaseResponse.success("success", response));
    }

    /**
     * 방 참여
     */
    @PostMapping("rooms/{roomId}/join/")
    public ResponseEntity<BaseResponse<RoomResponse>> joinRoom(
            @PathVariable Long roomId,
            @RequestBody JoinRoomRequest request
    ) {
        try {
            Room updatedRoom = roomService.joinRoom(roomId, request.getNickname(), request.getPassword());
            RoomResponse response = new RoomResponse(updatedRoom);
            return ResponseEntity.ok(BaseResponse.success("success", response));
        } catch (IllegalArgumentException e) {
            // 비밀번호 오류 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(BaseResponse.error("비밀번호가 일치하지 않습니다."));
        }
    }

    /**
     * 방 나가기
     */
    @PostMapping("rooms/{roomId}/leave/")
    public ResponseEntity<BaseResponse<RoomResponse>> leaveRoom(
            @PathVariable Long roomId,
            @RequestBody LeaveRoomRequest request
    ) {
        roomService.leaveRoom(roomId, request.getNickname());
        return ResponseEntity.ok(BaseResponse.success("success",null));
    }

    /**
     * 방 목록 조회
     */
    @GetMapping("rooms/")
    public ResponseEntity<BaseResponse<List<RoomResponse>>> getRooms() {
        List<Room> rooms = roomService.getRooms();
        List<RoomResponse> responseList = rooms.stream()
                .map(r -> new RoomResponse(r))
                .collect(Collectors.toList());
        return ResponseEntity.ok(BaseResponse.success("success", responseList));
    }

    /**
     * 방 단건 조회
     */
    @GetMapping("rooms/{roomId}/")
    public ResponseEntity<BaseResponse<RoomResponse>> searchRoom(@PathVariable Long roomId) {
        Room room = roomService.getRoom(roomId);
        RoomResponse response = new RoomResponse(room);
        return ResponseEntity.ok(BaseResponse.success("success", response));
    }
}
