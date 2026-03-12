package com.example.cloudArchitecture.exception;

import lombok.Getter;

@Getter

public class ServiceException extends RuntimeException{
    private ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
