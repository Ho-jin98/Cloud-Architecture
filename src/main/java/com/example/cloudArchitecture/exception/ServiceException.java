package com.example.cloudarchitecture.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter

public class ServiceException extends RuntimeException{
    private ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
