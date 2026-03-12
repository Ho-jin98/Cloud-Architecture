package com.example.cloudArchitecture.exception;

import lombok.Getter;

@Getter

public class ServerException extends RuntimeException{
    private ErrorCode errorCode;

    public ServerException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
