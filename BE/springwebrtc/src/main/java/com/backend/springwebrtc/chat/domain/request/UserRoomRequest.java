package com.backend.springwebrtc.chat.domain.request;

import lombok.Data;

@Data
public class UserRoomRequest {
    private String nickname; // 참여자의 닉네임
}