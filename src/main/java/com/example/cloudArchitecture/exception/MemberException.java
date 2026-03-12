package com.example.cloudArchitecture.exception;

import lombok.Getter;

@Getter

public class MemberException extends RuntimeException {

    private final ErrorCode errorCode;

    public MemberException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;

    }
}
