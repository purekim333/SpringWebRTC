package com.backend.springwebrtc.room.domain.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JoinRoomRequest {
    @NotNull
    private String nickname;
    private Integer password;
}
