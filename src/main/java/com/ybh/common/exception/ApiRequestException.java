package com.ybh.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiRequestException extends RuntimeException {
    private final ExceptionCode exceptionCode;
    private final HttpStatus httpStatus;

    public ApiRequestException(ExceptionCode exceptionCode, HttpStatus httpStatus) {
        super(exceptionCode.message());
        this.exceptionCode = exceptionCode;
        this.httpStatus = httpStatus;
    }

    public int code() {
        return exceptionCode.code();
    }
}
