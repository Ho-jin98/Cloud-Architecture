package com.example.cloudArchitecture.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // GlobalExceptionHandler에서 코드 작성의 편의성을 위해 "정적 팩토리 메서드"를 미리 만들어놓자!

    // @Valid 검증 실패용 -> MethodArgumentNotValidException
    public static ErrorResponse ofValid(String message, String path) {
        return new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value()
                , HttpStatus.BAD_REQUEST.name(), message, path);
    }


    // ErrorCode 기반 에러용 -> MemberException, ServiceException
    public static ErrorResponse of(ErrorCode errorCode, String path) {
        return new ErrorResponse(
                LocalDateTime.now(),
                errorCode.getHttpStatus().value(),
                errorCode.getHttpStatus().name(),
                errorCode.getMessage(),
                path
        );
    }

    // 500에러 전용 -> Exception (최후의 보루)
    public static ErrorResponse of500(String message, String path) {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                message,
                path
        );
    }
}
