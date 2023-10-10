package com.ybh.common.exception;

public class BusinessException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public BusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode.message());
        this.exceptionCode = exceptionCode;
    }

    public BusinessException(ExceptionCode exceptionCode, String append) {
        super(exceptionCode.message() + "\n" + append);
        this.exceptionCode = exceptionCode;
    }

    public BusinessException(ExceptionCode exceptionCode, Throwable cause) {
        super(exceptionCode.message(), cause);
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode code() {
        return exceptionCode;
    }
}
