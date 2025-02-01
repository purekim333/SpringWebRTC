package com.backend.springwebrtc.openvidu.service;

import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenViduService {

    private final OpenVidu openVidu;

    /**
     * 새로운 세션 생성
     *
     * @param sessionId 세션 ID (roomId 와 동일하게 사용)
     * @return 생성된 세션 객체
     */
    public Session createSession(String sessionId) throws OpenViduJavaClientException, OpenViduHttpException {
        SessionProperties properties = new SessionProperties.Builder()
                .customSessionId(sessionId)
                .mediaMode(MediaMode.ROUTED)
                .build();

        return openVidu.createSession(properties);
    }

    /**
     * 세션에 참여하기 위한 토큰 생성
     *
     * @param sessionId 세션 ID
     * @return 생성된 토큰
     */
    public String generateToken(String sessionId, String nickname) throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openVidu.getActiveSession(sessionId);

        if (session == null) {
            throw new IllegalArgumentException("세션을 찾을 수 없습니다. 세션 ID: " + sessionId);
        }

        ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
                .role(OpenViduRole.PUBLISHER) // 동영상 전송과 스트림
                .data("{\"sessionId\":\"" + sessionId + "\", \"username\":\"" + nickname + "\"}")
                .build();

        String token = session.createConnection(connectionProperties).getToken();
        System.out.println("generated token:" + token);

        return token;
    }
}