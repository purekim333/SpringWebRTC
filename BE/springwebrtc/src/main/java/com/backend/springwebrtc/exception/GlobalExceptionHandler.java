package com.backend.springwebrtc.exception;

import com.backend.springwebrtc.room.domain.response.BaseResponse;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 오픈비두 클라이언트 에러
    @ExceptionHandler(OpenViduJavaClientException.class)
    public ResponseEntity<BaseResponse<String>> handleOpenViduJavaClientException(OpenViduJavaClientException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.error("OpenVidu Client Exception: " + e.getMessage()));
    }

    // 오픈비두 HTTP 에러
    @ExceptionHandler(OpenViduHttpException.class)
    public ResponseEntity<BaseResponse<String>> handleOpenViduHttpException(OpenViduHttpException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.error("OpenVidu HTTP Exception: " + e.getMessage()));
    }

    // 파라미터 에러
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse<String>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.error(e.getMessage()));
    }

    // 런타임 에러
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse<String>> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.error("Unexpected Error: " + e.getMessage()));
    }
}

