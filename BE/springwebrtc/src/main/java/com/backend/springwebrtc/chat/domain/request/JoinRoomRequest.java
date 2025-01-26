package com.backend.springwebrtc.chat.domain.request;

import lombok.Data;

@Data
public class JoinRoomRequest {
    private String nickname;
    private Integer password;
}
