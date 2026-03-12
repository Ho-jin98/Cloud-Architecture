package com.example.cloudArchitecture.config;

import com.example.cloudArchitecture.common.CommonResponse;
import com.example.cloudArchitecture.exception.ErrorResponse;
import com.example.cloudArchitecture.exception.MemberException;
import com.example.cloudArchitecture.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<?>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        // 스택트레이스 : 에러가 발생했을 때 어디서부터 어디까지 타고 왔는지 경로를 보여주는 것!
        // 마지막 파라미터 타입이 Throwable이면, Logback이 자동으로 스택 트레이스를 출력해 줌
        log.error("[ValidationException] path: {}, message: {}", request.getRequestURI(), ex.getMessage(), ex);

        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.fail(ErrorResponse.ofValid(errorMessage, request.getRequestURI())));
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<CommonResponse<?>> handleMemberException(
            MemberException ex, HttpServletRequest request) {

        log.error("[MemberException] path: {}, message: {}", request.getRequestURI(), ex.getMessage(), ex);

        return ResponseEntity.status(ex.getErrorCode().getHttpStatus())
                .body(CommonResponse.fail(ErrorResponse.of(ex.getErrorCode(), request.getRequestURI())));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<CommonResponse<?>> handleServiceException(
            ServiceException ex, HttpServletRequest request) {

        log.error("[ServiceException] path: {}, message: {}", request.getRequestURI(), ex.getMessage(), ex);

        return ResponseEntity.status(ex.getErrorCode().getHttpStatus())
                .body(CommonResponse.fail(ErrorResponse.of(ex.getErrorCode(), request.getRequestURI())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<?>> handleException(
            Exception ex, HttpServletRequest request) {

        log.error("[Exception] path: {}, message: {}", request.getRequestURI(), ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.fail(ErrorResponse.of500(ex.getMessage(), request.getRequestURI())));
    }
}
