package com.backend.springwebrtc.room.controller;

import com.backend.springwebrtc.openvidu.service.OpenViduService;
import com.backend.springwebrtc.room.domain.entity.Room;
import com.backend.springwebrtc.room.domain.request.CreateRoomRequest;
import com.backend.springwebrtc.room.domain.request.JoinRoomRequest;
import com.backend.springwebrtc.room.domain.request.LeaveRoomRequest;
import com.backend.springwebrtc.room.domain.response.BaseResponse;
import com.backend.springwebrtc.room.domain.response.CreateRoomResponse;
import com.backend.springwebrtc.room.domain.response.JoinRoomResponse;
import com.backend.springwebrtc.room.domain.response.RoomResponse;
import com.backend.springwebrtc.room.service.RoomService;

import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final OpenViduService openViduService;


    /**
     * 방 생성
     */
    @PostMapping("/rooms")
    public ResponseEntity<BaseResponse<CreateRoomResponse>> createRoom(@RequestBody @Valid CreateRoomRequest request)
            throws OpenViduJavaClientException, OpenViduHttpException {
        Room createdRoom = roomService.createRoom(
                request.getTitle(),
                request.getPassword(),
                request.getNickname()
        );

        String hostName = request.getNickname(); // 생성자
        String sessionId = String.valueOf(createdRoom.getRoomId()); //세션 아이디

        openViduService.createSession(sessionId);
        String token = openViduService.generateToken(sessionId, hostName);

        CreateRoomResponse response = new CreateRoomResponse(createdRoom, token);
        return ResponseEntity.ok(BaseResponse.success("success", response));
    }

    /**
     * 방 참여
     */
    @PostMapping("/rooms/{roomId}/join")
    public ResponseEntity<BaseResponse<JoinRoomResponse>> joinRoom(
            @PathVariable Long roomId,
            @RequestBody @Valid JoinRoomRequest request
    ) throws OpenViduJavaClientException, OpenViduHttpException {
        Room updatedRoom = roomService.joinRoom(roomId, request.getNickname(), request.getPassword());

        String participantName = request.getNickname(); // 참여자
        String sessionId = String.valueOf(updatedRoom.getRoomId()); //세션 아이디

        // 참가위해 토큰 발급
        String token = openViduService.generateToken(sessionId, participantName);
        JoinRoomResponse response = new JoinRoomResponse(updatedRoom, token);
        return ResponseEntity.ok(BaseResponse.success("success", response));
    }

    /**
     * 방 나가기
     */
    @PostMapping("/rooms/{roomId}/leave")
    public ResponseEntity<BaseResponse<RoomResponse>> leaveRoom(
            @PathVariable Long roomId,
            @RequestBody LeaveRoomRequest request
    ) {
        roomService.leaveRoom(roomId, request.getNickname());
        return ResponseEntity.ok(BaseResponse.success("success", null));
    }

    /**
     * 방 목록 조회
     */
    @GetMapping("/rooms")
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
    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<BaseResponse<RoomResponse>> searchRoom(@PathVariable Long roomId) {
        Room room = roomService.getRoom(roomId);
        RoomResponse response = new RoomResponse(room);
        return ResponseEntity.ok(BaseResponse.success("success", response));
    }
}
