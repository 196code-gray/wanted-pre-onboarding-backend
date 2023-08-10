package com.onboarid.wanted.exception;

import lombok.Getter;

public class BusinessException extends RuntimeException{
    @Getter
    private final ExceptionCode exceptionCode;

    public BusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
