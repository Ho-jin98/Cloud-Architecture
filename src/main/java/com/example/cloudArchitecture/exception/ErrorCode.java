package com.example.cloudArchitecture.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter

public enum ErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "등록된 팀원을 찾을 수 없습니다."),
    IMAGE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 저장에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
